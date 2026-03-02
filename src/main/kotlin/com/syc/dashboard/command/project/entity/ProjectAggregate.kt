package com.syc.dashboard.command.project.entity

import com.syc.dashboard.command.jobcode.exceptions.JobCodeAlreadyExistException
import com.syc.dashboard.command.project.api.commands.*
import com.syc.dashboard.command.project.exceptions.ProjectEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.project.exceptions.ProjectStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.query.project.dto.ProjectDto
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import com.syc.dashboard.shared.project.events.*
import java.util.*

class ProjectAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var projectCode: String = ""
    var createdBy: String = ""
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE
    var description: String = ""
    var jobCodeList: MutableList<JobCode> = mutableListOf()
    var quickBookDescription: String = ""

    class JobCode(
        var id: String = "",
        var code: String = "",
        var createdBy: String = "",
        var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
        var watcherList: MutableList<String> = mutableListOf(),
        var description: String = "",
        var projectId: String = "",
        var projectIdInfo: ProjectDto? = null,
        var createdOn: Date = Date(),
    )

    constructor(command: RegisterProjectCommand) : this() {
        val createdOn = Date()
        raiseEvent(
            ProjectRegisteredEvent(
                id = command.id,
                projectCode = command.projectCode,
                createdBy = command.createdBy,
                status = command.status,
                createdOn = createdOn,
                description = command.description,
                quickBookDescription = command.quickBookDescription,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        tenantId = event.tenantId
        projectCode = event.projectCode
        createdBy = event.createdBy
        status = event.status
        description = event.description
        quickBookDescription = event.quickBookDescription
    }

    fun updateStatus(command: ProjectUpdateStatusCommand) {
        if (!active) {
            throw ProjectStateChangeNotAllowedForInactiveStatusException(
                "Project Update Status Exception!",
            )
        }
        raiseEvent(
            ProjectStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateAllFields(command: ProjectUpdateAllFieldsCommand) {
        if (!active) {
            throw ProjectEventStreamNotExistInEventStoreException(
                "Project all fields updated exception!",
            )
        }
        raiseEvent(
            ProjectAllFieldsUpdatedEvent(
                id = command.id,
                projectCode = command.projectCode,
                status = command.status,
                description = command.description,
                quickBookDescription = command.quickBookDescription,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        projectCode = event.projectCode
        status = event.status
        description = event.description
        quickBookDescription = event.quickBookDescription
    }

    fun addJobCode(command: AddJobCodeCommand) {
        if (!active) {
            throw ProjectEventStreamNotExistInEventStoreException(
                "JobCode cannot be added!",
            )
        }

        // validate duplicate job code
        if (jobCodeList.isNotEmpty() && jobCodeList.any { it.code == command.code }) {
            throw JobCodeAlreadyExistException("Job code already exist with code ${command.code}!")
        }

        raiseEvent(
            JobCodeAddedEvent(
                id = command.id,
                projectId = command.projectId,
                code = command.code,
                description = command.description,
                createdBy = command.createdBy,
                status = command.status,
                watcherList = command.watcherList,
            ).buildEvent(command),
        )
    }

    fun apply(event: JobCodeAddedEvent) {
        buildAggregateRoot(event)
        jobCodeList.add(
            JobCode(
                projectId = event.projectId,
                code = event.code,
                description = event.description,
                createdBy = event.createdBy,
                status = event.status,
                watcherList = event.watcherList,
                createdOn = event.createdOn,
            ),
        )
    }

    fun updateJobCodeByProjectId(command: UpdateJobCodeByProjectIdCommand) {
        if (!active) {
            throw ProjectStateChangeNotAllowedForInactiveStatusException(
                "JobCode cannot be updated!",
            )
        }
        raiseEvent(
            JobCodeUpdatedByProjectIdEvent(
                id = command.id,
                projectId = command.projectId,
                projectCode = command.projectCode,
                status = command.status,
                description = command.description,
                watcherList = command.watcherList,
            ).buildEvent(command),
        )
    }

    fun apply(event: JobCodeUpdatedByProjectIdEvent) {
        buildAggregateRoot(event)
        val jobCode = jobCodeList.find { jobCode -> jobCode.id == event.id }
        if (jobCode != null) {
            jobCode.status = event.status
            jobCode.description = event.description
            jobCode.watcherList = event.watcherList
        }
    }
}
