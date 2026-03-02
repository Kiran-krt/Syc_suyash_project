package com.syc.dashboard.command.document.api.commands

import com.syc.dashboard.command.document.entity.DocumentAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DocumentCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<DocumentAggregate>,
) : CommandHandler {
    private fun handle(command: DocumentUploadCommand) {
        val aggregate = DocumentAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: DocumentUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatusById(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is DocumentUploadCommand -> handle(command)
            is DocumentUpdateStatusCommand -> handle(command)
        }
    }
}
