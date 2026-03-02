package com.syc.dashboard.framework.common.security.jwt

import com.google.gson.Gson
import com.syc.dashboard.framework.common.security.dto.LoginFailResponse
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.io.Serializable
import java.nio.charset.StandardCharsets

@Component
class JwtAuthenticationEntryPoint : HttpBasicServerAuthenticationEntryPoint(), Serializable {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> {
        val loginResponse = LoginFailResponse()
        val jsonLoginResponse = Gson().toJson(loginResponse)
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED

        val buffer: DataBuffer =
            exchange.response.bufferFactory().wrap(jsonLoginResponse.toByteArray(StandardCharsets.UTF_8))

        return exchange.response.writeWith(Mono.just(buffer))
    }
}
