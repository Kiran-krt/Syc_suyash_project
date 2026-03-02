package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.command.tvhgConfig.entity.TvhgConfigAggregate
import com.syc.dashboard.command.tvhgConfig.repository.jpa.TvhgConfigEventStoreRepository
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.tvhgconfig.events.TvhgConfigRegisteredEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TvhgConfigCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<TvhgConfigAggregate>,
    private val eventStoreRepository: TvhgConfigEventStoreRepository,
) : CommandHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun handle(command: RegisterTvhgConfigCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataTenant(
            eventType = TvhgConfigRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
        )

        if (event.isNotEmpty()) {
            log.info("Tvhg config is already registered with tenant '${command.tenantId}'.")
            return
        }

        log.info("Registering Tvhg config for tenant '${command.tenantId}'.")

        command.id = command.tenantId

        val aggregate = TvhgConfigAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddUnitsInTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addUnits(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddDesignStormTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addDesignStorm(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddStructureTypeInTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addStructureType(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddInletControlDataTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addInletControlData(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddOutletStructureTypeTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addOutletStructureType(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddPipeMaterialTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addPipeMaterial(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddPipeTypeTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addPipeType(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateDesignStormAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateDesignStormAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateInletControlDataAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateInletControlDataAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdatePipeMaterialAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updatePipeMaterialAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdatePipeTypeAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updatePipeTypeAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateStructureTypeAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStructureTypeAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateUnitsAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateUnitsFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateOutletStructureTypeAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateOutletStructureTypeAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddMdStandardNumberTvhgConfigCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addMdStandardNumber(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateMdStandardNumberAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateMdStandardNumberAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RegisterTvhgConfigCommand -> handle(command)
            is AddUnitsInTvhgConfigCommand -> handle(command)
            is AddDesignStormTvhgConfigCommand -> handle(command)
            is AddStructureTypeInTvhgConfigCommand -> handle(command)
            is AddInletControlDataTvhgConfigCommand -> handle(command)
            is AddOutletStructureTypeTvhgConfigCommand -> handle(command)
            is AddPipeMaterialTvhgConfigCommand -> handle(command)
            is AddPipeTypeTvhgConfigCommand -> handle(command)
            is UpdateDesignStormAllFieldsCommand -> handle(command)
            is UpdateInletControlDataAllFieldsCommand -> handle(command)
            is UpdatePipeMaterialAllFieldsCommand -> handle(command)
            is UpdatePipeTypeAllFieldsCommand -> handle(command)
            is UpdateStructureTypeAllFieldsCommand -> handle(command)
            is UpdateUnitsAllFieldsCommand -> handle(command)
            is UpdateOutletStructureTypeAllFieldsCommand -> handle(command)
            is AddMdStandardNumberTvhgConfigCommand -> handle(command)
            is UpdateMdStandardNumberAllFieldsCommand -> handle(command)
        }
    }
}
