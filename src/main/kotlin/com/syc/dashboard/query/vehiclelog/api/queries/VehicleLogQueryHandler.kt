package com.syc.dashboard.query.vehiclelog.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.vehiclelog.api.queries.handler.FindVehicleLogByIdQueryHandler
import com.syc.dashboard.query.vehiclelog.api.queries.handler.FindVehicleLogByTenantIdQueryHandler
import com.syc.dashboard.query.vehiclelog.api.queries.handler.FindVehicleLogByVehicleIdQueryHandler
import com.syc.dashboard.query.vehiclelog.api.queries.handler.FindVehicleLogForEmployeeByIdQueryHandler
import com.syc.dashboard.query.vehiclelog.api.queries.handler.PageableSearchAllVehicleLogByFilterQueryHandler
import com.syc.dashboard.query.vehiclelog.repository.reactive.VehicleLogReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class VehicleLogQueryHandler @Autowired constructor(
    private val vehicleLogReactiveRepository: VehicleLogReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindVehicleLogByTenantIdQuery): Flux<out BaseDto> {
        return FindVehicleLogByTenantIdQueryHandler(
            vehicleLogReactiveRepository = vehicleLogReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: PageableSearchAllVehicleLogByFilterQuery): Flux<out BaseDto> {
        return PageableSearchAllVehicleLogByFilterQueryHandler(
            vehicleLogReactiveRepository = vehicleLogReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindVehicleLogByVehicleIdQuery): Flux<out BaseDto> {
        return FindVehicleLogByVehicleIdQueryHandler(
            vehicleLogReactiveRepository = vehicleLogReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindVehicleLogByIdQuery): Flux<out BaseDto> {
        return FindVehicleLogByIdQueryHandler(
            vehicleLogReactiveRepository = vehicleLogReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindVehicleLogForEmployeeByIdQuery): Flux<out BaseDto> {
        return FindVehicleLogForEmployeeByIdQueryHandler(
            vehicleLogReactiveRepository = vehicleLogReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindVehicleLogByTenantIdQuery -> handle(query)
            is PageableSearchAllVehicleLogByFilterQuery -> handle(query)
            is FindVehicleLogByVehicleIdQuery -> handle(query)
            is FindVehicleLogByIdQuery -> handle(query)
            is FindVehicleLogForEmployeeByIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
