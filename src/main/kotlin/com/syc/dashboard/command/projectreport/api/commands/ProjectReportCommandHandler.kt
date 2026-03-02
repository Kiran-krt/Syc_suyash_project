package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.command.projectreport.entity.ProjectReportAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectReportCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<ProjectReportAggregate>,
) : CommandHandler {

    private fun handle(command: RegisterProjectReportCommand) {
        val aggregate = ProjectReportAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ProjectReportUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ProjectReportUpdateFieldCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateField(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: OutfallPhotoUploadCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.outfallPhotoUpload(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: OutfallPhotoUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateOutfallPhotoStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: OutfallPhotoUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateOutfallPhotoAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddAppendixCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addAppendix(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AppendixUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAppendixAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AppendixUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAppendixStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RegisterProjectReportCommand -> handle(command)
            is ProjectReportUpdateStatusCommand -> handle(command)
            is ProjectReportUpdateFieldCommand -> handle(command)
            is OutfallPhotoUploadCommand -> handle(command)
            is OutfallPhotoUpdateStatusCommand -> handle(command)
            is OutfallPhotoUpdateAllFieldsCommand -> handle(command)
            is AddAppendixCommand -> handle(command)
            is AppendixUpdateAllFieldsCommand -> handle(command)
            is AppendixUpdateStatusCommand -> handle(command)
        }
    }
}
