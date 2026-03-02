package com.syc.dashboard.query.tvhgConfig.repository.reactive

import com.syc.dashboard.query.tvhgConfig.dto.DesignStormDto
import com.syc.dashboard.query.tvhgConfig.dto.MdStandardNumberListDto
import com.syc.dashboard.query.tvhgConfig.dto.UnitsDto
import com.syc.dashboard.query.tvhgConfig.entity.TvhgConfig
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassEnum
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface TvhgConfigReactiveRepository : ReactiveMongoRepository<TvhgConfig, String> {

    @Aggregation(
        "{ \"\$unwind\": { \"path\": \"\$units\", \"preserveNullAndEmptyArrays\": false } }",
        "{ \"\$match\": { \"_id\": :#{#id} } }",
        "{ \"\$project\": { " +
            "\"id\": \"\$units._id\", " +
            "\"tenantId\": \"\$tenantId\", " +
            "\"unitName\": \"\$units.unitName\", " +
            "\"status\": \"\$units.status\", " +
            "\"createdOn\": \"\$units.createdOn\" " +
            "} }",
    )
    fun findByTenantIdAndId(
        id: String,
    ): Flux<UnitsDto>

    @Aggregation(
        "{ \$match: { \"tenantId\": :#{#tenantId} } }",
        "{ \$project: { \"designStorm\": 1, \"structureType\": 1, \"units\": 1, \"inletControlData\": 1 ," +
            " \"inletControlParameter\": 1,  \"createdBy\": 1, \"pipeDrawingDataList\": 1, \"pipeTypeList\": 1," +
            " \"outletStructureType\": 1, \"pipeMaterialList\": 1, \"mdStandardNumberList\": 1,} }",
    )
    fun findByTenantId(tenantId: String): Mono<TvhgConfig>

    @Aggregation(
        "{ \"\$unwind\": { \"path\": \"\$designStorm\", \"preserveNullAndEmptyArrays\": false } }",
        "{ \"\$match\": { \"_id\": :#{#id} } }",
        "{ \"\$project\": { " +
            "\"id\": \"\$designStorm._id\", " +
            "\"tenantId\": \"\$tenantId\", " +
            "\"designStormName\": \"\$designStorm.designStormName\", " +
            "\"status\": \"\$designStorm.status\", " +
            "\"createdOn\": \"\$designStorm.createdOn\" " +
            "} }",
    )
    fun findAllByTenantIdAndId(
        id: String,
    ): Flux<DesignStormDto>

    @Aggregation(
        "{ \"\$unwind\": { \"path\": \"\$mdStandardNumberList\", \"preserveNullAndEmptyArrays\": false } }",
        "{ \"\$match\": { " +
            "\"_id\": :#{#tenantId}, " +
            "\"mdStandardNumberList.structureClass\": :#{#structureClass} " +
            "} }",
        "{ \"\$project\": { " +
            "\"id\": \"\$mdStandardNumberList._id\", " +
            "\"structureClass\": \"\$mdStandardNumberList.structureClass\", " +
            "\"mdStandardNumber\": \"\$mdStandardNumberList.mdStandardNumber\", " +
            "\"type\": \"\$mdStandardNumberList.type\", " +
            "\"status\": \"\$mdStandardNumberList.status\" " +
            "} }",
    )
    fun findAllByTenantIdAndStructureClass(
        tenantId: String,
        structureClass: StructureClassEnum,
    ): Flux<MdStandardNumberListDto>
}
