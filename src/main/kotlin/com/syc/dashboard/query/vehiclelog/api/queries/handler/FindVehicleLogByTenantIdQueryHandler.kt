package com.syc.dashboard.query.vehiclelog.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogByTenantIdQuery
import com.syc.dashboard.query.vehiclelog.dto.VehicleLogDto
import com.syc.dashboard.query.vehiclelog.repository.reactive.VehicleLogReactiveRepository
import reactor.core.publisher.Flux

class FindVehicleLogByTenantIdQueryHandler(
    private val vehicleLogReactiveRepository: VehicleLogReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindVehicleLogByTenantIdQuery
        return vehicleLogReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
            .map { EntityDtoConversion.toDto(it, VehicleLogDto::class) }
    }
}
