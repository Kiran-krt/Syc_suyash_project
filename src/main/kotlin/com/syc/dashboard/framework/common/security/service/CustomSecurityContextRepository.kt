package com.syc.dashboard.framework.common.security.service

import com.syc.dashboard.framework.common.security.jwt.JwtUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class CustomSecurityContextRepository @Autowired constructor(
    private val jwtUtils: JwtUtils,
) : ServerSecurityContextRepository {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void>? {
        log.info("CustomSecurityContextRepository.save")

        // Don't know yet where this is for.
        return null
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return jwtUtils.getJWTFromRequestReactive(exchange.request)
            .filter { jwtUtils.validateToken(it) }
            .switchIfEmpty { Mono.empty() }
            .flatMap { jwtUtils.getAuthenticationFromJwt(it) }
            .map { SecurityContextImpl(it) }
    }
}
