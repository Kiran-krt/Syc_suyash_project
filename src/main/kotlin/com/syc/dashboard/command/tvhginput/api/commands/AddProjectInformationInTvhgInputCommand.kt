package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhginput.entity.enums.ProjectInformationStatusEnum

class AddProjectInformationInTvhgInputCommand(
    id: String = "",
    val title: String = "",
    val numberOfStructures: String = "",
    val numberOfFlowPaths: String = "",
    val tailwaterElevationOutlet: String = "",
    val hydrologicInformation: String = "",
    val drawingInformation: String = "",
    val status: ProjectInformationStatusEnum = ProjectInformationStatusEnum.ACTIVE,
    val choiceOfUnitsId: String = "",
    val createdBy: String = "",
) : TenantBaseCommand(id = id)
