package com.syc.dashboard.migration.service

import com.syc.dashboard.query.jobcode.entity.JobCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class JobCodeMigrationService(
    @Value("\${syc.system.tenantIds:syc}")
    private val tenantIds: List<String> = listOf("syc"),
    private val mongoTemplate: MongoTemplate,
) {

    fun updateJobCode() {
        tenantIds.forEach { tenantId ->
            val jobCodes = mongoTemplate.find(
                Query(
                    Criteria().andOperator(
                        Criteria.where("tenantId").`is`(tenantId),
                        Criteria.where("code").exists(true),
                    ),
                ),
                JobCode::class.java,
                "q_jobcode",
            )

            if (jobCodes.isEmpty()) {
                return@forEach
            }

            for (jobCodes in jobCodes) {
                val trimmedCode = jobCodes.code.trim()

                if (trimmedCode != jobCodes.code) {
                    val jobCodeQuery = Query(
                        Criteria.where("_id").`is`(jobCodes.id),
                    )

                    val updateOperation = Update()
                        .set("code", trimmedCode)

                    mongoTemplate.updateFirst(jobCodeQuery, updateOperation, "q_jobcode")

                    val eventSpecificQuery = Query(
                        Criteria().orOperator(
                            Criteria().andOperator(
                                Criteria.where("eventData._id").`is`(jobCodes.id),
                                Criteria.where("eventType").`is`("com.syc.dashboard.shared.jobcode.events.JobCodeUpdatedEvent"),
                                Criteria.where("eventData.tenantId").`is`(tenantId),
                            ),
                            Criteria().andOperator(
                                Criteria.where("eventData._id").`is`(jobCodes.id),
                                Criteria.where("eventType").`is`("com.syc.dashboard.shared.jobcode.events.JobCodeRegisteredEvent"),
                                Criteria.where("eventData.tenantId").`is`(tenantId),
                            ),

                        ),
                    )

                    val updateEventOperation = Update()
                        .set("eventData.code", trimmedCode)

                    mongoTemplate.updateMulti(eventSpecificQuery, updateEventOperation, "c_jobCodeEventModel")
                }
            }
        }
    }
}
