package com.syc.dashboard.migration.service

import com.syc.dashboard.query.jobcode.entity.CostCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class CostCodeMigrationService(
    @Value("\${syc.system.tenantIds:syc}")
    private val tenantIds: List<String> = listOf("syc"),
    private val mongoTemplate: MongoTemplate,
) {

    fun updateCostCode() {
        tenantIds.forEach { tenantId ->
            val costCodes = mongoTemplate.find(
                Query(
                    Criteria().andOperator(
                        Criteria.where("tenantId").`is`(tenantId),
                        Criteria.where("code").exists(true),
                    ),
                ),
                CostCode::class.java,
                "q_costcode",
            )

            if (costCodes.isEmpty()) {
                return@forEach
            }

            for (costCodes in costCodes) {
                val trimmedCode = costCodes.code.trim()

                if (trimmedCode != costCodes.code) {
                    val costCodeQuery = Query(
                        Criteria.where("_id").`is`(costCodes.id),
                    )

                    val updateOperation = Update()
                        .set("code", trimmedCode)

                    mongoTemplate.updateFirst(costCodeQuery, updateOperation, "q_costcode")

                    val eventSpecificQuery = Query(
                        Criteria().orOperator(
                            Criteria().andOperator(
                                Criteria.where("eventData._id").`is`(costCodes.id),
                                Criteria.where("eventType").`is`("com.syc.dashboard.shared.jobcode.events.CostCodeUpdatedEvent"),
                                Criteria.where("eventData.tenantId").`is`(tenantId),
                            ),
                            Criteria().andOperator(
                                Criteria.where("eventData._id").`is`(costCodes.id),
                                Criteria.where("eventType").`is`("com.syc.dashboard.shared.jobcode.events.CostCodeAddedEvent"),
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
