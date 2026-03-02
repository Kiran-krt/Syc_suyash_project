package com.syc.dashboard.framework.common.security.service

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationManager : ReactiveAuthenticationManager {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun authenticate(authentication: Authentication): Mono<Authentication> = Mono.just(authentication)
}
