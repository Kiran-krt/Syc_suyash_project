package com.syc.dashboard.query.systemconfig.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.systemconfig.entity.SystemConfig
import com.syc.dashboard.query.systemconfig.repository.jpa.SystemConfigRepository
import com.syc.dashboard.shared.systemconfig.events.SystemConfigAllFieldsUpdatedEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigLogoUpdatedEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SystemConfigEventHandler @Autowired constructor(
    private val systemConfigRepository: SystemConfigRepository,
) : EventHandler {

    private fun on(event: SystemConfigRegisteredEvent) {
        val systemConfig = SystemConfig(
            id = event.id,
            createdOn = event.createdOn,
        ).buildEntity(event) as SystemConfig

        systemConfigRepository.save(systemConfig)
    }

    private fun on(event: SystemConfigAllFieldsUpdatedEvent) {
        val systemConfigOptional = systemConfigRepository.findById(event.id)
        if (systemConfigOptional.isEmpty) {
            return
        }
        systemConfigOptional.get().appName = event.appName
        systemConfigOptional.get().logo = event.logo
        systemConfigOptional.get().status = event.status
        systemConfigRepository.save(systemConfigOptional.get())
    }

    private fun on(event: SystemConfigLogoUpdatedEvent) {
        val systemConfigOptional = systemConfigRepository.findById(event.id)
        if (systemConfigOptional.isEmpty) {
            return
        }
        systemConfigOptional.get().logo = event.logo
        systemConfigRepository.save(systemConfigOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is SystemConfigRegisteredEvent -> on(event)
            is SystemConfigAllFieldsUpdatedEvent -> on(event)
            is SystemConfigLogoUpdatedEvent -> on(event)
        }
    }
}
