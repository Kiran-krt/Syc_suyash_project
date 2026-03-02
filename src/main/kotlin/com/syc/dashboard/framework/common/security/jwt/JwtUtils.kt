package com.syc.dashboard.framework.common.security.jwt

import com.syc.dashboard.framework.common.security.SecurityConstants
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.service.UserPrincipalReactiveService
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class JwtUtils @Autowired constructor(
    private val userPrincipalReactiveService: UserPrincipalReactiveService,
    @Value("\${syc.spring.security.jwt-secret-key:Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=}")
    private val jwtSecretKey: String,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getJWTFromRequestReactive(request: ServerHttpRequest): Mono<String> {
        return Mono.justOrEmpty(request.headers[SecurityConstants.AUTH_HEADER]?.get(0))
            .switchIfEmpty { Mono.justOrEmpty(request.queryParams[SecurityConstants.AUTH_HEADER]?.get(0)) }
            .filter { StringUtils.hasText(it) }
            .map {
                var token = it
                if (it.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                    token = it.substring(SecurityConstants.TOKEN_PREFIX.length)
                }
                token
            }
    }

    fun getJWTFromRequest2(request: ServerHttpRequest): String {
        var jwtToken = request.headers[SecurityConstants.AUTH_HEADER]?.get(0)
        if (jwtToken.isNullOrEmpty()) {
            jwtToken = request.queryParams[SecurityConstants.AUTH_HEADER]?.get(0)
        }
        return if (jwtToken != null && StringUtils.hasText(jwtToken)) {
            if (jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                jwtToken.substring(SecurityConstants.TOKEN_PREFIX.length)
            } else {
                jwtToken
            }
        } else {
            ""
        }
    }

    fun getAuthenticationFromJwt(jwt: String): Mono<UsernamePasswordAuthenticationToken> {
        return Mono.just(validateToken(jwt))
            .filter { it }
            .switchIfEmpty { Mono.empty() }
            .flatMap {
                val userId = getUserIdFromJWT(jwt)
                val userRole = getUserRoleFromJWT(jwt)
                userPrincipalReactiveService.findUserDetailsById(
                    id = userId,
                    role = userRole,
                )
            }.map {
                UsernamePasswordAuthenticationToken(
                    it,
                    it.password,
                    setOf(SimpleGrantedAuthority(it.getRole().name)),
                )
            }
    }

    // Validate the token
    fun validateToken(token: String): Boolean {
        if (!StringUtils.hasText(token)) return false

        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)))
                .build()
                .parseSignedClaims(token).payload
            return true
        } catch (ex: SignatureException) {
            log.error("Invalid JWT Signature")
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT Token")
        } catch (ex: ExpiredJwtException) {
            log.error("Expired JWT Token")
        } catch (ex: UnsupportedJwtException) {
            log.error("Unsupported JWT Token")
        } catch (ex: IllegalArgumentException) {
            log.error("JWT claims string is empty")
        }
        return false
    }

    // Get user id from token
    fun getUserIdFromJWT(token: String): String {
        val claims = getAllClaimsFromToken(token)
        return claims?.get("id") as String
    }

    // Get user role from token
    fun getUserRoleFromJWT(token: String): UserRole {
        val claims = getAllClaimsFromToken(token)
        return UserRole.valueOf(claims?.get("role") as String)
    }

    fun getAllClaimsFromToken(token: String): Claims? {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)))
            .build()
            .parseSignedClaims(token).payload
    }
}
