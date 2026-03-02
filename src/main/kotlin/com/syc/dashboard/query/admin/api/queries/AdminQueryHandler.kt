package com.syc.dashboard.query.admin.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.api.queries.handler.SearchAllUsersByFilterQueryHandler
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.admin.exceptions.AdminNotFoundException
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux

@Service
class AdminQueryHandler @Autowired constructor(
    private val adminReactiveRepository: AdminReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindAdminByIdQuery): Flux<out BaseDto> {
        return adminReactiveRepository
            .findByTenantIdAndId(query.tenantId, query.id)
            .map { EntityDtoConversion.toDto(it, AdminDto::class) }
            .switchIfEmpty { throw AdminNotFoundException("Admin not found with id ${query.id}.") }
            .flux()
    }

    private fun handle(query: FindAdminByEmailQuery): Flux<out BaseDto> {
        return adminReactiveRepository
            .findFirstByTenantIdAndEmail(query.tenantId, query.email)
            .map { EntityDtoConversion.toDto(it, AdminDto::class) }
            .toFlux()
    }

    private fun handle(query: SearchAllUsersByFilterQuery): Flux<out BaseDto> {
        return SearchAllUsersByFilterQueryHandler(
            employeeReactiveRepository = employeeReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindAdminByIdQuery -> handle(query)
            is FindAdminByEmailQuery -> handle(query)
            is SearchAllUsersByFilterQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
