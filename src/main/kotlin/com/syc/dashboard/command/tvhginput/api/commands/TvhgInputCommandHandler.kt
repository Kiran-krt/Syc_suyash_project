package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.command.tvhginput.entity.TvhgInputAggregate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TvhgInputCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<TvhgInputAggregate>,
) : CommandHandler {

    private fun handle(command: RegisterTvhgInputCommand) {
        val aggregate = TvhgInputAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TvhgInputUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TvhgInputUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddProjectInformationInTvhgInputCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addProjectInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddStructureInformationCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addStructureInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateStructureInformationAllFieldCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStructureInformationAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateProjectInformationAllFieldCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateProjectInformationAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddHydrologicInformationCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addHydrologicInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateHydrologicInformationAllFieldCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateHydrologicInformationAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddPipeInformationCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addPipeInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddStructureDrawingDataCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addStructureDrawingData(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdatePipeInformationAllFieldCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updatePipeInformationAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateStructureDrawingDataAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStructureDrawingDataAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddInletControlParameterCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addInletControlParameter(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddOutletDrawingInformationCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addOutletDrawingInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddPipeDrawingInformationCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addPipeDrawingInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateFlowPathDrawingInformationAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateFlowPathDrawingInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdatePipeDrawingInformationAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updatePipeDrawingInformationAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateInletControlParameterAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateInletControlParameterAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateOutletDrawingInformationAllFieldCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateOutletDrawingInformationAllField(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddFlowPathDrawingInformationCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.addFlowPathDrawingInformation(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateOutletDrawingInformationElevationDataAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateDistanceElevationData(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreTvhgInputReadDbCommand -> handle(command)
            is RegisterTvhgInputCommand -> handle(command)
            is TvhgInputUpdateStatusCommand -> handle(command)
            is TvhgInputUpdateAllFieldsCommand -> handle(command)
            is AddProjectInformationInTvhgInputCommand -> handle(command)
            is AddStructureInformationCommand -> handle(command)
            is UpdateStructureInformationAllFieldCommand -> handle(command)
            is UpdateProjectInformationAllFieldCommand -> handle(command)
            is AddHydrologicInformationCommand -> handle(command)
            is UpdateHydrologicInformationAllFieldCommand -> handle(command)
            is AddPipeInformationCommand -> handle(command)
            is AddStructureDrawingDataCommand -> handle(command)
            is UpdatePipeInformationAllFieldCommand -> handle(command)
            is UpdateStructureDrawingDataAllFieldsCommand -> handle(command)
            is AddInletControlParameterCommand -> handle(command)
            is AddOutletDrawingInformationCommand -> handle(command)
            is AddPipeDrawingInformationCommand -> handle(command)
            is UpdateFlowPathDrawingInformationAllFieldsCommand -> handle(command)
            is UpdatePipeDrawingInformationAllFieldsCommand -> handle(command)
            is UpdateInletControlParameterAllFieldsCommand -> handle(command)
            is UpdateOutletDrawingInformationAllFieldCommand -> handle(command)
            is AddFlowPathDrawingInformationCommand -> handle(command)
            is UpdateOutletDrawingInformationElevationDataAllFieldsCommand -> handle(command)
        }
    }
}
