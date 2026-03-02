package com.syc.dashboard.command.vehiclelog.api.commands

import com.syc.dashboard.command.vehiclelog.entity.VehicleLogAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VehicleLogCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<VehicleLogAggregate>,
) : CommandHandler {

    private fun handle(command: RestoreVehicleLogReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: RegisterVehicleLogCommand) {
        val aggregate = VehicleLogAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: VehicleLogUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: VehicleLogUpdateStatusByIdCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatusById(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreVehicleLogReadDbCommand -> handle(command)
            is RegisterVehicleLogCommand -> handle(command)
            is VehicleLogUpdateAllFieldsCommand -> handle(command)
            is VehicleLogUpdateStatusByIdCommand -> handle(command)
        }
    }
}
