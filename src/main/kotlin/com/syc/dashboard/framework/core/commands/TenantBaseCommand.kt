package com.syc.dashboard.framework.core.commands

import com.fasterxml.jackson.annotation.JsonIgnore
import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.framework.core.exceptions.TenantIdNotFoundInRequestHeaderException
import org.springframework.util.StringUtils

abstract class TenantBaseCommand(
    id: String,
    @JsonIgnore
    var tenantId: String = "",
) : BaseCommand(id = id) {

    fun buildCommand(command: TenantBaseCommand): TenantBaseCommand {
        if (!StringUtils.hasText(command.tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the command while building command")
        }

        tenantId = command.tenantId
        remoteAddress = command.remoteAddress
        remoteHostName = command.remoteHostName
        triggeredBy = command.triggeredBy
        return this
    }

    fun buildCommand(event: TenantBaseEvent): TenantBaseCommand {
        if (!StringUtils.hasText(event.tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the event while building command")
        }

        tenantId = event.tenantId
        remoteAddress = event.remoteAddress
        remoteHostName = event.remoteHostName
        triggeredBy = event.triggeredBy
        return this
    }

    fun buildSystemCommand(tenantId: String): TenantBaseCommand {
        if (!StringUtils.hasText(tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID should not be empty while building command")
        }

        this.tenantId = tenantId
        remoteAddress = "SYSTEM"
        remoteHostName = "SYSTEM"
        triggeredBy = "SYSTEM"
        return this
    }
}
