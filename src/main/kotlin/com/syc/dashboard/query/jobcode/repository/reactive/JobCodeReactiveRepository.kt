package com.syc.dashboard.query.jobcode.repository.reactive

import com.syc.dashboard.query.jobcode.entity.JobCode
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface JobCodeReactiveRepository : ReactiveMongoRepository<JobCode, String> {
    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, id: :#{#id}}}",
        "{\$lookup: " +
            "{ from: 'q_admin', " +
            "localField: 'createdBy', " +
            "foreignField: '_id', " +
            "as: 'createdByInfo' }}",
        "{\$unwind: { path: '\$createdByInfo', preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: 'q_project', " +
            "localField: 'projectId', " +
            "foreignField: '_id', " +
            "as: 'projectIdInfo' " +
            "}}",
        "{\$unwind: { path: '\$projectIdInfo', preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"watcherList\", " +
            "foreignField: \"_id\", " +
            "as: \"watcherListInfo\"" +
            "} } ",
    )
    fun findByTenantIdAndId(tenantId: String, id: String): Mono<JobCode>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, code: :#{#code}}, }",
        "{\$lookup: " +
            "{ from: \"q_admin\", " +
            "localField: \"createdBy\", " +
            "foreignField: \"_id\", " +
            "as: \"createdByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$createdByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndCode(tenantId: String, code: String): Mono<JobCode>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}}, }",
        "{\$lookup: " +
            "{ from: \"q_admin\", " +
            "localField: \"createdBy\", " +
            "foreignField: \"_id\", " +
            "as: \"createdByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$createdByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantId(tenantId: String): Flux<JobCode>

    @Aggregation(
        "{\$match: {" +
            "\"tenantId\": :#{#tenantId}," +
            "\"code\": {\"\$regex\": :#{#code}, \"\$options\": \"i\"}," +
            "\"status\": {\"\$in\": :#{#status}}" +
            "}}",
        "{\$lookup: " +
            "{ from: \"q_admin\", " +
            "localField: \"createdBy\", " +
            "foreignField: \"_id\", " +
            "as: \"createdByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$createdByInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"watcherList\", " +
            "foreignField: \"_id\", " +
            "as: \"watcherListInfo\"" +
            "} } ",
        "{\$lookup: " +
            "{ from: 'q_project', " +
            "localField: 'projectId', " +
            "foreignField: '_id', " +
            "as: 'projectIdInfo' " +
            "}}",
        "{\$unwind: { path: '\$projectIdInfo', preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndCodeContainsAndStatusIn(
        tenantId: String,
        code: String,
        status: List<JobCodeStatusEnum>,
        pageable: Pageable,
    ): Flux<JobCode>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, id: :#{#id}}, }",
        "{\$lookup: " +
            "{ from: \"q_admin\", " +
            "localField: \"createdBy\", " +
            "foreignField: \"_id\", " +
            "as: \"createdByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$createdByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndIdAndStatus(
        tenantId: String,
        id: String,
        status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    ): Flux<JobCode>

    fun findByTenantIdAndCodeContainsAndStatusIn(
        tenantId: String,
        code: String,
        status: List<JobCodeStatusEnum>,
    ): Flux<JobCode>

    fun findByTenantIdAndWatcherListContains(
        tenantId: String,
        watcherId: String,
    ): Flux<JobCode>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, projectId: :#{#projectId}}, }",
        "{'\$lookup': { " +
            "'from': 'q_project', " +
            "'localField': 'projectId', " +
            "'foreignField': '_id', " +
            "'as': 'projectIdInfo' " +
            "} }",
        "{'\$unwind': { 'path': '\$projectIdInfo', 'preserveNullAndEmptyArrays': true }}",
        "{\$lookup: " +
            "{ from: \"q_admin\", " +
            "localField: \"createdBy\", " +
            "foreignField: \"_id\", " +
            "as: \"createdByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$createdByInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"watcherList\", " +
            "foreignField: \"_id\", " +
            "as: \"watcherListInfo\"" +
            "} } ",
    )
    fun findJobCodeByTenantIdAndProjectId(
        tenantId: String,
        projectId: String,
    ): Flux<JobCode>
}
