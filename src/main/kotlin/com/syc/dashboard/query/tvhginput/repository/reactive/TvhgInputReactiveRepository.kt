package com.syc.dashboard.query.tvhginput.repository.reactive

import com.syc.dashboard.query.tvhginput.dto.*
import com.syc.dashboard.query.tvhginput.entity.TvhgInput
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface TvhgInputReactiveRepository : ReactiveMongoRepository<TvhgInput, String> {

    @Aggregation(
        "{ \$match: { \"tenantId\": :#{#tenantId}, \"_id\": :#{#id} } }",
        "{ \$group: { " +
            "\"_id\": \"\$_id\", " +
            "\"name\": { \"\$first\": \"\$name\" }, " +
            "\"description\": { \"\$first\": \"\$description\" }, " +
            "\"status\": { \"\$first\": \"\$status\" }, " +
            "\"projectTitle\": { \"\$first\": \"\$projectTitle\" }, " +
            "\"tenantId\": { \"\$first\": \"\$tenantId\" }, " +
            "\"projectInformation\": { \"\$first\": \"\$projectInformation\" }, " +
            "\"hydrologicInformation\": { \"\$first\": \"\$hydrologicInformation\" }, " +
            "\"outletDrawingInformation\": { \"\$first\": \"\$outletDrawingInformation\" }, " +
            "\"flowPathDrawingInformation\": { \"\$first\": \"\$flowPathDrawingInformation\" }, " +
            "\"pipeList\": { \"\$first\": \"\$pipeList\" }, " +
            "\"pipeDrawingInformationList\": { \"\$first\": \"\$pipeDrawingInformationList\" }, " +
            "\"structureDrawingDataList\": { \"\$first\": \"\$structureDrawingDataList\" }, " +
            "\"inletControlParameterList\": { \"\$first\": \"\$inletControlParameterList\" }, " +
            "\"structureList\": { \"\$first\": \"\$structureList\" }, " +
            "} }",
        "{ \$project: { " +
            "\"tenantId\": 1, " +
            "\"name\": 1, " +
            "\"description\": 1, " +
            "\"createdBy\": 1, " +
            "\"status\": 1, " +
            "\"projectTitle\": 1, " +
            "\"projectInformation\": 1, " +
            "\"structureList\": 1, " +
            "\"inletControlParameterList\": 1, " +
            "\"hydrologicInformation\": 1, " +
            "\"pipeInformation\": 1, " +
            "\"pipeList\": 1, " +
            "\"structureDrawingDataList\": 1, " +
            "\"flowPathDrawingInformation\": 1, " +
            "\"outletDrawingInformation\": 1, " +
            "\"pipeDrawingInformationList\": 1 " +
            "} }",
    )
    fun findByTenantIdAndId(tenantId: String, id: String): Mono<TvhgInput>

    @Aggregation(
        "{ \$match: { \"tenantId\": :#{#tenantId} } }",
        "{ \$unwind: { path: \"\$structureList\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$pipeDrawingInformationList\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$pipeList\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$flowPathDrawingInformation\", preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "from: \"q_tvhg_config\", " +
            "localField: \"structureList.structureTypeId\", " +
            "foreignField: \"structureType._id\", " +
            "as: \"structureList.structureTypeInfo\" " +
            "} }",
        "{ \$unwind: { path: \"\$structureList.structureTypeInfo\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$structureList.structureTypeInfo.structureType\", preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "from: \"q_tvhg_config\", " +
            "localField: \"pipeDrawingInformationList.pipeMaterialId\", " +
            "foreignField: \"pipeMaterialList._id\", " +
            "as: \"pipeDrawingInformationList.pipeMaterialInfo\" " +
            "} }",
        "{ \$unwind: { path: \"\$pipeDrawingInformationList.pipeMaterialInfo\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$pipeDrawingInformationList.pipeMaterialInfo.pipeMaterialList\", preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "from: \"q_tvhg_config\", " +
            "localField: \"flowPathDrawingInformation.inletControlDataId\", " +
            "foreignField: \"inletControlData._id\", " +
            "as: \"flowPathDrawingInformation.inletControlDataInfo\" " +
            "} }",
        "{ \$unwind: { path: \"\$flowPathDrawingInformation.inletControlDataInfo\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$flowPathDrawingInformation.inletControlDataInfo.inletControlData\", preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "from: \"q_tvhg_config\", " +
            "localField: \"pipeList.pipeTypeId\", " +
            "foreignField: \"pipeTypeList._id\", " +
            "as: \"pipeList.pipeTypeInfo\" " +
            "} }",
        "{ \$unwind: { path: \"\$pipeList.pipeTypeInfo\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$pipeList.pipeTypeInfo.pipeTypeList\", preserveNullAndEmptyArrays: true } }",
        "{ \$group: { " +
            "\"_id\": \"\$_id\", " +
            "\"name\": { \"\$first\": \"\$name\" }, " +
            "\"description\": { \"\$first\": \"\$description\" }, " +
            "\"status\": { \"\$first\": \"\$status\" }, " +
            "\"projectTitle\": { \"\$first\": \"\$projectTitle\" }, " +
            "\"tenantId\": { \"\$first\": \"\$tenantId\" }, " +
            "\"projectInformation\": { \"\$first\": \"\$projectInformation\" }, " +
            "\"hydrologicInformation\": { \"\$first\": \"\$hydrologicInformation\" }, " +
            "\"outletDrawingInformation\": { \"\$first\": \"\$outletDrawingInformation\" }, " +
            "\"pipeDrawingInformationList\": { \"\$addToSet\": \"\$pipeDrawingInformationList\" }, " +
            "\"structureDrawingDataList\": { \"\$first\": \"\$structureDrawingDataList\" }, " +
            "\"inletControlParameterList\": { \"\$first\": \"\$inletControlParameterList\" }, " +
            "\"structureList\": { " +
            "\"\$push\": { " +
            "\"_id\": \"\$structureList._id\", " +
            "\"tenantId\": \"\$structureList.tenantId\", " +
            "\"structureNumber\": \"\$structureList.structureNumber\", " +
            "\"structureId\": \"\$structureList.structureId\", " +
            "\"structureTypeId\": \"\$structureList.structureTypeId\", " +
            "\"structureTypeInfo\": \"\$structureList.structureTypeInfo.structureType\", " +
            "\"overflowElevation\": \"\$structureList.overflowElevation\", " +
            "\"contributionArea\": \"\$structureList.contributionArea\", " +
            "\"runoffCoefficient\": \"\$structureList.runoffCoefficient\", " +
            "\"timeOfConcentration\": \"\$structureList.timeOfConcentration\", " +
            "\"status\": \"\$structureList.status\", " +
            "\"createdBy\": \"\$structureList.createdBy\", " +
            "\"createdOn\": \"\$structureList.createdOn\" " +
            "} " +
            "} , " +
            "\"flowPathDrawingInformation\": { " +
            "\"\$push\": { " +
            "\"_id\": \"\$flowPathDrawingInformation._id\", " +
            "\"inletControlDataId\": \"\$flowPathDrawingInformation.inletControlDataId\", " +
            "\"pathTitle\": \"\$flowPathDrawingInformation.pathTitle\", " +
            "\"createdOn\": \"\$flowPathDrawingInformation.createdOn\", " +
            "\"inletControlDataInfo\": \"\$flowPathDrawingInformation.inletControlDataInfo.inletControlData\", " +
            "} " +
            "} , " +
            "\"pipeList\": { " +
            "\"\$push\": { " +
            "\"_id\": \"\$pipeList._id\", " +
            "\"pipeNumber\": \"\$pipeList.pipeNumber\" ," +
            "\"pipeId\": \"\$pipeList.pipeId\", " +
            "\"downstreamStructureNumber\": \"\$pipeList.downstreamStructureNumber\", " +
            "\"upstreamStructureNumber\": \"\$pipeList.upstreamStructureNumber\", " +
            "\"downstreamInvertElevation\": \"\$pipeList.downstreamInvertElevation\", " +
            "\"upstreamInvertElevation\": \"\$pipeList.upstreamInvertElevation\", " +
            "\"pipeTypeId\": \"\$pipeList.pipeTypeId\", " +
            "\"pipeTypeInfo\": \"\$pipeList.pipeTypeInfo.pipeTypeList\", " +
            "\"roughnessCoefficient\": \"\$pipeList.roughnessCoefficient\", " +
            "\"pipeLength\": \"\$pipeList.pipeLength\", " +
            "\"intersectionAngle\": \"\$pipeList.intersectionAngle\", " +
            "\"discharge\": \"\$pipeList.discharge\", " +
            "\"status\": \"\$pipeList.status\", " +
            "} " +
            "} " +
            "} }",
        "{ \$project: { " +
            "\"tenantId\": 1, " +
            "\"name\": 1, " +
            "\"description\": 1, " +
            "\"createdBy\": 1, " +
            "\"status\": 1, " +
            "\"projectTitle\": 1, " +
            "\"projectInformation\": 1, " +
            "\"structureList\": 1, " +
            "\"inletControlParameterList\": 1, " +
            "\"hydrologicInformation\": 1, " +
            "\"pipeInformation\": 1, " +
            "\"pipeList\": 1, " +
            "\"structureDrawingDataList\": 1, " +
            "\"flowPathDrawingInformation\": 1, " +
            "\"outletDrawingInformation\": 1, " +
            "\"pipeDrawingInformationList\": 1 " +
            "} }",
    )
    fun findByTenantId(tenantId: String): Flux<TvhgInput>

    @Aggregation(
        "{ \$match: { \"tenantId\": :#{#tenantId} } }",
        "{ \$lookup: { " +
            "from: \"q_admin\", " +
            "localField: \"createdBy\", " +
            "foreignField: \"_id\", " +
            "as: \"createdByInfo\" " +
            "} }",
        "{ \$unwind: { path: \"\$createdByInfo\", preserveNullAndEmptyArrays: true } }",
        "{ \$group: { " +
            "\"_id\": \"\$_id\", " +
            "\"name\": { \"\$first\": \"\$name\" }, " +
            "\"description\": { \"\$first\": \"\$description\" }, " +
            "\"status\": { \"\$first\": \"\$status\" }, " +
            "\"projectTitle\": { \"\$first\": \"\$projectTitle\" }, " +
            "\"tenantId\": { \"\$first\": \"\$tenantId\" }, " +
            "\"createdOn\": { \"\$first\": \"\$createdOn\" }, " +
            "\"createdBy\": {\"\$first\": \"\$createdBy\" }," +
            "\"createdByInfo\":{\"\$first\": \"\$createdByInfo\" }," +
            "\"projectInformation\": { \"\$first\": \"\$projectInformation\" }, " +
            "\"hydrologicInformation\": { \"\$first\": \"\$hydrologicInformation\" }, " +
            "\"outletDrawingInformation\": { \"\$first\": \"\$outletDrawingInformation\" }, " +
            "\"flowPathDrawingInformation\": { \"\$first\": \"\$flowPathDrawingInformation\" }, " +
            "\"pipeList\": { \"\$first\": \"\$pipeList\" }, " +
            "\"pipeDrawingInformationList\": { \"\$first\": \"\$pipeDrawingInformationList\" }, " +
            "\"structureDrawingDataList\": { \"\$first\": \"\$structureDrawingDataList\" }, " +
            "\"inletControlParameterList\": { \"\$first\": \"\$inletControlParameterList\" }, " +
            "\"structureList\": { \"\$first\": \"\$structureList\" } " +
            "} }",
        "{ \$project: { " +
            "\"tenantId\": 1, " +
            "\"name\": 1, " +
            "\"description\": 1, " +
            "\"createdBy\": 1, " +
            "\"createdOn\": 1, " +
            "\"createdByInfo\": 1," +
            "\"status\": 1, " +
            "\"projectTitle\": 1, " +
            "\"projectInformation\": 1, " +
            "\"structureList\": 1, " +
            "\"inletControlParameterList\": 1, " +
            "\"hydrologicInformation\": 1, " +
            "\"pipeList\": 1, " +
            "\"structureDrawingDataList\": 1, " +
            "\"flowPathDrawingInformation\": 1, " +
            "\"outletDrawingInformation\": 1, " +
            "\"pipeDrawingInformationList\": 1 " +
            "} }",
    )
    fun findAllByTenantId(tenantId: String, pageable: Pageable): Flux<TvhgInput>

    @Aggregation(
        "{ \$match: { tenantId: :#{#tenantId}, _id: :#{#id}, \"structureDrawingDataList._id\": :#{#structureDrawingDataId} } }",
        "{ \$unwind: { path: \"\$structureDrawingDataList\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$structureList\", preserveNullAndEmptyArrays: false } }",
        "{ \"\$match\": { " +
            "\"\$expr\": { " +
            "\"\$eq\": [" +
            "\"\$structureDrawingDataList.structureInformationId\", " +
            "\"\$structureList._id\"" +
            "]" +
            "}" +
            "}}",
        "{ \$project: { " +
            "\"_id\": \"\$structureDrawingDataList._id\", " +
            "\"structureInformationId\": \"\$structureDrawingDataList.structureInformationId\" ," +
            "\"existingOrProposedIndex\": \"\$structureDrawingDataList.existingOrProposedIndex\", " +
            "\"mdshaStandardNumber\": \"\$structureDrawingDataList.mdshaStandardNumber\", " +
            "\"typeOfStructure\": \"\$structureDrawingDataList.typeOfStructure\"," +
            "\"structureClass\": \"\$structureDrawingDataList.structureClass\"," +
            "\"station\": \"\$structureDrawingDataList.station\", " +
            "\"offset\": \"\$structureDrawingDataList.offset\", " +
            "\"createdBy\": \"\$structureDrawingDataList.createdBy\", " +
            "\"structureListInfo\": { " +
            "\"_id\": \"\$structureList._id\", " +
            "\"tenantId\": \"\$structureList.tenantId\" ," +
            "\"structureNumber\": \"\$structureList.structureNumber\", " +
            "\"structureId\": \"\$structureList.structureId\", " +
            "\"structureTypeId\": \"\$structureList.structureTypeId\", " +
            "\"overflowElevation\": \"\$structureList.overflowElevation\", " +
            "\"contributionArea\": \"\$structureList.contributionArea\", " +
            "\"runoffCoefficient\": \"\$structureList.runoffCoefficient\", " +
            "\"timeOfConcentration\": \"\$structureList.timeOfConcentration\", " +
            "\"status\": \"\$structureList.status\", " +
            "\"createdBy\": \"\$structureList.createdBy\", " +
            "\"createdOn\": \"\$structureList.createdOn\", " +
            "} " +
            "} } ",
    )
    fun findByTenantIdAndIdAndStructureDrawingDataId(
        tenantId: String,
        id: String,
        structureDrawingDataId: String,
    ): Mono<StructureDrawingDataDto>

    @Aggregation(
        "{ \$match: { tenantId: :#{#tenantId}, _id: :#{#id}, \"pipeDrawingInformationList._id\": :#{#pipeDrawingInformationId} } }",
        "{ \$unwind: { path: \"\$pipeDrawingInformationList\", preserveNullAndEmptyArrays: true } }",
        "{ \$unwind: { path: \"\$pipeList\", preserveNullAndEmptyArrays: false } }",
        "{ \"\$match\": { " +
            "\"\$expr\": { " +
            "\"\$eq\": [" +
            "\"\$pipeDrawingInformationList.pipeInformationId\", " +
            "\"\$pipeList._id\"" +
            "]" +
            "}" +
            "}}",
        "{ \$project: { " +
            "\"_id\": \"\$pipeDrawingInformationList._id\", " +
            "\"pipeMaterialId\": \"\$pipeDrawingInformationList.pipeMaterialId\" ," +
            "\"pipeInformationId\": \"\$pipeDrawingInformationList.pipeInformationId\", " +
            "\"distanceBetweenStructures\": \"\$pipeDrawingInformationList.distanceBetweenStructures\", " +
            "\"createdOn\": \"\$pipeDrawingInformationList.createdOn\", " +
            "\"pipeListInfo\": { " +
            "\"_id\": \"\$pipeList._id\", " +
            "\"pipeNumber\": \"\$pipeList.pipeNumber\" ," +
            "\"pipeId\": \"\$pipeList.pipeId\", " +
            "\"downstreamStructureNumber\": \"\$pipeList.downstreamStructureNumber\", " +
            "\"upstreamStructureNumber\": \"\$pipeList.upstreamStructureNumber\", " +
            "\"downstreamInvertElevation\": \"\$pipeList.downstreamInvertElevation\", " +
            "\"upstreamInvertElevation\": \"\$pipeList.upstreamInvertElevation\", " +
            "\"pipeTypeId\": \"\$pipeList.pipeTypeId\", " +
            "\"roughnessCoefficient\": \"\$pipeList.roughnessCoefficient\", " +
            "\"pipeLength\": \"\$pipeList.pipeLength\", " +
            "\"intersectionAngle\": \"\$pipeList.intersectionAngle\", " +
            "\"discharge\": \"\$pipeList.discharge\", " +
            "\"status\": \"\$pipeList.status\", " +
            "} " +
            "} } ",
    )
    fun findByTenantIdAndIdAndPipeDrawingInformationId(
        tenantId: String,
        id: String,
        pipeDrawingInformationId: String,
    ): Mono<PipeDrawingInformationDto>

    @Aggregation(
        "{ \"\$match\": { \"tenantId\": :#{#tenantId}, \"_id\": :#{#id} } }",
        "{ \"\$unwind\": { \"path\": \"\$projectInformation\", \"preserveNullAndEmptyArrays\": false } }",
        "{ \"\$project\": { " +
            "\"title\": \"\$projectInformation.title\", " +
            "\"description\": \"\$projectInformation.description\", " +
            "\"numberOfStructures\": \"\$projectInformation.numberOfStructures\", " +
            "\"numberOfFlowPaths\": \"\$projectInformation.numberOfFlowPaths\", " +
            "\"tailwaterElevationOutlet\": \"\$projectInformation.tailwaterElevationOutlet\", " +
            "\"hydrologicInformation\": \"\$projectInformation.hydrologicInformation\", " +
            "\"drawingInformation\": \"\$projectInformation.drawingInformation\", " +
            "\"status\": \"\$projectInformation.status\", " +
            "\"choiceOfUnitsId\": \"\$projectInformation.choiceOfUnitsId\", " +
            "\"createdBy\": \"\$projectInformation.createdBy\" " +
            "} }",
    )
    fun findAllByTenantIdAndId(
        tenantId: String,
        id: String,
    ): Mono<ProjectInformationDto>

    @Aggregation(
        "{ \"\$match\": { \"tenantId\": :#{#tenantId}, \"_id\": :#{#id} } }",
        "{ \"\$unwind\": { \"path\": \"\$hydrologicInformation\", \"preserveNullAndEmptyArrays\": false } }",
        "{ \"\$project\": { " +
            "\"designStormId\": \"\$hydrologicInformation.designStormId\", " +
            "\"zeroToTenMinuteDuration\": \"\$hydrologicInformation.zeroToTenMinuteDuration\", " +
            "\"tenToFourtyMinuteDuration\": \"\$hydrologicInformation.tenToFourtyMinuteDuration\", " +
            "\"fourtyToOneHundredFiftyMinuteDuration\": \"\$hydrologicInformation.fourtyToOneHundredFiftyMinuteDuration\", " +
            "\"createdBy\": \"\$hydrologicInformation.createdBy\" " +
            "} }",
    )
    fun findHydrologicInformationByTenantIdAndId(
        tenantId: String,
        id: String,
    ): Mono<HydrologicInformationDto>
}
