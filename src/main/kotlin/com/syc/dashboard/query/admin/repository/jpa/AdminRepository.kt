package com.syc.dashboard.query.admin.repository.jpa

import com.syc.dashboard.query.admin.entity.Admin
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : MongoRepository<Admin, String> {
    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, status: :#{#status}}}, }",
        "{\$project: { _id: 1}}",
    )
    fun findListOfIdsByTenantIdAndStatus(tenantId: String, status: AdminStatusEnum): List<String>

    fun findByTenantIdAndId(tenantId: String, id: String): Admin?
}
