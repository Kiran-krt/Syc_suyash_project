package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.settings.dto.PayrollItemDto
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetRowByIdQuery
import com.syc.dashboard.query.timesheet.dto.DayDetailsDto
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetRowReactiveRepository
import reactor.core.publisher.Flux

class FindTimesheetRowByIdQueryHandler(
    private val timesheetRowReactiveRepository: TimesheetRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindTimesheetRowByIdQuery
        return timesheetRowReactiveRepository
            .findByTenantIdAndTimesheetIdWithJobCodeAndCostCodeDetails(
                tenantId = query.tenantId,
                timesheetId = query.timesheetId,
            )
            .map {
                val timesheetRowDto = EntityDtoConversion.toDto(it, TimesheetRowDto::class)
                if (it.jobCodeInfo != null) {
                    timesheetRowDto.jobCodeInfo = EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                }
                if (it.costCodeInfo != null) {
                    timesheetRowDto.costCodeInfo = EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                }
                timesheetRowDto.weeklyDetails = it.weeklyDetails.map { dayDetail ->
                    val dayDetailDto = EntityDtoConversion.copyFromJson(dayDetail, DayDetailsDto::class.java)

                    dayDetailDto.payrollItemId = dayDetail.payrollItemId

                    if (dayDetail.payrollItemInfo != null) {
                        dayDetailDto.payrollItemInfo =
                            EntityDtoConversion.copyFromJson(dayDetail.payrollItemInfo!!, PayrollItemDto::class.java)
                    }
                    dayDetailDto
                }
                timesheetRowDto
            }
    }
}
