package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.EmptyBaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.api.queries.handler.EmployeeSearchByFilterQueryHandler
import com.syc.dashboard.query.employee.api.queries.handler.SearchAllActiveSupervisorListQueryHandler
import com.syc.dashboard.query.employee.api.queries.handler.SearchAllEmployeeBySupervisorIdQueryHandler
import com.syc.dashboard.query.employee.api.queries.handler.SearchAllSupervisorListQueryHandler
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.employee.dto.UserDto
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import com.syc.dashboard.query.employee.exceptions.EmployeeNotFoundException
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.timesheet.dto.TimesheetDto
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux

@Service
class EmployeeQueryHandler @Autowired constructor(
    private val employeeReactiveRepository: EmployeeReactiveRepository,
    private val timesheetReactiveRepository: TimesheetReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindEmployeeByEmailQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findFirstByTenantIdAndEmail(query.tenantId, query.email)
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
            .toFlux()
    }

    private fun handle(query: FindEmployeeByIdQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findByTenantIdAndId(query.tenantId, query.id)
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
            .switchIfEmpty { throw EmployeeNotFoundException("Employee not found with id ${query.id}.") }
            .toFlux()
    }

    private fun handle(query: SearchEmployeeByFilterQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findByTenantIdAndFirstNameContainsIgnoreCaseAndStatusInAndRoleIn(
                tenantId = query.tenantId,
                firstName = query.name,
                status = query.status ?: EmployeeStatusEnum.values().toList(),
                roles = query.roles ?: UserRole.values().toList(),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "firstName",
                ),
            )
            .flatMap { employee ->
                if (employee.managerInfo == null) {
                    employeeReactiveRepository.findByTenantIdAndId(
                        tenantId = employee.tenantId,
                        id = employee.managerId,
                    )
                        .map {
                            employee.managerInfo = it
                            employee
                        }
                        .switchIfEmpty { Mono.just(employee) }
                } else {
                    Mono.just(employee)
                }
            }
            .flatMap { employee ->
                if (employee.managerInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(tenantId = employee.tenantId, id = employee.managerId)
                        .map {
                            employee.managerInfo = it
                            employee
                        }
                        .switchIfEmpty { Mono.just(employee) }
                } else {
                    Mono.just(employee)
                }
            }
            .map {
                val employeeDto: BaseDto = EntityDtoConversion.toDto(it, EmployeeDto::class)
                if (employeeDto is EmployeeDto) {
                    if (it.managerInfo != null) {
                        employeeDto.managerInfo = EntityDtoConversion.toDto(it.managerInfo!!, UserDto::class)
                    }
                }
                employeeDto
            }
    }

    private fun handle(query: FindEmployeeByStatusQuery): Flux<out BaseDto> {
        return timesheetReactiveRepository
            .findByTenantIdAndUserIdAndStatus(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.status,
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

    private fun handle(query: SearchEmployeeByIdQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findByTenantIdAndManagerIdOrderByFirstName(
                tenantId = query.tenantId,
                managerId = query.managerId,
            )
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
    }

    private fun handle(query: SearchEmployeeByStatusQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findByTenantIdAndStatus(
                tenantId = query.tenantId,
                status = query.status,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "firstName",
                ),
            )
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
    }

    private fun handle(query: SearchEmployeeByIdAndStatusQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findByTenantIdAndManagerIdAndStatus(
                tenantId = query.tenantId,
                managerId = query.managerId,
                status = query.status,
            )
            .map { EntityDtoConversion.toDto(it, EmployeeDto::class) }
    }

    private fun handle(query: SearchAdminAndManagerByFilterQuery): Flux<out BaseDto> {
        return Flux.concat(
            employeeReactiveRepository
                .findByTenantIdAndFirstNameContainsIgnoreCaseAndRoleInOrderByFirstName(
                    tenantId = query.tenantId,
                    firstName = query.name,
                    roles = listOf(UserRole.MANAGER),
                ),
            adminReactiveRepository
                .findByTenantIdAndFirstNameContainsIgnoreCaseOrderByFirstName(
                    tenantId = query.tenantId,
                    firstName = query.name,
                ),
        ).map {
            EntityDtoConversion.toDto(it, UserDto::class)
        }
    }

    private fun handle(query: EmployeeSearchByFilterQuery): Flux<out BaseDto> {
        return EmployeeSearchByFilterQueryHandler(
            employeeReactiveRepository = employeeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindEmployeeByUserIdQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findByTenantIdAndIdWithMangerInfo(
                tenantId = query.tenantId,
                id = query.id,
            )
            .flatMap { employee ->
                if (employee.managerInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(tenantId = employee.tenantId, id = employee.managerId)
                        .map {
                            employee.managerInfo = it
                            employee
                        }
                        .switchIfEmpty { Mono.just(employee) }
                } else {
                    Mono.just(employee)
                }
            }
            .map {
                val employeeDto: BaseDto = EntityDtoConversion.toDto(it, EmployeeDto::class)
                if (employeeDto is EmployeeDto) {
                    if (it.managerInfo != null) {
                        employeeDto.managerInfo = EntityDtoConversion.toDto(it.managerInfo!!, UserDto::class)
                    }
                }
                employeeDto
            }
            .switchIfEmpty { Mono.just(EmptyBaseDto()) }
            .flux()
    }

    private fun handle(query: FindEmployeeCountByFilterQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .employeeCountByTenantIdAndRoleAndStatus(
                tenantId = query.tenantId,
                role = query.roleList,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }

    private fun handle(query: SearchAllSupervisorListQuery): Flux<out BaseDto> {
        return SearchAllSupervisorListQueryHandler(employeeReactiveRepository = employeeReactiveRepository).handle(query)
    }

    private fun handle(query: SearchAllActiveSupervisorListQuery): Flux<out BaseDto> {
        return SearchAllActiveSupervisorListQueryHandler(employeeReactiveRepository = employeeReactiveRepository).handle(query)
    }

    private fun handle(query: SearchAllEmployeeBySupervisorIdQuery): Flux<out BaseDto> {
        return SearchAllEmployeeBySupervisorIdQueryHandler(employeeReactiveRepository = employeeReactiveRepository).handle(
            query,
        )
    }

    private fun handle(query: FindEmployeeVacationsByIdQuery): Flux<out BaseDto> {
        return employeeReactiveRepository
            .findEmployeeVacationsByTenantIdAndId(query.tenantId, query.id)
            .switchIfEmpty(
                Mono.error(EmployeeNotFoundException("Employee vacation not found with id ${query.id}.")),
            ).toFlux()
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindEmployeeByEmailQuery -> handle(query)
            is FindEmployeeByIdQuery -> handle(query)
            is SearchEmployeeByFilterQuery -> handle(query)
            is FindEmployeeByStatusQuery -> handle(query)
            is SearchEmployeeByIdQuery -> handle(query)
            is SearchEmployeeByStatusQuery -> handle(query)
            is SearchEmployeeByIdAndStatusQuery -> handle(query)
            is SearchAdminAndManagerByFilterQuery -> handle(query)
            is EmployeeSearchByFilterQuery -> handle(query)
            is FindEmployeeByUserIdQuery -> handle(query)
            is FindEmployeeCountByFilterQuery -> handle(query)
            is SearchAllSupervisorListQuery -> handle(query)
            is SearchAllActiveSupervisorListQuery -> handle(query)
            is SearchAllEmployeeBySupervisorIdQuery -> handle(query)
            is FindEmployeeVacationsByIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
