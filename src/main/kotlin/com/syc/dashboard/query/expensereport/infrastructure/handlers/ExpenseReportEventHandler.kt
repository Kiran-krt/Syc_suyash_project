package com.syc.dashboard.query.expensereport.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.expensereport.entity.ExpenseReport
import com.syc.dashboard.query.expensereport.entity.ExpenseReportRow
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportRowStatusEnum
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import com.syc.dashboard.query.expensereport.repository.jpa.ExpenseReportRepository
import com.syc.dashboard.query.expensereport.repository.jpa.ExpenseReportRowRepository
import com.syc.dashboard.shared.expensereport.events.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExpenseReportEventHandler @Autowired constructor(
    private val expenseReportRepository: ExpenseReportRepository,
    private val expenseReportRowRepository: ExpenseReportRowRepository,
) : EventHandler {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun on(event: ExpenseReportAddedEvent) {
        val expenseReport = ExpenseReport(
            id = event.id,
            periodFrom = event.periodFrom,
            periodTo = event.periodTo,
            employeeId = event.employeeId,
            supervisorId = event.supervisorId,
            description = event.description,
            status = event.status,
            createdOn = event.createdDate,
        ).buildEntity(event) as ExpenseReport
        expenseReportRepository.save(expenseReport)
    }

    private fun on(event: ExpenseRowForEmployeeAddedEvent) {
        val expenseRow = ExpenseReportRow(
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
        ).buildEntity(event) as ExpenseReportRow
        expenseReportRowRepository.save(expenseRow)
    }
    private fun on(event: ExpenseReportRowDeletedEvent) {
        val expenseReportRowOptional = expenseReportRowRepository
            .findByTenantIdAndIdAndExpenseReportId(
                tenantId = event.tenantId,
                id = event.id,
                expenseReportId = event.expenseReportId,
            )
        if (expenseReportRowOptional.isEmpty) {
            return
        }
        val expenseReportRow = expenseReportRowOptional.get()
        expenseReportRow.expenseReportRowStatus = ExpenseReportRowStatusEnum.DELETED
        expenseReportRowRepository.save(expenseReportRow)
    }

    private fun on(event: ExpenseReportDeletedEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (expenseReportOptional.isEmpty) {
            return
        }
        val expenseReport = expenseReportOptional.get()
        expenseReport.status = ExpenseReportStatusEnum.DELETED
        expenseReportRepository.save(expenseReport)
    }

    private fun on(event: ExpenseReportStatusUpdatedEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (expenseReportOptional.isEmpty) {
            return
        }
        expenseReportOptional.get().status = event.status
        expenseReportOptional.get().updatedOn = event.updatedOn
        expenseReportRepository.save(expenseReportOptional.get())
    }

    private fun on(event: ExpenseReportReviewedByAdminEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (expenseReportOptional.isEmpty) {
            return
        }
        expenseReportOptional.get().status = event.status
        expenseReportOptional.get().commentsByAdmin = event.commentsByAdmin
        expenseReportOptional.get().adminSignature = event.adminSignature
        expenseReportOptional.get().updatedOn = event.updatedOn
        expenseReportRepository.save(expenseReportOptional.get())
    }

    private fun on(event: ExpenseReportSubmittedByEmployeeEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (expenseReportOptional.isEmpty) {
            return
        }
        expenseReportOptional.get().status = event.status
        expenseReportOptional.get().commentsByEmployee = event.commentsByEmployee
        expenseReportOptional.get().employeeSignature = event.employeeSignature
        expenseReportOptional.get().updatedOn = event.updatedOn
        expenseReportRepository.save(expenseReportOptional.get())
    }

    private fun on(event: ExpenseReportReviewedBySupervisorEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (expenseReportOptional.isEmpty) {
            return
        }
        expenseReportOptional.get().status = event.status
        expenseReportOptional.get().commentsBySupervisor = event.commentsBySupervisor
        expenseReportOptional.get().supervisorSignature = event.supervisorSignature
        expenseReportOptional.get().updatedOn = event.updatedOn
        expenseReportRepository.save(expenseReportOptional.get())
    }

    private fun on(event: ExpenseReportAllFieldsUpdatedEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (expenseReportOptional.isEmpty) {
            return
        }
        expenseReportOptional.get().periodFrom = event.periodFrom
        expenseReportOptional.get().periodTo = event.periodTo
        expenseReportOptional.get().description = event.description
        expenseReportOptional.get().updatedOn = event.updatedOn
        expenseReportRepository.save(expenseReportOptional.get())
    }

    private fun on(event: ExpenseRowForSuyashAddedEvent) {
        val expenseRow = ExpenseReportRow(
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
        ).buildEntity(event) as ExpenseReportRow
        expenseReportRowRepository.save(expenseRow)
    }

    private fun on(event: ExpenseRowsForSuyashUpdatedEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (!expenseReportOptional.isEmpty) {
            val expenseReport = expenseReportOptional.get()
            expenseReportOptional.get().updatedOn = event.updatedOn
            expenseReportRepository.save(expenseReport)
        }

        val expenseRowForSuyash = event.expenseRowsForSuyash.map {
            ExpenseReportRow(
                id = it.id,
                expenseReportId = event.id,
                expenseTypeId = it.expenseTypeId,
                expenseAmount = it.expenseAmount,
                expenseDescription = it.expenseDescription,
                jobCodeId = it.jobCodeId,
                costCodeId = it.costCodeId,
                expenseMileage = it.expenseMileage,
                expenseMileageRate = it.expenseMileageRate,
                expenseDate = it.expenseDate,
                expenseReportRowStatus = it.expenseReportRowStatus,
                expenseBy = it.expenseBy,
                receiptNumber = it.receiptNumber,
                receiptDocumentId = it.receiptDocumentId,
            ).buildEntity(event) as ExpenseReportRow
        }
        expenseReportRowRepository.saveAll(expenseRowForSuyash)
    }

    private fun on(event: ExpenseRowsForEmployeeUpdatedEvent) {
        val expenseReportOptional = expenseReportRepository.findById(event.id)
        if (!expenseReportOptional.isEmpty) {
            val expenseReport = expenseReportOptional.get()
            expenseReportOptional.get().updatedOn = event.updatedOn
            expenseReportRepository.save(expenseReport)
        }

        val expenseRowForEmployee = event.expenseRowsForEmployee.map {
            ExpenseReportRow(
                id = it.id,
                expenseReportId = event.id,
                expenseTypeId = it.expenseTypeId,
                expenseAmount = it.expenseAmount,
                expenseDescription = it.expenseDescription,
                jobCodeId = it.jobCodeId,
                costCodeId = it.costCodeId,
                expenseMileage = it.expenseMileage,
                expenseMileageRate = it.expenseMileageRate,
                expenseDate = it.expenseDate,
                expenseReportRowStatus = it.expenseReportRowStatus,
                expenseBy = it.expenseBy,
                receiptNumber = it.receiptNumber,
                receiptDocumentId = it.receiptDocumentId,
            ).buildEntity(event) as ExpenseReportRow
        }
        expenseReportRowRepository.saveAll(expenseRowForEmployee)
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is ExpenseReportAddedEvent -> on(event)
            is ExpenseRowForEmployeeAddedEvent -> on(event)
            is ExpenseReportRowDeletedEvent -> on(event)
            is ExpenseReportDeletedEvent -> on(event)
            is ExpenseReportStatusUpdatedEvent -> on(event)
            is ExpenseReportReviewedByAdminEvent -> on(event)
            is ExpenseReportSubmittedByEmployeeEvent -> on(event)
            is ExpenseReportReviewedBySupervisorEvent -> on(event)
            is ExpenseReportAllFieldsUpdatedEvent -> on(event)
            is ExpenseRowForSuyashAddedEvent -> on(event)
            is ExpenseRowsForSuyashUpdatedEvent -> on(event)
            is ExpenseRowsForEmployeeUpdatedEvent -> on(event)
        }
    }
}
