package com.syc.dashboard.command.expensereport.entity

import com.syc.dashboard.command.expensereport.api.commands.*
import com.syc.dashboard.command.expensereport.exceptions.ExpenseReportStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto
import com.syc.dashboard.query.expensereport.entity.enums.*
import com.syc.dashboard.shared.expensereport.events.*
import java.util.*

class ExpenseReportAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var description: String = ""
    var status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS
    var periodFrom: String = ""
    var periodTo: String = ""
    var dateSubmitted: Date? = null
    var employeeSignature: String = ""
    var supervisorSignature: String = ""
    var reimbursementMadeOn: Date? = null
    var reimbursementMadeStatus: ReimbursementMadeStatusEnum? = null
    var employeeId: String = ""
    var supervisorId: String = ""
    var expenseRowsForEmployee: MutableList<ExpenseReportRowDto> = mutableListOf()
    var expenseRowsForSuyash: MutableList<ExpenseReportRowDto> = mutableListOf()
    var commentsByAdmin: String = ""
    var commentsByEmployee: String = ""
    var commentsBySupervisor: String = ""
    var adminSignature: String = ""
    var receiptNumber: String = ""
    var expenseReportRowsForEmployee: List<ExpenseReportRowDto> = mutableListOf()
    var expenseReportRowsForSuyash: List<ExpenseReportRowDto> = mutableListOf()

    class ExpenseReportRow(
        var id: String = "",
        var expenseReportId: String = "",
        var expenseTypeId: String = "",
        var expenseAmount: Double = 0.0,
        var expenseDescription: String = "",
        var jobCodeId: String = "",
        var costCodeId: String = "",
        var expenseMileage: Double = 0.0,
        var expenseMileageRate: Double = 0.655,
        var expenseDate: String = "MM/dd/YYYY",
        var expenseReportRowStatus: ExpenseReportRowStatusEnum = ExpenseReportRowStatusEnum.ACTIVE,
        var createdOn: Date = Date(),
        var expenseBy: ExpenseByEnum = ExpenseByEnum.EMPLOYEE,
        var receiptNumber: String = "",
        var updatedOn: Date = Date(),
        var receiptDocumentId: String = "",
    )

    constructor(command: AddExpenseReportCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            ExpenseReportAddedEvent(
                id = command.id,
                description = command.description,
                status = command.status,
                periodFrom = command.periodFrom,
                periodTo = command.periodTo,
                employeeId = command.employeeId,
                supervisorId = command.supervisorId,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportAddedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        periodFrom = event.periodFrom
        periodTo = event.periodTo
        employeeId = event.employeeId
        supervisorId = event.supervisorId
        description = event.description
        status = event.status
    }

    fun addExpenseReportRowForEmployee(command: AddExpenseRowForEmployeeCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense report row by employee cannot be added!",
            )
        }

        raiseEvent(
            ExpenseRowForEmployeeAddedEvent(
                id = command.id,
                expenseReportId = command.expenseReportId,
                expenseTypeId = command.expenseTypeId,
                expenseAmount = command.expenseAmount,
                expenseDescription = command.expenseDescription,
                jobCodeId = command.jobCodeId,
                costCodeId = command.costCodeId,
                expenseMileage = command.expenseMileage,
                expenseMileageRate = command.expenseMileageRate,
                expenseDate = command.expenseDate,
                expenseReportRowStatus = command.expenseReportRowStatus,
                expenseBy = ExpenseByEnum.EMPLOYEE,
                receiptNumber = command.receiptNumber,
                createdOn = command.createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseRowForEmployeeAddedEvent) {
        buildAggregateRoot(event)
        expenseRowsForEmployee.add(
            ExpenseReportRowDto(
                id = event.id,
                expenseReportId = event.expenseReportId,
                expenseTypeId = event.expenseTypeId,
                expenseAmount = event.expenseAmount,
                expenseDescription = event.expenseDescription,
                jobCodeId = event.jobCodeId,
                costCodeId = event.costCodeId,
                expenseMileage = event.expenseMileage,
                expenseMileageRate = event.expenseMileageRate,
                expenseDate = event.expenseDate,
                expenseReportRowStatus = event.expenseReportRowStatus,
                expenseBy = event.expenseBy,
                receiptNumber = event.receiptNumber,
                createdOn = event.createdOn,
            ),
        )
    }

    fun deleteExpenseReportRow(command: DeleteExpenseReportRowCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense Report Row Status Delete Exception!",
            )
        }
        raiseEvent(
            ExpenseReportRowDeletedEvent(
                id = command.id,
                expenseReportId = command.expenseReportId,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportRowDeletedEvent) {
        buildAggregateRoot(event)
        val expenseReportRow = expenseRowsForEmployee.find { expenseReportRow -> expenseReportRow.id == event.id }
        if (expenseReportRow != null) {
            expenseReportRow.expenseReportRowStatus = ExpenseReportRowStatusEnum.DELETED
        }
    }

    fun deleteExpenseReport(command: DeleteExpenseReportCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense Report Delete Exception!",
            )
        }
        raiseEvent(
            ExpenseReportDeletedEvent(
                id = command.id,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportDeletedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = ExpenseReportStatusEnum.DELETED
    }

    fun updateExpenseReportStatus(command: UpdateExpenseReportStatusCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense Report Status Update Exception!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            ExpenseReportStatusUpdatedEvent(
                id = command.id,
                status = command.status!!,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun reviewExpenseReportByAdmin(command: ReviewExpenseReportByAdminCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Review Expense Report By Admin Exception!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            ExpenseReportReviewedByAdminEvent(
                id = command.id,
                status = command.status,
                commentsByAdmin = command.commentsByAdmin,
                adminSignature = command.adminSignature,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportReviewedByAdminEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
        commentsByAdmin = event.commentsByAdmin
        adminSignature = event.adminSignature
    }

    fun submitExpenseReportByEmployee(command: SubmitExpenseReportByEmployeeCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense Report Submit By Employee Exception!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            ExpenseReportSubmittedByEmployeeEvent(
                id = command.id,
                status = command.status,
                commentsByEmployee = command.commentsByEmployee,
                employeeSignature = command.employeeSignature,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportSubmittedByEmployeeEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
        commentsByEmployee = event.commentsByEmployee
        employeeSignature = event.employeeSignature
    }

    fun reviewExpenseReportBySupervisor(command: ReviewExpenseReportBySupervisorCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense Report Review By Superviser Exception!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            ExpenseReportReviewedBySupervisorEvent(
                id = command.id,
                status = command.status,
                commentsBySupervisor = command.commentsBySupervisor,
                supervisorSignature = command.supervisorSignature,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportReviewedBySupervisorEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
        commentsBySupervisor = event.commentsBySupervisor
        supervisorSignature = event.supervisorSignature
    }

    fun updateExpenseReportAllFields(command: ExpenseReportUpdateAllFieldsCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense Report Update All Fields Exception!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            ExpenseReportAllFieldsUpdatedEvent(
                id = command.id,
                periodFrom = command.periodFrom,
                periodTo = command.periodTo,
                description = command.description,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseReportAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        periodFrom = event.periodFrom
        periodTo = event.periodTo
        description = event.description
    }

    fun addExpenseRowsForSuyash(command: AddExpenseRowForSuyashCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense rows for suyash cannot be added!",
            )
        }

        raiseEvent(
            ExpenseRowForSuyashAddedEvent(
                id = command.id,
                expenseReportId = command.expenseReportId,
                expenseTypeId = command.expenseTypeId,
                expenseAmount = command.expenseAmount,
                expenseDescription = command.expenseDescription,
                jobCodeId = command.jobCodeId,
                costCodeId = command.costCodeId,
                expenseMileage = command.expenseMileage,
                expenseMileageRate = command.expenseMileageRate,
                expenseDate = command.expenseDate,
                expenseReportRowStatus = command.expenseReportRowStatus,
                expenseBy = ExpenseByEnum.SUYASH,
                receiptNumber = command.receiptNumber,
                createdOn = command.createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseRowForSuyashAddedEvent) {
        buildAggregateRoot(event)
        ExpenseReportRow(
            id = event.id,
            expenseReportId = event.expenseReportId,
            expenseTypeId = event.expenseTypeId,
            expenseAmount = event.expenseAmount,
            expenseDescription = event.expenseDescription,
            jobCodeId = event.jobCodeId,
            costCodeId = event.costCodeId,
            expenseMileage = event.expenseMileage,
            expenseMileageRate = event.expenseMileageRate,
            expenseDate = event.expenseDate,
            expenseReportRowStatus = event.expenseReportRowStatus,
            expenseBy = event.expenseBy,
            receiptNumber = event.receiptNumber,
            createdOn = event.createdOn,
        )
    }

    fun updateExpenseRowsForSuyash(command: UpdateExpenseRowsForSuyashCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense rows for suyash can not be updated!",
            )
        }
        raiseEvent(
            ExpenseRowsForSuyashUpdatedEvent(
                id = command.id,
                expenseRowsForSuyash = command.expenseRowsForSuyash,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseRowsForSuyashUpdatedEvent) {
        buildAggregateRoot(event)
        expenseRowsForSuyash = event.expenseRowsForSuyash
    }

    fun updateExpenseRowsForEmployee(command: UpdateExpenseRowsForEmployeeCommand) {
        if (!active) {
            throw ExpenseReportStateChangeNotAllowedForInactiveStatusException(
                "Expense rows for employee can not be updated!",
            )
        }
        raiseEvent(
            ExpenseRowsForEmployeeUpdatedEvent(
                id = command.id,
                expenseRowsForEmployee = command.expenseRowsForEmployee,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseRowsForEmployeeUpdatedEvent) {
        buildAggregateRoot(event)
        expenseRowsForEmployee = event.expenseRowsForEmployee
    }
}
