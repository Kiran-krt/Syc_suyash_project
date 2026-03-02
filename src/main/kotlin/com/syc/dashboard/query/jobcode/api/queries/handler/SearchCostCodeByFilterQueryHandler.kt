package com.syc.dashboard.query.jobcode.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.jobcode.api.queries.SearchCostCodeByFilterQuery
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import com.syc.dashboard.query.jobcode.repository.reactive.CostCodeReactiveRepository
import reactor.core.publisher.Flux

class SearchCostCodeByFilterQueryHandler(
    private val costCodeReactiveRepository: CostCodeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchCostCodeByFilterQuery
        return costCodeReactiveRepository
            .findByTenantIdAndCodeContainsAndStatusIn(
                tenantId = query.tenantId,
                code = query.code,
                status = query.status ?: CostCodeStatusEnum.values().toList(),
            )
            .map { EntityDtoConversion.toDto(it, CostCodeDto::class) }
    }
}
