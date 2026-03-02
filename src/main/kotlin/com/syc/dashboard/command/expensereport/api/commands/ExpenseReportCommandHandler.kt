package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.command.expensereport.entity.ExpenseReportAggregate
import com.syc.dashboard.command.expensereport.exceptions.ExpenseReportNotExistException
import com.syc.dashboard.command.expensereport.exceptions.ExpenseReportPeriodAlreadyExistException
import com.syc.dashboard.command.expensereport.repository.jpa.ExpenseReportEventStoreRepository
import com.syc.dashboard.framework.common.exceptions.InvalidDateFormatException
import com.syc.dashboard.framework.common.utils.DateUtils.isValidExpenseDate
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.expensereport.events.ExpenseReportAddedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExpenseReportCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<ExpenseReportAggregate>,
    private val eventStoreRepository: ExpenseReportEventStoreRepository,
) : CommandHandler {

    private fun handle(command: AddExpenseReportCommand) {
        if (command.periodFrom.isBlank() || command.periodTo.isBlank()) {
            throw IllegalArgumentException("Period from and period to must not be empty")
        }

        val event = eventStoreRepository.findByEventTypeAndEventDataPeriodFromAndEmployeeId(
            eventType = ExpenseReportAddedEvent::class.java.typeName,
            tenantId = command.tenantId,
            periodFrom = command.periodFrom,
            employeeId = command.employeeId,
        )

        if (event.isNotEmpty()) {
            throw ExpenseReportPeriodAlreadyExistException("Specified period '${command.periodFrom}' - '${command.periodTo}' is already added.")
        }

        val aggregate = ExpenseReportAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddExpenseRowForEmployeeCommand) {
        if (!isValidExpenseDate(command.expenseDate)) {
            throw InvalidDateFormatException("Please add expense date.")
        }
        val aggregate = eventSourcingHandler.getById(command.expenseReportId)
        aggregate.addExpenseReportRowForEmployee(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: DeleteExpenseReportRowCommand) {
        val aggregate = eventSourcingHandler.getById(command.expenseReportId)
        aggregate.deleteExpenseReportRow(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: DeleteExpenseReportCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.deleteExpenseReport(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateExpenseReportStatusCommand) {
        if (command.status == null) {
            throw ExpenseReportNotExistException("Specify correct status.")
        }

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateExpenseReportStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ReviewExpenseReportByAdminCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.reviewExpenseReportByAdmin(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SubmitExpenseReportByEmployeeCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.submitExpenseReportByEmployee(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ReviewExpenseReportBySupervisorCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.reviewExpenseReportBySupervisor(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ExpenseReportUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateExpenseReportAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddExpenseRowForSuyashCommand) {
        if (!isValidExpenseDate(command.expenseDate)) {
            throw InvalidDateFormatException("Please add expense date.")
        }
        val aggregate = eventSourcingHandler.getById(command.expenseReportId)
        aggregate.addExpenseRowsForSuyash(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateExpenseRowsForSuyashCommand) {
        command.expenseRowsForSuyash.map { expenseRow ->
            if (!isValidExpenseDate(expenseRow.expenseDate)) {
                throw InvalidDateFormatException("Please add expense date.")
            }
            expenseRow
        }
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateExpenseRowsForSuyash(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: UpdateExpenseRowsForEmployeeCommand) {
        command.expenseRowsForEmployee.map { expenseRow ->
            if (!isValidExpenseDate(expenseRow.expenseDate)) {
                throw InvalidDateFormatException("Please add expense date.")
            }
            expenseRow
        }
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateExpenseRowsForEmployee(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is AddExpenseReportCommand -> handle(command)
            is AddExpenseRowForEmployeeCommand -> handle(command)
            is DeleteExpenseReportRowCommand -> handle(command)
            is DeleteExpenseReportCommand -> handle(command)
            is UpdateExpenseReportStatusCommand -> handle(command)
            is ReviewExpenseReportByAdminCommand -> handle(command)
            is SubmitExpenseReportByEmployeeCommand -> handle(command)
            is ReviewExpenseReportBySupervisorCommand -> handle(command)
            is ExpenseReportUpdateAllFieldsCommand -> handle(command)
            is AddExpenseRowForSuyashCommand -> handle(command)
            is UpdateExpenseRowsForEmployeeCommand -> handle(command)
            is UpdateExpenseRowsForSuyashCommand -> handle(command)
        }
    }
}
