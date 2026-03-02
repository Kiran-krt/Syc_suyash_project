package com.syc.dashboard.query.project.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.jobcode.entity.JobCode
import com.syc.dashboard.query.jobcode.repository.jpa.JobCodeRepository
import com.syc.dashboard.query.project.entity.Project
import com.syc.dashboard.query.project.exceptions.ProjectNotFoundException
import com.syc.dashboard.query.project.repository.jpa.ProjectRepository
import com.syc.dashboard.shared.project.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectEventHandler @Autowired constructor(
    private val projectRepository: ProjectRepository,
    private val jobCodeRepository: JobCodeRepository,
) : EventHandler {

    private fun on(event: ProjectRegisteredEvent) {
        val project = Project(
            id = event.id,
            projectCode = event.projectCode,
            createdBy = event.createdBy,
            createdOn = event.createdOn,
            status = event.status,
            description = event.description,
            quickBookDescription = event.quickBookDescription,
        ).buildEntity(event) as Project

        projectRepository.save(project)
    }

    private fun on(event: ProjectStatusUpdatedEvent) {
        val projectOptional = projectRepository.findById(event.id)
        if (projectOptional.isEmpty) {
            return
        }
        projectOptional.get().status = event.status
        projectRepository.save(projectOptional.get())
    }

    private fun on(event: ProjectAllFieldsUpdatedEvent) {
        val projectOptional = projectRepository.findById(event.id)
        if (projectOptional.isEmpty) {
            return
        }
        projectOptional.get().projectCode = event.projectCode
        projectOptional.get().description = event.description
        projectOptional.get().status = event.status
        projectOptional.get().quickBookDescription = event.quickBookDescription
        projectRepository.save(projectOptional.get())
    }

    private fun on(event: JobCodeAddedEvent) {
        val jobCode = JobCode(
            id = event.id,
            projectId = event.projectId,
            code = event.code,
            description = event.description,
            createdBy = event.createdBy,
            status = event.status,
            watcherList = event.watcherList,
            createdOn = event.createdOn,
        ).buildEntity(event) as JobCode
        jobCodeRepository.save(jobCode)
    }

    private fun on(event: JobCodeUpdatedByProjectIdEvent) {
        val jobCodeOptional = jobCodeRepository.findById(event.id)
        if (jobCodeOptional.isEmpty) {
            return
        }
        jobCodeOptional.get().status = event.status
        jobCodeOptional.get().description = event.description
        jobCodeOptional.get().watcherList = event.watcherList
        jobCodeRepository.save(jobCodeOptional.get())

        val projectOptional = projectRepository.findById(event.projectId)
        if (projectOptional.isEmpty) {
            throw ProjectNotFoundException("Project with id ${event.projectId} not found")
        }
        val project = projectOptional.get()
        project.projectCode = event.projectCode
        projectRepository.save(project)
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is ProjectRegisteredEvent -> on(event)
            is ProjectStatusUpdatedEvent -> on(event)
            is ProjectAllFieldsUpdatedEvent -> on(event)
            is JobCodeAddedEvent -> on(event)
            is JobCodeUpdatedByProjectIdEvent -> on(event)
        }
    }
}
