package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.settings.api.queries.FindAllActiveVehicleInfoQuery
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum
import com.syc.dashboard.query.settings.repository.reactive.VehicleInfoReactiveRepository
import reactor.core.publisher.Flux

class FindAllActiveVehicleInfoQueryHandler(
    private val vehicleInfoReactiveRepository: VehicleInfoReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindAllActiveVehicleInfoQuery
        return vehicleInfoReactiveRepository
            .findAllByTenantIdAndVehicleStatus(
                tenantId = query.tenantId,
                vehicleStatus = VehicleStatusEnum.ACTIVE,
            )
    }
}
