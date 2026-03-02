package com.syc.dashboard.framework.common.security.jwt

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.user.UserPrincipal
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${syc.spring.security.jwt-secret-key:Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=}")
    private val jwtSecretKey: String? = null

    @Value(value = "\${syc.spring.security.jwt-token-expiration-time:3600000}")
    private var jwtTokenExpirationTime: Long = 3600000L // default 1hr

    // Generate the token
    fun generateToken(authentication: Authentication, userRole: UserRole): String {
        val user = authentication.principal as UserPrincipal
        val now = Date(System.currentTimeMillis())
        val expiryDate = Date(now.time + jwtTokenExpirationTime)
        val userId = user.id
        val claims: MutableMap<String?, Any?> = HashMap()
        claims["id"] = user.id
        claims["username"] = user.getUsername()
        claims["fullName"] = "${user.firstName} ${user.lastName}"
        claims["role"] = userRole

        var jwtBuilder = Jwts.builder()

        if (jwtTokenExpirationTime > 0) {
            jwtBuilder = jwtBuilder.expiration(Date(now.time + jwtTokenExpirationTime))
        }

        return jwtBuilder.subject(userId)
            .claims(claims)
            .issuedAt(now)
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)))
            .compact()
    }
}
