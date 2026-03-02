package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.jobcode.api.queries.handler.*
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.query.jobcode.exceptions.CostCodeNotFoundWithIdException
import com.syc.dashboard.query.jobcode.repository.reactive.CostCodeReactiveRepository
import com.syc.dashboard.query.jobcode.repository.reactive.JobCodeReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux

@Service
class JobCodeQueryHandler @Autowired constructor(
    private val jobCodeReactiveRepository: JobCodeReactiveRepository,
    private val costCodeReactiveRepository: CostCodeReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindJobCodeByIdQuery): Flux<out BaseDto> {
        return jobCodeReactiveRepository
            .findByTenantIdAndId(
                tenantId = query.tenantId,
                id = query.id,
            )
            .flatMap { jobcode ->
                if (jobcode.createdByInfo == null) {
                    employeeReactiveRepository.findByTenantIdAndId(jobcode.tenantId, jobcode.createdBy)
                        .map {
                            jobcode.createdByInfo = it
                            jobcode
                        }
                        .switchIfEmpty { Mono.just(jobcode) }
                } else {
                    Mono.just(jobcode)
                }
            }
            .map {
                val jobCodeDto = EntityDtoConversion.copyFromJson(it, JobCodeDto::class.java)
                if (it.createdByInfo != null) {
                    jobCodeDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, AdminDto::class)
                }
                jobCodeDto
            }.flux()
    }

    private fun handle(query: FindJobCodeByCodeQuery): Flux<out BaseDto> {
        return jobCodeReactiveRepository
            .findByTenantIdAndCode(
                tenantId = query.tenantId,
                code = query.code,
            )
            .map {
                val jobCodeDto = EntityDtoConversion.toDto(it, JobCodeDto::class)
                if (it.createdByInfo != null) {
                    jobCodeDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, AdminDto::class)
                }
                jobCodeDto
            }
            .flux()
    }

    private fun handle(query: FindAllJobCodeQuery): Flux<out BaseDto> {
        return jobCodeReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
            .flatMap { jobcode ->
                if (jobcode.createdByInfo == null) {
                    employeeReactiveRepository.findByTenantIdAndId(jobcode.tenantId, jobcode.createdBy)
                        .map {
                            jobcode.createdByInfo = it
                            jobcode
                        }
                        .switchIfEmpty { Mono.just(jobcode) }
                } else {
                    Mono.just(jobcode)
                }
            }
            .map {
                val jobCodeDto = EntityDtoConversion.toDto(it, JobCodeDto::class)
                if (it.createdByInfo != null) {
                    jobCodeDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, AdminDto::class)
                }
                jobCodeDto
            }
    }

    private fun handle(query: SearchJobCodeByFilterQuery): Flux<out BaseDto> {
        return SearchJobCodeByFilterQueryHandler(
            jobCodeReactiveRepository = jobCodeReactiveRepository,
            employeeReactiveRepository = employeeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: PageableFindCostCodeByJobCodeIdQuery): Flux<out BaseDto> {
        return costCodeReactiveRepository
            .findByTenantIdAndJobCodeId(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "code",
                ),
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
    }

    private fun handle(query: PageableFindAllActiveStatusCostCodeByIdQuery): Flux<out BaseDto> {
        return costCodeReactiveRepository
            .findByTenantIdAndJobCodeIdAndStatus(
                tenantId = query.tenantId,
                jobCodeId = query.id,
                status = CostCodeStatusEnum.ACTIVE,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "code",
                ),
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
    }

    private fun handle(query: FindCostCodeAllActiveStatusByIdQuery): Flux<out BaseDto> {
        return costCodeReactiveRepository
            .findByTenantIdAndStatusAndJobCodeId(
                tenantId = query.tenantId,
                jobCodeId = query.id,
                status = CostCodeStatusEnum.ACTIVE,
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
    }

    private fun handle(query: FindCostCodeByJobCodeIdQuery): Flux<out BaseDto> {
        return costCodeReactiveRepository
            .findByTenantIdAndJobCodeId(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
    }

    private fun handle(query: FindCostCodByJobCodeIdIdAndIdQuery): Flux<out BaseDto> {
        return costCodeReactiveRepository
            .findByTenantIdAndJobCodeIdAndId(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
                id = query.id,
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
            .switchIfEmpty { throw CostCodeNotFoundWithIdException("Cost code not found with id ${query.id}.") }
            .toFlux()
    }

    private fun handle(query: FindAllActiveStatusJobCodeByIdQuery): Flux<out BaseDto> {
        return jobCodeReactiveRepository
            .findByTenantIdAndIdAndStatus(
                tenantId = query.tenantId,
                id = query.id,
                status = JobCodeStatusEnum.ACTIVE,
            )
            .map {
                val jobCodeDto = EntityDtoConversion.toDto(it, JobCodeDto::class)
                if (it.createdByInfo != null) {
                    jobCodeDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, AdminDto::class)
                }
                jobCodeDto
            }
    }

    private fun handle(query: FindCostCodeByCodeQuery): Flux<out BaseDto> {
        return costCodeReactiveRepository
            .findByTenantIdAndJobCodeIdAndCodeContains(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
                code = query.code,
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
    }

    private fun handle(query: SearchJobCodeByCodeQuery): Flux<out BaseDto> {
        return SearchJobCodeByCodeQueryHandler(
            jobCodeReactiveRepository = jobCodeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: SearchCostCodeByFilterQuery): Flux<out BaseDto> {
        return SearchCostCodeByFilterQueryHandler(
            costCodeReactiveRepository = costCodeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindAllJobCodeByWatcherQuery): Flux<out BaseDto> {
        return FindAllJobCodeByWatcherQueryHandler(
            jobCodeReactiveRepository = jobCodeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: SearchJobCodeByProjectIdQuery): Flux<out BaseDto> {
        return SearchJobCodeByProjectIdQueryHandler(
            jobCodeReactiveRepository = jobCodeReactiveRepository,
            employeeReactiveRepository = employeeReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindJobCodeByIdQuery -> handle(query)
            is FindJobCodeByCodeQuery -> handle(query)
            is SearchJobCodeByFilterQuery -> handle(query)
            is FindAllJobCodeQuery -> handle(query)
            is PageableFindCostCodeByJobCodeIdQuery -> handle(query)
            is PageableFindAllActiveStatusCostCodeByIdQuery -> handle(query)
            is FindCostCodeAllActiveStatusByIdQuery -> handle(query)
            is FindCostCodeByJobCodeIdQuery -> handle(query)
            is FindCostCodByJobCodeIdIdAndIdQuery -> handle(query)
            is FindAllActiveStatusJobCodeByIdQuery -> handle(query)
            is FindCostCodeByCodeQuery -> handle(query)
            is SearchJobCodeByCodeQuery -> handle(query)
            is SearchCostCodeByFilterQuery -> handle(query)
            is FindAllJobCodeByWatcherQuery -> handle(query)
            is SearchJobCodeByProjectIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
