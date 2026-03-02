package com.syc.dashboard.query.vehiclelog.repository.reactive

import com.syc.dashboard.query.vehiclelog.dto.VehicleLogDto
import com.syc.dashboard.query.vehiclelog.entity.VehicleLog
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface VehicleLogReactiveRepository : ReactiveMongoRepository<VehicleLog, String> {

    fun findByTenantId(
        tenantId: String,
    ): Flux<VehicleLog>

    @Aggregation(
        "{ \$match: { " +
            "\"tenantId\": :#{#tenantId}, " +
            "\"id\": :#{#id} " +
            "} }",
        "{ \$lookup: { " +
            "'from': 'q_settings_vehicle_info', " +
            "'localField': 'vehicleId', " +
            "'foreignField': '_id', " +
            "'as': 'vehicleInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$vehicleInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
        "{ \$lookup: { " +
            "from: 'q_employee', " +
            "localField: 'accompany', " +
            "foreignField: '_id', " +
            "as: 'accompanyInfo' " +
            "} }",
    )
    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): Mono<VehicleLogDto>

    @Aggregation(
        "{ \$lookup: { " +
            "'from': 'q_settings_vehicle_info', " +
            "'localField': 'vehicleId', " +
            "'foreignField': '_id', " +
            "'as': 'vehicleInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$vehicleInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
    )
    fun findAllByTenantId(
        tenantId: String,
        pageable: Pageable,
    ): Flux<VehicleLogDto>

    @Aggregation(
        "{ \$lookup: { " +
            "'from': 'q_settings_vehicle_info', " +
            "'localField': 'vehicleId', " +
            "'foreignField': '_id', " +
            "'as': 'vehicleInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$vehicleInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
        "{ \$match: { " +
            "\"tenantId\": :#{#tenantId}, " +
            "\"vehicleId\": :#{#vehicleId} " +
            "} }",
        "{ \$lookup: { " +
            "'from': 'q_employee', " +
            "'localField': 'staffInitial', " +
            "'foreignField': '_id', " +
            "'as': 'employeeInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$employeeInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
        "{ \$lookup: { " +
            "from: 'q_employee', " +
            "localField: 'accompany', " +
            "foreignField: '_id', " +
            "as: 'accompanyInfo' " +
            "} }",
        "{ \$lookup: { " +
            "'from': 'q_employee', " +
            "'localField': 'createdBy', " +
            "'foreignField': '_id', " +
            "'as': 'createdByInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$createdByInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
    )
    fun findByTenantIdAndVehicleId(
        tenantId: String,
        vehicleId: String,
    ): Flux<VehicleLogDto>

    @Aggregation(
        "{ \$lookup: { " +
            "'from': 'q_settings_vehicle_info', " +
            "'localField': 'vehicleId', " +
            "'foreignField': '_id', " +
            "'as': 'vehicleInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$vehicleInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
        "{ \$match: { " +
            "\"tenantId\": :#{#tenantId}, " +
            "\"createdBy\": :#{#createdBy} " +
            "\"vehicleId\": :#{#vehicleId} " +
            "} }",
        "{ \$lookup: { " +
            "'from': 'q_employee', " +
            "'localField': 'staffInitial', " +
            "'foreignField': '_id', " +
            "'as': 'employeeInfo' " +
            "} }",
        "{ \$unwind: { " +
            "'path': '\$employeeInfo', " +
            "'preserveNullAndEmptyArrays': true " +
            "} }",
        "{ \$lookup: { " +
            "from: 'q_employee', " +
            "localField: 'accompany', " +
            "foreignField: '_id', " +
            "as: 'accompanyInfo' " +
            "} }",
    )
    fun findByTenantIdAndCreatedByAndVehicleId(
        tenantId: String,
        createdBy: String,
        vehicleId: String,
    ): Flux<VehicleLogDto>
}
