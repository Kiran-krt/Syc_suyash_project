package com.syc.dashboard.command.project.api.commands

import com.syc.dashboard.command.project.entity.ProjectAggregate
import com.syc.dashboard.command.project.exceptions.ProjectAlreadyExistException
import com.syc.dashboard.command.project.repository.ProjectEventStoreRepository
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.project.events.ProjectRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<ProjectAggregate>,
    private val eventStoreRepository: ProjectEventStoreRepository,
) : CommandHandler {

    private fun handle(command: RegisterProjectCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataId(
            eventType = ProjectRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            projectCode = command.projectCode,
        )

        if (event.isNotEmpty()) {
            throw ProjectAlreadyExistException("projectCode '${command.projectCode}' is already registered.")
        }

        val aggregate = ProjectAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ProjectUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ProjectUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddJobCodeCommand) {
        val aggregate = eventSourcingHandler.getById(command.projectId)
        aggregate.addJobCode(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateJobCodeByProjectIdCommand) {
        val aggregate = eventSourcingHandler.getById(command.projectId)
        aggregate.updateJobCodeByProjectId(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RegisterProjectCommand -> handle(command)
            is ProjectUpdateStatusCommand -> handle(command)
            is ProjectUpdateAllFieldsCommand -> handle(command)
            is AddJobCodeCommand -> handle(command)
            is UpdateJobCodeByProjectIdCommand -> handle(command)
        }
    }
}
