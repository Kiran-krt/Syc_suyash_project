package com.syc.dashboard.query.vehiclelog.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.vehiclelog.api.queries.PageableSearchAllVehicleLogByFilterQuery
import com.syc.dashboard.query.vehiclelog.dto.VehicleLogDto
import com.syc.dashboard.query.vehiclelog.repository.reactive.VehicleLogReactiveRepository
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux

class PageableSearchAllVehicleLogByFilterQueryHandler(
    private val vehicleLogReactiveRepository: VehicleLogReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableSearchAllVehicleLogByFilterQuery

        return vehicleLogReactiveRepository
            .findAllByTenantId(
                tenantId = query.tenantId,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "createdOn",
                ),
            ).map {
                EntityDtoConversion.copyFromJson(it, VehicleLogDto::class.java)
            }
    }
}
