package com.syc.dashboard.query.projectreport.repository.jpa

import com.syc.dashboard.query.projectreport.entity.ProjectReport
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectReportRepository : MongoRepository<ProjectReport, String> {

    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): ProjectReport?
}
