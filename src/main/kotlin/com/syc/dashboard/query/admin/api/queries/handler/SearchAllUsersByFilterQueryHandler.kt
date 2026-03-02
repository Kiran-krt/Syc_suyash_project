package com.syc.dashboard.query.admin.api.queries.handler

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.api.queries.SearchAllUsersByFilterQuery
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import reactor.core.publisher.Flux

class SearchAllUsersByFilterQueryHandler(
    private val adminReactiveRepository: AdminReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,

) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchAllUsersByFilterQuery

        return Flux.concat(
            employeeReactiveRepository
                .findByTenantIdAndStatusInAndFirstNameContainsIgnoreCaseAndRoleInOrderByFirstName(
                    tenantId = query.tenantId,
                    status = query.status,
                    firstName = query.name,
                    roles = listOf(UserRole.MANAGER, UserRole.EMPLOYEE, UserRole.SUPERVISOR),
                ),
            adminReactiveRepository
                .findByTenantIdAndFirstNameContainsIgnoreCaseOrderByFirstName(
                    tenantId = query.tenantId,
                    firstName = query.name,
                ),
        ).map {
            EntityDtoConversion.toDto(it, EmployeeDto::class)
        }
    }
}
