package com.syc.dashboard.framework.common.config

import com.syc.dashboard.framework.common.security.SecurityConstants
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig @Autowired constructor(
    @Value("\${spring.application.name:AppName}")
    private val appName: String,
    @Value("\${syc.swagger.server.urls:localhost}")
    private val swaggerServerUrls: List<String>,
) {

    @Bean
    fun springShopOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title(appName)
                    .description("Suyash Consulting Timesheet Dashboard Application"),
            )
            .servers(swaggerServerUrls.map { Server().url(it) })
            .security(
                listOf(
                    SecurityRequirement().addList("bearer_key"),
                    SecurityRequirement().addList("tenant_id"),
                ),
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        "bearer_key",
                        SecurityScheme()
                            .name("Bearer-Key")
                            .description("Bearer Token")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT"),
                    )
                    .addSecuritySchemes(
                        "tenant_id",
                        SecurityScheme()
                            .name(SecurityConstants.TENANT_ID_HEADER)
                            .description("Tenant / Realm id. Use default tenant as 'syc'.")
                            .type(SecurityScheme.Type.APIKEY)
                            .`in`(SecurityScheme.In.HEADER),
                    ),
            )
    }
}
