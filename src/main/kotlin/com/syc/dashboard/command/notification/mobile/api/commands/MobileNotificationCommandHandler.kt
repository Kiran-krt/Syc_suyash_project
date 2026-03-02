package com.syc.dashboard.command.notification.mobile.api.commands

import com.syc.dashboard.command.notification.mobile.entity.MobileNotificationAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MobileNotificationCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<MobileNotificationAggregate>,
) : CommandHandler {

    private fun handle(command: RestoreMobileNotificationReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: SendMobileNotificationCommand) {
        val aggregate = MobileNotificationAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreMobileNotificationReadDbCommand -> handle(command)
            is SendMobileNotificationCommand -> handle(command)
        }
    }
}
