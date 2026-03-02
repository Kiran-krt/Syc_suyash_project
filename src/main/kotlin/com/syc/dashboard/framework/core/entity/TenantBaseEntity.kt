package com.syc.dashboard.framework.core.entity

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.framework.core.exceptions.TenantIdNotFoundInRequestHeaderException
import org.springframework.util.StringUtils

abstract class TenantBaseEntity(
    var tenantId: String = "",
    var remoteAddress: String = "",
    var remoteHostName: String = "",
    var triggeredBy: String = "",
) : BaseEntity() {

    fun buildEntity(event: TenantBaseEvent): TenantBaseEntity {
        if (!StringUtils.hasText(event.tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the event while building entity")
        }

        tenantId = event.tenantId
        remoteAddress = event.remoteAddress
        remoteHostName = event.remoteHostName
        triggeredBy = event.triggeredBy
        return this
    }
}
