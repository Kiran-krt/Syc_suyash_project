package com.syc.dashboard.command.jobcode.entity

import com.syc.dashboard.command.jobcode.api.commands.*
import com.syc.dashboard.command.jobcode.exceptions.CostCodeAlreadyExistException
import com.syc.dashboard.command.jobcode.exceptions.JobCodeStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.shared.jobcode.events.*
import java.util.*

class JobCodeAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var code: String = ""
    var projectId: String = ""
    var createdBy: String = ""
    var costCodeList: MutableList<CostCode> = mutableListOf()
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE
    var watcherList: MutableList<String> = mutableListOf()
    var description: String = ""
    var quickBookDescription: String = ""

    class CostCode(
        var id: String = "",
        var tenantId: String = "",
        var code: String = "",
        var description: String = "",
        var createdBy: String = "",
        var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
        var createdOn: Date = Date(),
    )

    constructor(command: RegisterJobCodeCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            JobCodeRegisteredEvent(
                id = command.id,
                code = command.code,
                projectId = command.projectId,
                createdBy = command.createdBy,
                status = command.status,
                createdDate = createdDate,
                description = command.description,
                quickBookDescription = command.quickBookDescription,
                watcherList = command.watcherList,
            ).buildEvent(command),
        )
    }

    fun apply(event: JobCodeRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        tenantId = event.tenantId
        code = event.code
        projectId = event.projectId
        createdBy = event.createdBy
        status = event.status
        description = event.description
        quickBookDescription = event.quickBookDescription
        active = true
    }

    fun updateStatus(command: JobCodeUpdateStatusCommand) {
        if (!active) {
            throw JobCodeStateChangeNotAllowedForInactiveStatusException(
                "Jobcode Update Status Exception!",
            )
        }
        raiseEvent(
            JobCodeStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: JobCodeStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun addCostCode(command: AddCostCodeCommand) {
        if (!active) {
            throw JobCodeStateChangeNotAllowedForInactiveStatusException(
                "CostCode cannot be added!",
            )
        }

        val trimmedCode = command.code.trim()

        // validate duplicate cost code
        if (costCodeList.isNotEmpty() && costCodeList.any { it.code.trim() == trimmedCode }) {
            throw CostCodeAlreadyExistException("Cost code already exist with code ${command.code}!")
        }

        raiseEvent(
            CostCodeAddedEvent(
                id = command.id,
                jobCodeId = command.jobCodeId,
                code = command.code,
                description = command.description,
                createdBy = command.createdBy,
                status = command.status,
                createdOn = command.createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: CostCodeAddedEvent) {
        buildAggregateRoot(event)
        costCodeList.add(
            CostCode(
                id = event.id,
                tenantId = event.tenantId,
                code = event.code,
                description = event.description,
                createdBy = event.createdBy,
                status = event.status,
                createdOn = event.createdOn,
            ),
        )
    }

    fun updateCostCode(command: UpdateCostCodeCommand) {
        if (!active) {
            throw JobCodeStateChangeNotAllowedForInactiveStatusException(
                "CostCode cannot be updated!",
            )
        }
        raiseEvent(
            CostCodeUpdatedEvent(
                id = command.id,
                jobCodeId = command.jobCodeId,
                description = command.description,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: CostCodeUpdatedEvent) {
        buildAggregateRoot(event)
        val costCode = costCodeList.find { costCode -> costCode.id == event.id }
        if (costCode != null) {
            costCode.status = event.status
            costCode.description = event.description
        }
    }

    fun updateJobCode(command: UpdateJobCodeCommand) {
        if (!active) {
            throw JobCodeStateChangeNotAllowedForInactiveStatusException(
                "Jobcode Update Status And Description Exception!",
            )
        }
        raiseEvent(
            JobCodeUpdatedEvent(
                id = command.id,
                status = command.status,
                watcherList = command.watcherList,
                description = command.description,
                projectId = command.projectId,
                quickBookDescription = command.quickBookDescription,
            ).buildEvent(command),
        )
    }

    fun apply(event: JobCodeUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
        description = event.description
        watcherList = event.watcherList
        projectId = event.projectId
        quickBookDescription = event.quickBookDescription
    }
}
