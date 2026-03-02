package com.syc.dashboard.framework.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver

@Configuration
class FreeMarkerConfig {

    @Bean
    fun freemarkerViewResolver(): FreeMarkerViewResolver {
        val resolver = FreeMarkerViewResolver()
        resolver.setSuffix(".ftlh")
        return resolver
    }

    @Bean
    fun freemarkerConfig(): FreeMarkerConfigurer {
        val freeMarkerConfigurer = FreeMarkerConfigurer()
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates/")
        return freeMarkerConfigurer
    }
}
