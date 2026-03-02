package com.syc.dashboard.framework.common.log.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.IdGenerator
import org.springframework.util.JdkIdGenerator

@Configuration
class IdGeneratorConfig {
    @Bean
    fun idGenerator(): IdGenerator {
        return JdkIdGenerator()
    }
}
