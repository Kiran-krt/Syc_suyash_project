package com.syc.dashboard.query.jobcode.repository.jpa

import com.syc.dashboard.query.jobcode.entity.JobCode
import org.springframework.data.mongodb.repository.MongoRepository

interface JobCodeRepository : MongoRepository<JobCode, String> {

    fun findByTenantIdAndId(tenantId: String, id: String): JobCode?
}
