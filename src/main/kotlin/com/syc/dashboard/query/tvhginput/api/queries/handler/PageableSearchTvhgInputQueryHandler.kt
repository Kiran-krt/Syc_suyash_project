package com.syc.dashboard.query.tvhginput.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.tvhginput.api.queries.PageableSearchAllTvhgInputQuery
import com.syc.dashboard.query.tvhginput.dto.TvhgInputDto
import com.syc.dashboard.query.tvhginput.repository.reactive.TvhgInputReactiveRepository
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class PageableSearchTvhgInputQueryHandler(
    private val tvhgInputReactiveRepository: TvhgInputReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableSearchAllTvhgInputQuery

        return tvhgInputReactiveRepository
            .findAllByTenantId(
                tenantId = query.tenantId,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "createdOn",
                ),
            )
            .flatMap({ tvhginput ->
                if (tvhginput.createdByInfo == null) {
                    employeeReactiveRepository.findByTenantIdAndId(tvhginput.tenantId, tvhginput.createdBy)
                        .map {
                            tvhginput.createdByInfo = it
                            tvhginput
                        }
                        .switchIfEmpty { Mono.just(tvhginput) }
                } else {
                    Mono.just(tvhginput)
                }
            }, 1,)
            .map {
                val tvhgInputDto = EntityDtoConversion.toDto(it, TvhgInputDto::class)
                if (it.createdByInfo != null) {
                    tvhgInputDto.createdByInfo = EntityDtoConversion.toDto(it.createdByInfo!!, AdminDto::class)
                }
                tvhgInputDto
            }
    }
}
