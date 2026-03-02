package com.syc.dashboard.query.employee.api.queries.handler

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.employee.api.queries.SearchAllSupervisorListQuery
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import reactor.core.publisher.Flux

class SearchAllSupervisorListQueryHandler(
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchAllSupervisorListQuery
        return employeeReactiveRepository
            .findAllByTenantIdAndRoleInOrderByFirstName(
                tenantId = query.tenantId,
                role = listOf(UserRole.SUPERVISOR),
            )
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
    }
}
