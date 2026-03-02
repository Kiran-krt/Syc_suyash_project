package com.syc.dashboard.query.vehiclelog.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogByVehicleIdQuery
import com.syc.dashboard.query.vehiclelog.repository.reactive.VehicleLogReactiveRepository
import reactor.core.publisher.Flux

class FindVehicleLogByVehicleIdQueryHandler(
    private val vehicleLogReactiveRepository: VehicleLogReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindVehicleLogByVehicleIdQuery
        return vehicleLogReactiveRepository
            .findByTenantIdAndVehicleId(
                query.tenantId,
                query.vehicleId,
            )
    }
}
