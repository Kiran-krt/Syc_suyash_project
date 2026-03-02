package com.syc.dashboard.query.project.repository.jpa

import com.syc.dashboard.query.project.entity.Project
import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectRepository : MongoRepository<Project, String> {

    fun findByTenantIdAndId(tenantId: String, id: String): Project?
}
