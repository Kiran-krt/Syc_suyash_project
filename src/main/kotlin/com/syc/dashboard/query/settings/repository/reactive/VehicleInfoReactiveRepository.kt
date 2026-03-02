package com.syc.dashboard.query.settings.repository.reactive

import com.syc.dashboard.query.settings.dto.VehicleInfoDto
import com.syc.dashboard.query.settings.entity.VehicleInfo
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface VehicleInfoReactiveRepository : ReactiveMongoRepository<VehicleInfo, String> {

    @Aggregation(
        "{ \$match: { " +
            "tenantId: :#{#tenantId}, " +
            "vehicleName : { \$regex: :#{#vehicleName}, \$options: 'i' } " +
            "} }",
    )
    fun findByTenantId(
        tenantId: String,
        vehicleName: String,
        pageable: Pageable,
    ): Flux<VehicleInfo>

    fun findByTenantIdAndId(tenantId: String, id: String): Mono<VehicleInfo>

    @Aggregation(
        "{ \$match: { " +
            "tenantId: :#{#tenantId}, " +
            "vehicleStatus: :#{#vehicleStatus} " +
            "} }",
        "{ \$lookup: { " +
            "'from': 'q_vehicle_log', " +
            "'localField': '_id', " +
            "'foreignField': 'vehicleId', " +
            "'as': 'vehicleLogInfo' " +
            "} }",
        "{ \$project: { " +
            "_id: 1, " +
            "settingsId: 1, " +
            "vehicleName: 1, " +
            "vehicleModel: 1, " +
            "vehicleNumber: 1, " +
            "vehicleInsurance: 1, " +
            "vehicleLogInfo: { " +
            "\$filter: { " +
            "input: '\$vehicleLogInfo', " +
            "as: 'log', " +
            "cond: { \$eq: ['$\$log.status', 'INUSE'] } " +
            "} " +
            "} " +
            "} }",
    )
    fun findAllByTenantIdAndVehicleStatus(
        tenantId: String,
        vehicleStatus: VehicleStatusEnum,
    ): Flux<VehicleInfoDto>
}
