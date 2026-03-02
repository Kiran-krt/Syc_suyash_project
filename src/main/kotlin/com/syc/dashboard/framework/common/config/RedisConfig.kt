package com.syc.dashboard.framework.common.config

import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveKeyCommands
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.ReactiveStringCommands
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig @Autowired constructor(
    private var factory: RedisConnectionFactory,
) {

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any> {
        val serializer: Jackson2JsonRedisSerializer<Any> = Jackson2JsonRedisSerializer(Any::class.java)
        val builder: RedisSerializationContextBuilder<String, Any> =
            RedisSerializationContext.newSerializationContext(StringRedisSerializer())
        val context: RedisSerializationContext<String, Any> = builder.value(serializer)
            .build()
        return ReactiveRedisTemplate(factory, context)
    }

    @Bean
    fun keyCommands(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveKeyCommands {
        return reactiveRedisConnectionFactory.reactiveConnection.keyCommands()
    }

    @Bean
    fun stringCommands(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveStringCommands {
        return reactiveRedisConnectionFactory.reactiveConnection.stringCommands()
    }

    @PreDestroy
    fun cleanRedis() {
        factory.connection.flushDb()
    }
}
