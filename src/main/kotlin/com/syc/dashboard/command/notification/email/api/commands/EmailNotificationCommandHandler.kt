package com.syc.dashboard.command.notification.email.api.commands

import com.syc.dashboard.command.notification.email.entity.EmailNotificationAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmailNotificationCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<EmailNotificationAggregate>,
) : CommandHandler {

    private fun handle(command: RestoreEmailNotificationReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: SendEmailNotificationCommand) {
        val aggregate = EmailNotificationAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreEmailNotificationReadDbCommand -> handle(command)
            is SendEmailNotificationCommand -> handle(command)
        }
    }
}
