package com.syc.dashboard.command.projectreport.repository.jpa

import com.syc.dashboard.command.projectreport.entity.ProjectReportEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectReportEventStoreRepository : MongoRepository<ProjectReportEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<ProjectReportEventModel>
}
