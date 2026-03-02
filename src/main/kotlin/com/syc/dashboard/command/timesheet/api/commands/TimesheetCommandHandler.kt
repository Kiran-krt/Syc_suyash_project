package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.command.timesheet.entity.TimesheetAggregate
import com.syc.dashboard.command.timesheet.exceptions.TimesheetWeekEndingDateAlreadyExistsException
import com.syc.dashboard.command.timesheet.repository.jpa.TimesheetEventStoreRepository
import com.syc.dashboard.framework.common.exceptions.InvalidDateFormatException
import com.syc.dashboard.framework.common.utils.DateUtils.isValidDateByFormat
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.timesheet.events.TimesheetRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TimesheetCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<TimesheetAggregate>,
    private val eventStoreRepository: TimesheetEventStoreRepository,
) : CommandHandler {

    private fun handle(command: RestoreTimesheetReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: RegisterTimesheetCommand) {
        if (!(isValidDateByFormat(command.weekStartingDate) && isValidDateByFormat(command.weekEndingDate))) {
            throw InvalidDateFormatException("Invalid date format.")
        }

        val event = eventStoreRepository.findByTenantIdAndUserIdAndWeekEndingDate(
            eventType = TimesheetRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            userId = command.userId,
            weekEndingDate = command.weekEndingDate,
        )

        if (event.isNotEmpty()) {
            throw TimesheetWeekEndingDateAlreadyExistsException(
                "Week Ending Date '${command.weekEndingDate}' is already registered for user.",
            )
        }

        val aggregate = TimesheetAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: RegisterTimesheetWithStartDateCommand) {
        if (!(isValidDateByFormat(command.weekStartingDate) && isValidDateByFormat(command.weekEndingDate))) {
            throw InvalidDateFormatException("Invalid date format.")
        }

        val event = eventStoreRepository.findByTenantIdAndUserIdAndWeekEndingDate(
            eventType = TimesheetRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            userId = command.userId,
            weekEndingDate = command.weekEndingDate,
        )

        if (event.isNotEmpty()) {
            throw TimesheetWeekEndingDateAlreadyExistsException(
                "Week Ending Date '${command.weekEndingDate}' is already registered for user.",
            )
        }

        val aggregate = TimesheetAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateDayDetailsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateDayData(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateAdminCommentsByIdCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateCommentsByAdmin(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateEmployeeCommentsByIdCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateCommentsByEmployee(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateManagerCommentsByIdCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateCommentsByManager(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimesheetStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateWithTimesheetRowsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimesheetRows(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: DeleteTimesheetRowCommand) {
        val aggregate = eventSourcingHandler.getById(command.timesheetId)
        aggregate.deleteTimesheetRow(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateApproverCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimesheetApprover(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateWeekStartingDateCommand) {
        if (!(isValidDateByFormat(command.weekStartingDate))) {
            throw InvalidDateFormatException("Invalid date format.")
        }
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimesheetWeekStartingDate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: TimesheetUpdateWeekEndingDateCommand) {
        if (!(isValidDateByFormat(command.weekEndingDate))) {
            throw InvalidDateFormatException("Invalid date format.")
        }
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimesheetWeekEndingDate(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreTimesheetReadDbCommand -> handle(command)
            is RegisterTimesheetCommand -> handle(command)
            is TimesheetUpdateDayDetailsCommand -> handle(command)
            is TimesheetUpdateAdminCommentsByIdCommand -> handle(command)
            is TimesheetUpdateEmployeeCommentsByIdCommand -> handle(command)
            is TimesheetUpdateManagerCommentsByIdCommand -> handle(command)
            is TimesheetUpdateStatusCommand -> handle(command)
            is RegisterTimesheetWithStartDateCommand -> handle(command)
            is TimesheetUpdateWithTimesheetRowsCommand -> handle(command)
            is DeleteTimesheetRowCommand -> handle(command)
            is TimesheetUpdateApproverCommand -> handle(command)
            is TimesheetUpdateWeekStartingDateCommand -> handle(command)
            is TimesheetUpdateWeekEndingDateCommand -> handle(command)
        }
    }
}
