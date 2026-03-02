package com.syc.dashboard.query.projectreport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.projectreport.api.queries.PageableSearchProjectReportByFilterQuery
import com.syc.dashboard.query.projectreport.dto.ProjectReportDto
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import com.syc.dashboard.query.projectreport.repository.reactive.ProjectReportReactiveRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class PageableSearchProjectReportByFilterQueryHandler(
    private val projectReportReactiveRepository: ProjectReportReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableSearchProjectReportByFilterQuery

        return projectReportReactiveRepository
            .findAllByTenantIdAndProjectNameAndStatusIn(
                tenantId = query.tenantId,
                projectName = query.projectName,
                status = query.status.ifEmpty { ProjectReportStatusEnum.entries },
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    Sort.by(query.sort, "createdDate"),
                ),
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
            }
    }
}
