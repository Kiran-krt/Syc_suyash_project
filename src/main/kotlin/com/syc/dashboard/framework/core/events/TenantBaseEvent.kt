package com.syc.dashboard.framework.core.events

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.framework.core.exceptions.EventNameMismatchException
import com.syc.dashboard.framework.core.exceptions.TenantIdNotFoundInRequestHeaderException
import org.springframework.util.StringUtils
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.declaredMembers

private const val EVENT_NAME_VARIABLE = "EVENT_NAME"

abstract class TenantBaseEvent(
    id: String,
    version: Int,
    auditTrail: Boolean = false,
    var tenantId: String = "",
) : BaseEvent(id = id, version = version, auditTrail = auditTrail) {

    init {
        val eventName = this::class.companionObject?.declaredMembers?.find { it.name == EVENT_NAME_VARIABLE }?.call("")
        if (eventName != this::class.simpleName) {
            throw EventNameMismatchException("Event name is not configured correctly - class name '${this::class.simpleName}' and event name '$eventName'.")
        }
    }

    fun buildEvent(command: TenantBaseCommand): TenantBaseEvent {
        if (!StringUtils.hasText(command.tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the command while building event")
        }

        tenantId = command.tenantId
        remoteAddress = command.remoteAddress
        remoteHostName = command.remoteHostName
        triggeredBy = command.triggeredBy
        return this
    }
}
