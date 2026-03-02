package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.common.utils.DateUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.EmptyBaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.settings.repository.jpa.SettingsRepository
import com.syc.dashboard.query.timesheet.api.queries.handler.*
import com.syc.dashboard.query.timesheet.dto.TimesheetDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetReactiveRepository
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetRowReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux

@Service
class TimesheetQueryHandler @Autowired constructor(
    private val timesheetReactiveRepository: TimesheetReactiveRepository,
    private val timesheetRowReactiveRepository: TimesheetRowReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
    private val settingsRepository: SettingsRepository,
) : QueryHandler {

    private fun handle(query: FindTimesheetByIdQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndIdWithUserDetails(
                query.tenantId,
                query.id,
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
            .flux()
    }

    private fun handle(query: FindTimesheetByStatusQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndStatus(
                tenantId = query.tenantId,
                status = query.status,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetByStatusListQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndStatusIn(
                tenantId = query.tenantId,
                status = query.status,
            )
            .map { EntityDtoConversion.toDto(it, TimesheetDto::class) }
    }

    private fun handle(query: FindTimesheetSubmittedForApprovalForAdminQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findSubmittedForApprovalForAdmin(
                tenantId = query.tenantId,
                status = listOf(
                    TimesheetStatusEnum.SUBMITTED,
                    TimesheetStatusEnum.SUBMITTED_BY_SYSTEM,
                    TimesheetStatusEnum.UNLOCKED,
                ),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    Sort.by(Sort.Direction.DESC, "weekEnding_toDate")
                        .and(
                            Sort.by(Sort.Direction.ASC, "updatedOn")
                                .and(Sort.by(Sort.Direction.ASC, "firstName")),
                        ),
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetForManagerToReviewQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndManagerIdAndStatus(
                tenantId = query.tenantId,
                managerId = query.managerId,
                status = listOf(
                    TimesheetStatusEnum.SUBMITTED,
                    TimesheetStatusEnum.SUBMITTED_BY_SYSTEM,
                    TimesheetStatusEnum.UNLOCKED,
                ),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetForEmployeeToReviewQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndUserIdAndStatusIn(
                tenantId = query.tenantId,
                userId = query.userId,
                status = listOf(
                    TimesheetStatusEnum.IN_PROGRESS,
                    TimesheetStatusEnum.REJECTED_BY_MANAGER,
                    TimesheetStatusEnum.REJECTED_BY_ADMIN,
                ),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetByStatusAndWeekEndingDateQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndWeekEndingDateAndStatusIn(
                tenantId = query.tenantId,
                weekEndingDate = query.weekEndingDate,
                status = query.status,
            )
            .map { EntityDtoConversion.toDto(it, TimesheetDto::class) }
    }

    private fun handle(query: SearchTimesheetForEmployeeQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndUserIdAndStatusInAndWeekEndingDate(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.status ?: TimesheetStatusEnum.values().toList(),
                weekEndingDate = query.weekEndingDate,
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .mapNotNull {
                val timesheetDto: BaseDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (timesheetDto is TimesheetDto) {
                    if (it.employeeInfo != null) {
                        timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                    }
                    if (it.approvedByInfo != null) {
                        timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                    }
                }
                timesheetDto
            }
            .switchIfEmpty { Mono.just(EmptyBaseDto()) }
            .flux()
    }

    private fun handle(query: SearchTimesheetForManagerQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndUserIdAndManagerIdAndStatusAndWeekEndingDate(
                tenantId = query.tenantId,
                userId = query.userId,
                managerId = query.managerId,
                status = query.status ?: TimesheetStatusEnum.values().toList(),
                weekEndingDate = query.weekEndingDate,
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .mapNotNull {
                val timesheetDto: BaseDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (timesheetDto is TimesheetDto) {
                    if (it.employeeInfo != null) {
                        timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                    }
                    if (it.approvedByInfo != null) {
                        timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                    }
                }
                timesheetDto
            }
            .switchIfEmpty { Mono.just(EmptyBaseDto()) }
            .flux()
    }

    private fun handle(query: TimesheetForRejectedByManagerByEmployeeIdQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndStatusAndUserId(
                tenantId = query.tenantId,
                status = listOf(TimesheetStatusEnum.REJECTED_BY_MANAGER, TimesheetStatusEnum.REJECTED_BY_ADMIN),
                userId = query.userId,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetRowByIdQuery): Flux<out BaseDto> {
        return FindTimesheetRowByIdQueryHandler(
            timesheetRowReactiveRepository = timesheetRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindApprovedTimesheetForEmployeeQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndStatusAndUserId(
                tenantId = query.tenantId,
                userId = query.userId,
                status = listOf(
                    TimesheetStatusEnum.APPROVED_BY_MANAGER,
                    TimesheetStatusEnum.APPROVED_BY_ADMIN,
                    TimesheetStatusEnum.APPROVED_BY_SYSTEM,
                ),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindApprovedTimesheetForManagerQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findAllByTenantIdAndManagerIdAndStatusAndWeekStartingDateAndWeekEndingDate(
                tenantId = query.tenantId,
                managerId = query.managerId,
                status = listOf(
                    TimesheetStatusEnum.APPROVED_BY_MANAGER,
                    TimesheetStatusEnum.APPROVED_BY_ADMIN,
                    TimesheetStatusEnum.APPROVED_BY_SYSTEM,
                ),
                weekStartingDate = DateUtils.getDayOfMonth(
                    year = query.weekStartingYear!!,
                    month = query.weekStartingMonth!!,
                    day = query.weekStartingDay!!,
                ),
                weekEndingDate = DateUtils.getDayOfMonth(
                    year = query.weekEndingYear!!,
                    month = query.weekEndingMonth!!,
                    day = query.weekEndingDay!!,
                ),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindApprovedTimesheetForAdminQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findSubmittedForApprovalForAdminByFullNameAndWeekStartingDateAndWeekEndingDate(
                tenantId = query.tenantId,
                fullName = query.fullName,
                employeeId = query.employeeId,
                status = listOf(
                    TimesheetStatusEnum.APPROVED_BY_MANAGER,
                    TimesheetStatusEnum.APPROVED_BY_ADMIN,
                    TimesheetStatusEnum.APPROVED_BY_SYSTEM,
                ),
                weekStartingDate = DateUtils.getDayOfMonth(
                    year = query.weekStartingYear!!,
                    month = query.weekStartingMonth!!,
                    day = query.weekStartingDay!!,
                ),
                weekEndingDate = DateUtils.getDayOfMonth(
                    year = query.weekEndingYear!!,
                    month = query.weekEndingMonth!!,
                    day = query.weekEndingDay!!,
                ),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    Sort.by(Sort.Direction.DESC, "weekEnding_toDate")
                        .and(Sort.by(Sort.Direction.ASC, "updatedOn")),
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map {
                val timesheetDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (it.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.approvedByInfo != null) {
                    timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetCountForEmployeeByStatusQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .countByTenantIdAndUserIdAndStatus(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }

    private fun handle(query: FindTimesheetCountForManagerByStatusQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .timesheetCountByTenantIdAndApprovedByUserIdAndStatus(
                tenantId = query.tenantId,
                approvedByUserId = query.approvedByUserId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }

    private fun handle(query: FindTimesheetCountForAdminByStatusQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .timesheetCountByTenantIdAndStatus(
                tenantId = query.tenantId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }

    private fun handle(query: ExportTimesheetToExelByIdQuery): Flux<out BaseDto> {
        return ExportTimesheetToExelByIdQueryHandler(
            timesheetReactiveRepository = timesheetReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: SearchTimesheetListForEmployeeQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndUserIdAndFilterData(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.status ?: TimesheetStatusEnum.entries,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "weekEnding_toDate",
                ),
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .mapNotNull {
                val timesheetDto: BaseDto = EntityDtoConversion.toDto(it, TimesheetDto::class)
                if (timesheetDto is TimesheetDto) {
                    if (it.employeeInfo != null) {
                        timesheetDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                    }
                    if (it.approvedByInfo != null) {
                        timesheetDto.approvedByInfo = EntityDtoConversion.toDto(it.approvedByInfo!!, EmployeeDto::class)
                    }
                }
                timesheetDto
            }
    }

    private fun handle(query: FindTimesheetByStatusListAndBeforeTodaysDateQuery): Flux<out BaseDto> {
        return FindTimesheetByStatusListAndBeforeTodayDateQueryHandler(
            timesheetReactiveRepository = timesheetReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindTimesheetCountForSupervisorByStatusQuery): Flux<out BaseDto> {
        return FindTimesheetCountForSupervisorByStatusQueryHandler(
            timesheetReactiveRepository = timesheetReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: SearchTimesheetRowsQuery): Flux<out BaseDto> {
        return SearchTimesheetRowsQueryHandler(
            timesheetRowReactiveRepository = timesheetRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindTimesheetRowByIdExportToQuickBookQuery): Flux<out BaseDto> {
        return FindTimesheetRowByIdExportToQuickBookQueryHandler(
            timesheetRowReactiveRepository = timesheetRowReactiveRepository,
            settingsRepository = settingsRepository,
        ).handle(query)
    }

    private fun handle(query: SearchTimesheetRowsByFilterExportToExcelQuery): Flux<out BaseDto> {
        return SearchTimesheetRowsByFilterExportToExcelQueryHandler(
            timesheetRowReactiveRepository = timesheetRowReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindTimesheetByIdQuery -> handle(query)
            is FindTimesheetByStatusQuery -> handle(query)
            is FindTimesheetByStatusListQuery -> handle(query)
            is FindTimesheetSubmittedForApprovalForAdminQuery -> handle(query)
            is FindTimesheetForManagerToReviewQuery -> handle(query)
            is FindTimesheetForEmployeeToReviewQuery -> handle(query)
            is FindTimesheetByStatusAndWeekEndingDateQuery -> handle(query)
            is SearchTimesheetForEmployeeQuery -> handle(query)
            is SearchTimesheetForManagerQuery -> handle(query)
            is TimesheetForRejectedByManagerByEmployeeIdQuery -> handle(query)
            is FindTimesheetRowByIdQuery -> handle(query)
            is FindApprovedTimesheetForEmployeeQuery -> handle(query)
            is FindApprovedTimesheetForManagerQuery -> handle(query)
            is FindApprovedTimesheetForAdminQuery -> handle(query)
            is FindTimesheetCountForEmployeeByStatusQuery -> handle(query)
            is FindTimesheetCountForManagerByStatusQuery -> handle(query)
            is FindTimesheetCountForAdminByStatusQuery -> handle(query)
            is ExportTimesheetToExelByIdQuery -> handle(query)
            is SearchTimesheetListForEmployeeQuery -> handle(query)
            is FindTimesheetByStatusListAndBeforeTodaysDateQuery -> handle(query)
            is FindTimesheetCountForSupervisorByStatusQuery -> handle(query)
            is SearchTimesheetRowsQuery -> handle(query)
            is FindTimesheetRowByIdExportToQuickBookQuery -> handle(query)
            is SearchTimesheetRowsByFilterExportToExcelQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
