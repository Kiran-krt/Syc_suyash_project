package com.syc.dashboard.query.admin.repository.jpa

import com.syc.dashboard.query.admin.entity.AdminMobileInfo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminMobileInfoRepository : MongoRepository<AdminMobileInfo, String> {

    fun deleteByTenantIdAndFirebasePushToken(tenantId: String, firebasePushToken: String)
}
