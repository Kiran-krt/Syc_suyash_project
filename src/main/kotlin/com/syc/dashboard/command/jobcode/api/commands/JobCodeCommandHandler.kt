package com.syc.dashboard.command.jobcode.api.commands

import com.syc.dashboard.command.jobcode.entity.JobCodeAggregate
import com.syc.dashboard.command.jobcode.exceptions.JobCodeAlreadyExistException
import com.syc.dashboard.command.jobcode.repository.JobCodeEventStoreRepository
import com.syc.dashboard.command.project.exceptions.ProjectDoesNotExistException
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.query.project.repository.jpa.ProjectRepository
import com.syc.dashboard.shared.jobcode.events.JobCodeRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobCodeCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<JobCodeAggregate>,
    private val eventStoreRepository: JobCodeEventStoreRepository,
    private val projectRepository: ProjectRepository,
) : CommandHandler {

    private fun handle(command: RestoreJobCodeReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: RegisterJobCodeCommand) {
        if (command.projectId.isEmpty()) {
            throw ProjectDoesNotExistException("Job code cannot be registered without a project.")
        }

        val event = eventStoreRepository.findByEventTypeAndEventDataId(
            eventType = JobCodeRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            code = command.code,
        )

        if (event.isNotEmpty()) {
            throw JobCodeAlreadyExistException("jobCode '${command.code}' is already registered.")
        }

        val aggregate = JobCodeAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: JobCodeUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddCostCodeCommand) {
        val aggregate = eventSourcingHandler.getById(command.jobCodeId)
        aggregate.addCostCode(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateCostCodeCommand) {
        val aggregate = eventSourcingHandler.getById(command.jobCodeId)
        aggregate.updateCostCode(command)
        eventSourcingHandler.save(aggregate)
    }
    private fun handle(command: UpdateJobCodeCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateJobCode(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreJobCodeReadDbCommand -> handle(command)
            is RegisterJobCodeCommand -> handle(command)
            is JobCodeUpdateStatusCommand -> handle(command)
            is AddCostCodeCommand -> handle(command)
            is UpdateCostCodeCommand -> handle(command)
            is UpdateJobCodeCommand -> handle(command)
        }
    }
}
