package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhginput.entity.enums.ProjectInformationStatusEnum
import java.util.*

class ProjectInformationDto(
    var title: String = "",
    var numberOfStructures: String = "",
    var numberOfFlowPaths: String = "",
    var tailwaterElevationOutlet: String = "",
    var hydrologicInformation: String = "",
    var drawingInformation: String = "",
    var status: ProjectInformationStatusEnum = ProjectInformationStatusEnum.ACTIVE,
    var choiceOfUnitsId: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
) : BaseDto()
