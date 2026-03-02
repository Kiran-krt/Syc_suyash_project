package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.common.utils.DateUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetByStatusListAndBeforeTodaysDateQuery
import com.syc.dashboard.query.timesheet.dto.TimesheetDto
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetReactiveRepository
import reactor.core.publisher.Flux

class FindTimesheetByStatusListAndBeforeTodayDateQueryHandler(
    private val timesheetReactiveRepository: TimesheetReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindTimesheetByStatusListAndBeforeTodaysDateQuery
        return timesheetReactiveRepository
            .findByTenantIdAndStatusInAndBeforeCurrentDate(
                tenantId = query.tenantId,
                status = query.status,
                currentDate = DateUtils.getDateMMddyyyy(),
            )
            .map { EntityDtoConversion.toDto(it, TimesheetDto::class) }
    }
}
