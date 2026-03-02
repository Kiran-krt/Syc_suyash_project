package com.syc.dashboard.command.systemconfig.entity

import com.syc.dashboard.command.systemconfig.api.commands.RegisterSystemConfigCommand
import com.syc.dashboard.command.systemconfig.api.commands.SystemConfigUpdateAllFieldsCommand
import com.syc.dashboard.command.systemconfig.api.commands.SystemConfigUpdateLogoCommand
import com.syc.dashboard.command.systemconfig.exceptions.SystemConfigEventStreamNotExistInEventStoreException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.systemconfig.entity.enums.SystemConfigStatusEnum
import com.syc.dashboard.shared.systemconfig.events.SystemConfigAllFieldsUpdatedEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigLogoUpdatedEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigRegisteredEvent
import java.util.*

class SystemConfigAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var appName: String = ""
    var logo: MutableList<DocumentIdDto> = mutableListOf()
    var status: SystemConfigStatusEnum = SystemConfigStatusEnum.ACTIVE

    constructor(command: RegisterSystemConfigCommand) : this() {
        val createdOn = Date()
        raiseEvent(
            SystemConfigRegisteredEvent(
                id = command.id,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: SystemConfigRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
    }

    fun updateAllFields(command: SystemConfigUpdateAllFieldsCommand) {
        if (!active) {
            throw SystemConfigEventStreamNotExistInEventStoreException(
                "System Config all fields updated exception!",
            )
        }
        raiseEvent(
            SystemConfigAllFieldsUpdatedEvent(
                id = command.id,
                appName = command.appName,
                logo = command.logo,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: SystemConfigAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        appName = event.appName
        logo = event.logo
        status = event.status
    }

    fun updateLogo(command: SystemConfigUpdateLogoCommand) {
        if (!active) {
            throw SystemConfigEventStreamNotExistInEventStoreException(
                "System Config Logo updated exception!",
            )
        }
        raiseEvent(
            SystemConfigLogoUpdatedEvent(
                id = command.id,
                logo = command.logo,
            ).buildEvent(command),
        )
    }

    fun apply(event: SystemConfigLogoUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        logo = event.logo
    }
}
