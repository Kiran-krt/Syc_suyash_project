package com.syc.dashboard.query.employee.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.employee.api.queries.SearchAllEmployeeBySupervisorIdQuery
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import reactor.core.publisher.Flux

class SearchAllEmployeeBySupervisorIdQueryHandler(
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchAllEmployeeBySupervisorIdQuery
        return employeeReactiveRepository
            .findByTenantIdAndSupervisorListOrderByFirstName(
                tenantId = query.tenantId,
                supervisorList = listOf(query.supervisorId),
            )
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
    }
}
