package com.syc.dashboard.query.projectreport.repository.reactive

import com.syc.dashboard.query.projectreport.dto.AppendixDto
import com.syc.dashboard.query.projectreport.entity.ProjectReport
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProjectReportReactiveRepository : ReactiveMongoRepository<ProjectReport, String> {
    @Aggregation(
        "{ \$match: { tenantId: :#{#tenantId}, _id: :#{#id} } }",
        "{ \$lookup: { " +
            "     from: 'q_admin', " +
            "     localField: 'createdBy', " +
            "     foreignField: '_id', " +
            "     as: 'createdByInfo' } }",
        "{ \$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "     from: 'q_employee', " +
            "     localField: 'createdBy', " +
            "     foreignField: '_id', " +
            "     as: 'createdByInfo' } }",
        "{ \$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true } }",
    )
    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): Mono<ProjectReport>

    @Aggregation(
        "{ \$match: { " +
            "tenantId: { \$regex: :#{#tenantId}, \$options: 'i' }, " +
            "projectName: { \$regex: :#{#projectName}, \$options: 'i' }, " +
            "status: { \$in: :#{#status} } " +
            "} }",
        "{ \$lookup: { " +
            "from: 'q_admin', " +
            "localField: 'createdBy', " +
            "foreignField: '_id', " +
            "as: 'createdByInfo' } }",
        "{ \$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "from: 'q_employee', " +
            "localField: 'createdBy', " +
            "foreignField: '_id', " +
            "as: 'createdByInfo' } }",
        "{ \$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true } }",
    )
    fun findAllByTenantIdAndProjectNameAndStatusIn(
        tenantId: String,
        projectName: String,
        status: List<ProjectReportStatusEnum>,
        pageable: Pageable,
    ): Flux<ProjectReport>

    @Aggregation(
        "{ \$match: { tenantId: { \$regex: :#{#tenantId}, \$options: 'i' } } }",
        "{ \$lookup: { " +
            "     from: 'q_admin', " +
            "     localField: 'createdBy', " +
            "     foreignField: '_id', " +
            "     as: 'createdByInfo' } }",
        "{ \$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true } }",
        "{ \$lookup: { " +
            "     from: 'q_employee', " +
            "     localField: 'createdBy', " +
            "     foreignField: '_id', " +
            "     as: 'createdByInfo' } }",
        "{ \$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true } }",
    )
    fun findAllByTenantId(
        tenantId: String,
    ): Flux<ProjectReport>

    @Aggregation(
        "{ '\$match': { 'tenantId': { '\$regex': :#{#tenantId}, '\$options': 'i' } } }",
        "{ '\$unwind': '\$outfallPhotoList' }",
        "{ '\$addFields': { 'outfallPhotoList.order': { '\$convert': { 'input': '\$outfallPhotoList.order', 'to': 'int', 'onError': 0, 'onNull': 0 } } } }",
        "{ '\$sort': { 'outfallPhotoList.order': 1 } }",
        "{ '\$match': { 'outfallPhotoList.projectReportId': :#{#id}, 'outfallPhotoList.status': { '\$ne': 'DELETED' } } }",
        "{ '\$group': { '_id': '\$_id', 'outfallPhotoList': { '\$push': '\$outfallPhotoList' }, 'tenantId': { '\$first': '\$tenantId' }, 'createdBy': { '\$first': '\$createdBy' } } }",
        "{ '\$lookup': { " +
            "     from: 'q_admin', " +
            "     localField: 'createdBy', " +
            "     foreignField: '_id', " +
            "     as: 'uploadedByInfo' } }",
        "{ '\$unwind': { path: '\$uploadedByInfo', preserveNullAndEmptyArrays: true } }",
    )
    fun findByTenantIdAndIdAndOutfallPhotoList(
        tenantId: String,
        id: String,
    ): Flux<ProjectReport>

    @Aggregation(
        "{ '\$match': { 'tenantId': { '\$regex': :#{#tenantId}, '\$options': 'i' } } }",
        "{ '\$unwind': '\$appendixList' }",
        "{ '\$addFields': { 'appendixList.order': { '\$toInt': '\$appendixList.order' } } }",
        "{ '\$sort': { 'appendixList.order': 1 } }",
        "{ '\$match': { 'appendixList.projectReportId': :#{#id}, 'appendixList.status': { '\$ne': 'DELETED' } } }",
        "{ '\$lookup': { " +
            "     from: 'q_admin', " +
            "     localField: 'appendixList.createdBy', " +
            "     foreignField: '_id', " +
            "     as: 'appendixList.createdByInfo' } }",
        "{ '\$unwind': { path: '\$appendixList.createdByInfo', preserveNullAndEmptyArrays: true } }",
        "{ '\$replaceRoot': { 'newRoot': '\$appendixList' } }",
    )
    fun findByTenantIdAndIdAndAppendixList(
        tenantId: String,
        id: String,
    ): Flux<AppendixDto>
}
