package com.syc.dashboard.query.jobcode.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.jobcode.api.queries.SearchJobCodeByProjectIdQuery
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.jobcode.repository.reactive.JobCodeReactiveRepository
import com.syc.dashboard.query.project.dto.ProjectDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class SearchJobCodeByProjectIdQueryHandler(
    private val jobCodeReactiveRepository: JobCodeReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchJobCodeByProjectIdQuery
        return jobCodeReactiveRepository
            .findJobCodeByTenantIdAndProjectId(
                tenantId = query.tenantId,
                projectId = query.projectId,
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
                if (it.projectIdInfo != null) {
                    jobCodeDto.projectIdInfo = EntityDtoConversion.toDto(it.projectIdInfo!!, ProjectDto::class)
                }

                if (it.createdByInfo != null) {
                    jobCodeDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, AdminDto::class)
                }
                jobCodeDto
            }
    }
}
