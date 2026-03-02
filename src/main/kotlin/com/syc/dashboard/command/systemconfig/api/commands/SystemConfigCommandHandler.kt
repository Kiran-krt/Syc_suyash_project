package com.syc.dashboard.command.systemconfig.api.commands

import com.syc.dashboard.command.systemconfig.entity.SystemConfigAggregate
import com.syc.dashboard.command.systemconfig.repository.jpa.SystemConfigEventStoreRepository
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.systemconfig.events.SystemConfigRegisteredEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SystemConfigCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<SystemConfigAggregate>,
    private val eventStoreRepository: SystemConfigEventStoreRepository,
) : CommandHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun handle(command: RegisterSystemConfigCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataTenant(
            eventType = SystemConfigRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
        )

        if (event.isNotEmpty()) {
            log.info("System configuration is already registered with tenant '${command.tenantId}'.")
            return
        }

        log.info("Registering system configuration for tenant '${command.tenantId}'.")

        command.id = command.tenantId

        val aggregate = SystemConfigAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SystemConfigUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SystemConfigUpdateLogoCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateLogo(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RegisterSystemConfigCommand -> handle(command)
            is SystemConfigUpdateAllFieldsCommand -> handle(command)
            is SystemConfigUpdateLogoCommand -> handle(command)
        }
    }
}
