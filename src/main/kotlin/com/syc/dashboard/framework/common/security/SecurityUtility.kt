package com.syc.dashboard.framework.common.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class SecurityUtility constructor(
    @Value("\${syc.spring.security.salt-key:SALT_KEY}")
    private val SALT: String = "SALT_KEY", // Salt should be protected carefully
) {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(12, SecureRandom(SALT.toByteArray()))
    }
}
