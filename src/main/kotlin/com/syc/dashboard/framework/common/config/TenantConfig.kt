package com.syc.dashboard.framework.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.system")
data class TenantConfig(
    val portalUrl: String = "https://www.suyashconsulting.com/",
    val tenants: Map<String, TenantProperties> = mutableMapOf(),
) {

    data class TenantProperties(
        val portalUrl: String = "",
    )

    fun getPortalUrlByTenantId(tenantId: String): String {
        return tenants[tenantId]?.portalUrl ?: portalUrl
    }
}
