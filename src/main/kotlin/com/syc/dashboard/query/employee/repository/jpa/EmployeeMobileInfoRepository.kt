package com.syc.dashboard.query.employee.repository.jpa

import com.syc.dashboard.query.employee.entity.EmployeeMobileInfo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeMobileInfoRepository : MongoRepository<EmployeeMobileInfo, String> {

    fun deleteByTenantIdAndFirebasePushToken(tenantId: String, firebasePushToken: String)
}
