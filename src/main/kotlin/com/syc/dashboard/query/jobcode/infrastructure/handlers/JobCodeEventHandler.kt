package com.syc.dashboard.query.jobcode.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.jobcode.entity.CostCode
import com.syc.dashboard.query.jobcode.entity.JobCode
import com.syc.dashboard.query.jobcode.repository.jpa.CostCodeRepository
import com.syc.dashboard.query.jobcode.repository.jpa.JobCodeRepository
import com.syc.dashboard.shared.jobcode.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobCodeEventHandler @Autowired constructor(
    private val jobCodeRepository: JobCodeRepository,
    private val costCodeRepository: CostCodeRepository,
) : EventHandler {

    private fun on(event: JobCodeRegisteredEvent) {
        val jobCode = JobCode(
            id = event.id,
            code = event.code,
            projectId = event.projectId,
            createdBy = event.createdBy,
            createdOn = event.createdDate,
            status = event.status,
            watcherList = event.watcherList,
            description = event.description,
            quickBookDescription = event.quickBookDescription,
        ).buildEntity(event) as JobCode

        jobCodeRepository.save(jobCode)
    }

    private fun on(event: JobCodeStatusUpdatedEvent) {
        val jobCodeOptional = jobCodeRepository.findById(event.id)
        if (jobCodeOptional.isEmpty) {
            return
        }
        jobCodeOptional.get().status = event.status
        jobCodeRepository.save(jobCodeOptional.get())
    }

    private fun on(event: CostCodeAddedEvent) {
        val costCode = CostCode(
            id = event.id,
            tenantId = event.tenantId,
            jobCodeId = event.jobCodeId,
            code = event.code,
            description = event.description,
            createdBy = event.createdBy,
            status = event.status,
            createdOn = event.createdOn,
        ).buildEntity(event) as CostCode
        costCodeRepository.save(costCode)
    }

    private fun on(event: CostCodeUpdatedEvent) {
        val costCodeOptional = costCodeRepository.findById(event.id)
        if (costCodeOptional.isEmpty) {
            return
        }
        costCodeOptional.get().status = event.status
        costCodeOptional.get().description = event.description
        costCodeRepository.save(costCodeOptional.get())
    }

    private fun on(event: JobCodeUpdatedEvent) {
        val jobCodeOptional = jobCodeRepository.findById(event.id)
        if (jobCodeOptional.isEmpty) {
            return
        }
        jobCodeOptional.get().status = event.status
        jobCodeOptional.get().description = event.description
        jobCodeOptional.get().watcherList = event.watcherList
        jobCodeOptional.get().projectId = event.projectId
        jobCodeOptional.get().quickBookDescription = event.quickBookDescription
        jobCodeRepository.save(jobCodeOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is JobCodeRegisteredEvent -> on(event)
            is JobCodeStatusUpdatedEvent -> on(event)
            is CostCodeAddedEvent -> on(event)
            is CostCodeUpdatedEvent -> on(event)
            is JobCodeUpdatedEvent -> on(event)
        }
    }
}
