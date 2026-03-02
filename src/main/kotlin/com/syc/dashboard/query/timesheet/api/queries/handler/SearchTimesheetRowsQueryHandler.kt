package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.timesheet.api.queries.SearchTimesheetRowsQuery
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetRowReactiveRepository
import reactor.core.publisher.Flux

class SearchTimesheetRowsQueryHandler(
    private val timesheetRowReactiveRepository: TimesheetRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchTimesheetRowsQuery

        return timesheetRowReactiveRepository
            .findByTenantIdAndJobCode(
                tenantId = query.tenantId,
                jobcode = query.jobcode,
                startDate = query.startDate,
                endDate = query.endDate,
            )
    }
}
