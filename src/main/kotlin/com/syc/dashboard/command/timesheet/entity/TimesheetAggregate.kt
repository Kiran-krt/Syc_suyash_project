package com.syc.dashboard.command.timesheet.entity

import com.syc.dashboard.command.timesheet.api.commands.*
import com.syc.dashboard.command.timesheet.exceptions.TimesheetStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.utils.DateUtils
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import com.syc.dashboard.shared.timesheet.events.*
import java.util.*

class TimesheetAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    private var userId: String = ""
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS
    private var weekStartingDate: String = ""
    private var weekEndingDate: String = ""
    private var approvedByUserId: String = ""
    private var commentsByEmployee: String = ""
    private var commentsByManager: String = ""
    private var commentsByAdmin: String = ""
    private var submittedByName: String = ""
    private var timesheetRows: List<TimesheetRowDto> = mutableListOf()

    constructor(command: RegisterTimesheetCommand) : this() {
        val createdDate = Date()
        val timesheetRowId = UUID.randomUUID().toString()

        val timesheetRow: List<TimesheetRowDto> = listOf(
            TimesheetRowDto(
                tenantId = command.tenantId,
                timesheetId = command.id,
                id = timesheetRowId,
                weeklyDetails = DateUtils.getTimesheetEmptyWeeklyDetails(
                    timesheetId = command.id,
                    timesheetRowId = timesheetRowId,
                ),
            ),
        )

        raiseEvent(
            TimesheetRegisteredEvent(
                id = command.id,
                userId = command.userId,
                status = command.status,
                weekStartingDate = command.weekStartingDate,
                weekEndingDate = command.weekEndingDate,
                approvedByUserId = command.approvedByUserId,
                commentsByEmployee = command.commentsByEmployee,
                commentsByManager = command.commentsByManager,
                commentsByAdmin = command.commentsByAdmin,
                submittedByName = command.submittedByName,
                timesheetRows = timesheetRow,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        userId = event.userId
        status = event.status
        weekStartingDate = event.weekStartingDate
        weekEndingDate = event.weekEndingDate
        approvedByUserId = event.approvedByUserId
        commentsByEmployee = event.commentsByEmployee
        commentsByManager = event.commentsByManager
        commentsByAdmin = event.commentsByAdmin
        submittedByName = event.submittedByName
        timesheetRows = event.timesheetRows
    }
    constructor (command: RegisterTimesheetWithStartDateCommand) : this() {
        val createdDate = Date()

        val timesheetRowId = UUID.randomUUID().toString()
        val timesheetRow: List<TimesheetRowDto> = listOf(
            TimesheetRowDto(
                tenantId = command.tenantId,
                timesheetId = command.id,
                id = timesheetRowId,
                weeklyDetails = DateUtils.getTimesheetEmptyWeeklyDetailsByStartDate(
                    weekStartingDate = command.weekStartingDate,
                    timesheetId = command.id,
                    timesheetRowId = timesheetRowId,
                ),
            ),
        )
        raiseEvent(
            TimesheetRegisteredEvent(
                id = command.id,
                userId = command.userId,
                status = command.status,
                weekStartingDate = command.weekStartingDate,
                weekEndingDate = command.weekEndingDate,
                approvedByUserId = command.approvedByUserId,
                timesheetRows = timesheetRow,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun updateDayData(command: TimesheetUpdateDayDetailsCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet Day Details Update Status Exception!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetDayDetailsUpdatedByIdEvent(
                id = command.id,
                timesheetRowId = command.timesheetRowId,
                numberOfHours = command.numberOfHours,
                day = command.day,
                comment = command.comment,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetDayDetailsUpdatedByIdEvent) {
        buildAggregateRoot(event)
        val timesheetRowDto =
            timesheetRows.find { timesheetRowDto -> timesheetRowDto.id == event.timesheetRowId }

        val dayDetailsDto =
            timesheetRowDto?.weeklyDetails?.find { dayDetailsDto -> dayDetailsDto.day == event.day }

        if (dayDetailsDto != null) {
            dayDetailsDto.numberOfHours = event.numberOfHours
            dayDetailsDto.comment = event.comment
        }
    }

    fun updateCommentsByAdmin(command: TimesheetUpdateAdminCommentsByIdCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet comments by admin cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetAdminCommentsUpdatedByIdEvent(
                id = command.id,
                commentsByAdmin = command.commentsByAdmin,
                status = command.status,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetAdminCommentsUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        commentsByAdmin = event.commentsByAdmin
        status = event.status
    }

    fun updateCommentsByEmployee(command: TimesheetUpdateEmployeeCommentsByIdCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet comments by employee cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetEmployeeCommentsUpdatedByIdEvent(
                id = command.id,
                submittedByName = command.submittedByName,
                commentsByEmployee = command.commentsByEmployee,
                status = command.status,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetEmployeeCommentsUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        submittedByName = event.submittedByName
        commentsByEmployee = event.commentsByEmployee
        status = event.status
    }

    fun updateCommentsByManager(command: TimesheetUpdateManagerCommentsByIdCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet comments by manager cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetManagerCommentsUpdatedByIdEvent(
                id = command.id,
                commentsByManager = command.commentsByManager,
                status = command.status,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetManagerCommentsUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        commentsByManager = event.commentsByManager
        status = event.status
    }

    fun updateTimesheetStatus(command: TimesheetUpdateStatusCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet status cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetStatusUpdatedEvent(
                id = command.id,
                status = command.status,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun deleteTimesheetRow(command: DeleteTimesheetRowCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "TimesheetRow can be deleted!",
            )
        }
        raiseEvent(
            TimesheetRowDeletedEvent(
                id = command.id,
                timesheetId = command.timesheetId,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetRowDeletedEvent) {
        buildAggregateRoot(event)
        val timesheetRowDto =
            timesheetRows.find { timesheetRowDto -> timesheetRowDto.id == event.id }

        // TODO remove row from list
        if (timesheetRowDto != null) {
            timesheetRowDto.status = event.status
        }
    }

    fun updateTimesheetRows(command: TimesheetUpdateWithTimesheetRowsCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet rows can not be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetUpdatedWithTimesheetRowsEvent(
                id = command.id,
                timesheetRows = command.timesheetRows,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetUpdatedWithTimesheetRowsEvent) {
        buildAggregateRoot(event)
        timesheetRows = event.timesheetRows
    }

    fun updateTimesheetApprover(command: TimesheetUpdateApproverCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet approver cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetApproverUpdatedEvent(
                id = command.id,
                approvedByUserId = command.approvedByUserId,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetApproverUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        approvedByUserId = event.approvedByUserId
    }

    fun updateTimesheetWeekStartingDate(command: TimesheetUpdateWeekStartingDateCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet week starting date cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetWeekStartingDateUpdatedByIdEvent(
                id = command.id,
                weekStartingDate = command.weekStartingDate,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetWeekStartingDateUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        weekStartingDate = event.weekStartingDate
    }

    fun updateTimesheetWeekEndingDate(command: TimesheetUpdateWeekEndingDateCommand) {
        if (!active) {
            throw TimesheetStateChangeNotAllowedForInactiveStatusException(
                "Timesheet week ending date cannot be updated!",
            )
        }
        val updatedOn = Date()
        raiseEvent(
            TimesheetWeekEndingDateUpdatedByIdEvent(
                id = command.id,
                weekEndingDate = command.weekEndingDate,
                updatedOn = updatedOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TimesheetWeekEndingDateUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        weekEndingDate = event.weekEndingDate
    }
}
