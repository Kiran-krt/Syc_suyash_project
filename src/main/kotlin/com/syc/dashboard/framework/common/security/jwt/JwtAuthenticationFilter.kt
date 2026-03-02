package com.syc.dashboard.framework.common.security.jwt

import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Service
class JwtAuthenticationFilter @Autowired constructor(
    private val jwtUtils: JwtUtils,
) : WebFilter {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return jwtUtils.getJWTFromRequestReactive(exchange.request)
            .filter { StringUtils.isNotEmpty(it) && SecurityContextHolder.getContext().authentication == null }
            .flatMap { jwtUtils.getAuthenticationFromJwt(it) }
            .map { SecurityContextHolder.getContext().authentication = it }
            .flatMap { chain.filter(exchange) }
            .switchIfEmpty(chain.filter(exchange))
    }
}
