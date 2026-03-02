package com.syc.dashboard.query.jobcode.api.queries.handler

import com.syc.dashboard.framework.common.utils.ReflectionUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.jobcode.api.queries.SearchJobCodeByFilterQuery
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.query.jobcode.repository.reactive.JobCodeReactiveRepository
import com.syc.dashboard.query.project.dto.ProjectDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class SearchJobCodeByFilterQueryHandler(
    private val jobCodeReactiveRepository: JobCodeReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchJobCodeByFilterQuery

        return jobCodeReactiveRepository
            .findByTenantIdAndCodeContainsAndStatusIn(
                tenantId = query.tenantId,
                code = query.code,
                status = query.status ?: JobCodeStatusEnum.values().toList(),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    Sort.by(query.sort, query.sortBy),
                ),
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
                if (it.projectIdInfo != null) {
                    jobCodeDto.projectIdInfo = EntityDtoConversion.toDto(it.projectIdInfo!!, ProjectDto::class)
                }
                jobCodeDto
            }
            .sort { o1, o2 ->
                val firstObject = if (query.sort == Sort.Direction.DESC) o2 else o1
                val secondObject = if (query.sort == Sort.Direction.DESC) o1 else o2
                ReflectionUtils.getFieldValue(obj = firstObject, fieldName = query.sortBy)
                    .compareTo(ReflectionUtils.getFieldValue(obj = secondObject, fieldName = query.sortBy))
            }
    }
}
