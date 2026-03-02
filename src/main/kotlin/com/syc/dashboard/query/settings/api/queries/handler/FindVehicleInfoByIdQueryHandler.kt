package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindVehicleInfoByIdQuery
import com.syc.dashboard.query.settings.dto.VehicleInfoDto
import com.syc.dashboard.query.settings.exceptions.VehicleInfoNotFoundException
import com.syc.dashboard.query.settings.repository.reactive.VehicleInfoReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.switchIfEmpty

class FindVehicleInfoByIdQueryHandler(
    private val vehicleInfoReactiveRepository: VehicleInfoReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindVehicleInfoByIdQuery
        return vehicleInfoReactiveRepository
            .findByTenantIdAndId(query.tenantId, query.id)
            .map { EntityDtoConversion.toDto(it, VehicleInfoDto::class) }
            .switchIfEmpty { throw VehicleInfoNotFoundException("Vehicle info not found with id ${query.id}.") }
            .flux()
    }
}
