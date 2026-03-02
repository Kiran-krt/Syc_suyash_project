package com.syc.dashboard.query.project.repository.reactive

import com.syc.dashboard.query.project.entity.Project
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProjectReactiveRepository : ReactiveMongoRepository<Project, String> {

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
    fun findByTenantIdAndIdAndGetDetails(
        tenantId: String,
        id: String,
    ): Mono<Project>

    @Aggregation(
        "{ \$match: { tenantId: :#{#tenantId} } }",
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
    ): Flux<Project>

    @Aggregation(
        "{ '\$match': { 'tenantId': :#{#tenantId}, 'projectCode': { '\$regex': :#{#projectCode}, '\$options': 'i' }, " +
            "'status': { '\$in': :#{#status} } } }",
        "{ '\$lookup': {" +
            " 'from': 'q_admin'," +
            " 'localField': 'createdBy'," +
            " 'foreignField': '_id'," +
            " 'as': 'createdByInfo'" +
            " } }",
        "{ '\$unwind': { 'path': '\$createdByInfo', 'preserveNullAndEmptyArrays': true } }",
    )
    fun findByTenantIdAndProjectCodeContains(
        tenantId: String,
        projectCode: String,
        status: List<ProjectStatusEnum>,
        pageable: Pageable,
    ): Flux<Project>

    fun findAllByTenantIdAndStatusOrderByProjectCodeAsc(
        tenantId: String,
        status: ProjectStatusEnum,
    ): Flux<Project>
}
