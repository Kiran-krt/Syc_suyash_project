package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetCountForSupervisorByStatusQuery
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetReactiveRepository
import reactor.core.publisher.Flux

class FindTimesheetCountForSupervisorByStatusQueryHandler(
    private val timesheetReactiveRepository: TimesheetReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindTimesheetCountForSupervisorByStatusQuery
        return timesheetReactiveRepository
            .countByTenantIdAndUserIdAndStatus(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .flux()
    }
}
