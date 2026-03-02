package com.syc.dashboard.query.employee.api.queries.handler

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.employee.api.queries.SearchAllActiveSupervisorListQuery
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import reactor.core.publisher.Flux

class SearchAllActiveSupervisorListQueryHandler(
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchAllActiveSupervisorListQuery
        return employeeReactiveRepository
            .findAllByTenantIdAndRoleInAndStatusInOrderByFirstName(
                tenantId = query.tenantId,
                role = listOf(UserRole.SUPERVISOR),
                status = listOf(EmployeeStatusEnum.ACTIVE),
            )
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
    }
}
