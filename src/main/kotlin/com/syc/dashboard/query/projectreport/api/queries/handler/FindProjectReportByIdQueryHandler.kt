package com.syc.dashboard.query.projectreport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.projectreport.api.queries.FindProjectReportByIdQuery
import com.syc.dashboard.query.projectreport.dto.ProjectReportDto
import com.syc.dashboard.query.projectreport.repository.reactive.ProjectReportReactiveRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class FindProjectReportByIdQueryHandler(
    private val projectReportReactiveRepository: ProjectReportReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindProjectReportByIdQuery
        return projectReportReactiveRepository
            .findByTenantIdAndId(
                tenantId = query.tenantId,
                id = query.id,
            )
            .flatMap { projectreport ->
                if (projectreport.createdByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(projectreport.tenantId, projectreport.createdBy)
                        .map {
                            projectreport.createdByInfo = it
                            projectreport
                        }
                        .switchIfEmpty { Mono.just(projectreport) }
                } else {
                    Mono.just(projectreport)
                }
            }
            .map {
                val projectReportDto = EntityDtoConversion.copyFromJson(it, ProjectReportDto::class.java)
                if (it.createdByInfo != null) {
                    projectReportDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, EmployeeDto::class)
                }
                projectReportDto
            }.flux()
    }
}
