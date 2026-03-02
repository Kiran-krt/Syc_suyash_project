package com.syc.dashboard.query.timesheet.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.timesheet.entity.Timesheet
import com.syc.dashboard.query.timesheet.entity.TimesheetRow
import com.syc.dashboard.query.timesheet.repository.jpa.TimesheetRepository
import com.syc.dashboard.query.timesheet.repository.jpa.TimesheetRowRepository
import com.syc.dashboard.shared.timesheet.events.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TimesheetEventHandler @Autowired constructor(
    private val timesheetRepository: TimesheetRepository,
    private val timesheetRowRepository: TimesheetRowRepository,
) : EventHandler {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun on(event: TimesheetRegisteredEvent) {
        val timesheet = Timesheet(
            id = event.id,
            userId = event.userId,
            status = event.status,
            weekStartingDate = event.weekStartingDate,
            weekEndingDate = event.weekEndingDate,
            approvedByUserId = event.approvedByUserId,
            commentsByEmployee = event.commentsByEmployee,
            commentsByManager = event.commentsByManager,
            commentsByAdmin = event.commentsByAdmin,
            submittedByName = event.submittedByName,
            createdOn = event.createdDate,
        ).buildEntity(event) as Timesheet
        timesheetRepository.save(timesheet)

        val timesheetRow = event.timesheetRows.map {
            TimesheetRow(
                id = it.id,
                timesheetId = event.id,
                jobCodeId = it.jobCodeId,
                costCodeId = it.costCodeId,
                description = it.description,
                weeklyDetails = it.weeklyDetails,
            ).buildEntity(event) as TimesheetRow
        }
        timesheetRowRepository.saveAll(timesheetRow)
    }

    private fun on(event: TimesheetDayDetailsUpdatedByIdEvent) {
        val timesheetRowOptional = timesheetRowRepository
            .findByTenantIdAndIdAndTimesheetId(
                tenantId = event.tenantId,
                id = event.timesheetRowId,
                timesheetId = event.id,
            )
        if (timesheetRowOptional.isEmpty) {
            return
        }
        val dayDetails =
            timesheetRowOptional.get().weeklyDetails.find { dayDetailsDto -> dayDetailsDto.day == event.day }
        if (dayDetails != null) {
            dayDetails.numberOfHours = event.numberOfHours
            dayDetails.comment = event.comment
        }
        timesheetRowRepository.save(timesheetRowOptional.get())
    }

    private fun on(event: TimesheetAdminCommentsUpdatedByIdEvent) {
        val timesheetOptional = timesheetRepository
            .findById(event.id)
        if (timesheetOptional.isEmpty) {
            return
        }
        timesheetOptional.get().commentsByAdmin = event.commentsByAdmin
        timesheetOptional.get().status = event.status
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetRepository.save(timesheetOptional.get())
    }

    private fun on(event: TimesheetEmployeeCommentsUpdatedByIdEvent) {
        val timesheetOptional = timesheetRepository
            .findById(event.id)
        if (timesheetOptional.isEmpty) {
            return
        }
        timesheetOptional.get().commentsByEmployee = event.commentsByEmployee
        timesheetOptional.get().status = event.status
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetOptional.get().submittedByName = event.submittedByName
        timesheetRepository.save(timesheetOptional.get())
    }

    private fun on(event: TimesheetManagerCommentsUpdatedByIdEvent) {
        val timesheetOptional = timesheetRepository
            .findById(event.id)
        if (timesheetOptional.isEmpty) {
            return
        }
        timesheetOptional.get().commentsByManager = event.commentsByManager
        timesheetOptional.get().status = event.status
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetRepository.save(timesheetOptional.get())
    }

    private fun on(event: TimesheetStatusUpdatedEvent) {
        val timesheetOptional = timesheetRepository.findById(event.id)

        if (timesheetOptional.isEmpty) {
            return
        }

        timesheetOptional.get().status = event.status
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetRepository.save(timesheetOptional.get())
    }

    private fun on(event: TimesheetRowDeletedEvent) {
        val timesheetRowOptional = timesheetRowRepository
            .findByTenantIdAndTimesheetIdAndId(
                tenantId = event.tenantId,
                timesheetId = event.timesheetId,
                id = event.id,
            )
        if (timesheetRowOptional.isEmpty) {
            return
        }
        timesheetRowOptional.get().status = event.status
        timesheetRowRepository.save(timesheetRowOptional.get())
    }

    private fun on(event: TimesheetUpdatedWithTimesheetRowsEvent) {
        val timesheetEntityRows = event.timesheetRows.map {
            TimesheetRow(
                id = it.id,
                timesheetId = event.id,
                jobCodeId = it.jobCodeId,
                costCodeId = it.costCodeId,
                description = it.description,
                weeklyDetails = it.weeklyDetails,
            ).buildEntity(event) as TimesheetRow
        }
        timesheetRowRepository.saveAll(timesheetEntityRows)
    }

    private fun on(event: TimesheetApproverUpdatedEvent) {
        val timesheetOptional = timesheetRepository.findById(event.id)

        if (timesheetOptional.isEmpty) {
            return
        }

        timesheetOptional.get().approvedByUserId = event.approvedByUserId
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetRepository.save(timesheetOptional.get())
    }

    private fun on(event: TimesheetWeekStartingDateUpdatedByIdEvent) {
        val timesheetOptional = timesheetRepository.findById(event.id)

        if (timesheetOptional.isEmpty) {
            return
        }
        timesheetOptional.get().weekStartingDate = event.weekStartingDate
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetRepository.save(timesheetOptional.get())
    }

    private fun on(event: TimesheetWeekEndingDateUpdatedByIdEvent) {
        val timesheetOptional = timesheetRepository.findById(event.id)

        if (timesheetOptional.isEmpty) {
            return
        }
        timesheetOptional.get().weekEndingDate = event.weekEndingDate
        timesheetOptional.get().updatedOn = event.updatedOn
        timesheetRepository.save(timesheetOptional.get())
    }
    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is TimesheetRegisteredEvent -> on(event)
            is TimesheetDayDetailsUpdatedByIdEvent -> on(event)
            is TimesheetAdminCommentsUpdatedByIdEvent -> on(event)
            is TimesheetEmployeeCommentsUpdatedByIdEvent -> on(event)
            is TimesheetManagerCommentsUpdatedByIdEvent -> on(event)
            is TimesheetStatusUpdatedEvent -> on(event)
            is TimesheetRowDeletedEvent -> on(event)
            is TimesheetUpdatedWithTimesheetRowsEvent -> on(event)
            is TimesheetApproverUpdatedEvent -> on(event)
            is TimesheetWeekStartingDateUpdatedByIdEvent -> on(event)
            is TimesheetWeekEndingDateUpdatedByIdEvent -> on(event)
        }
    }
}
