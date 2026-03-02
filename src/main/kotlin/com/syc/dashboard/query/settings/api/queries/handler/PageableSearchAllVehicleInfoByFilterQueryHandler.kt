package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.PageableSearchAllVehicleInfoByFilterQuery
import com.syc.dashboard.query.settings.dto.VehicleInfoDto
import com.syc.dashboard.query.settings.repository.reactive.VehicleInfoReactiveRepository
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux

class PageableSearchAllVehicleInfoByFilterQueryHandler(
    private val vehicleInfoReactiveRepository: VehicleInfoReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableSearchAllVehicleInfoByFilterQuery

        return vehicleInfoReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
                vehicleName = query.vehicleName,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "createdOn",
                ),
            ).map {
                EntityDtoConversion.copyFromJson(it, VehicleInfoDto::class.java)
            }
    }
}
