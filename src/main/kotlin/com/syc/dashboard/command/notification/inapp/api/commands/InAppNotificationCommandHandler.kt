package com.syc.dashboard.command.notification.inapp.api.commands

import com.syc.dashboard.command.notification.inapp.entity.InAppNotificationAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InAppNotificationCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<InAppNotificationAggregate>,
) : CommandHandler {

    private fun handle(command: RestoreInAppNotificationReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: SendInAppNotificationCommand) {
        val aggregate = InAppNotificationAggregate(command)
        eventSourcingHandler.save(aggregate)
    }
    private fun handle(command: InAppNotificationUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreInAppNotificationReadDbCommand -> handle(command)
            is SendInAppNotificationCommand -> handle(command)
            is InAppNotificationUpdateStatusCommand -> handle(command)
        }
    }
}
