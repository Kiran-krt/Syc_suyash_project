package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureTypeStatusEnum
import java.util.*

class StructureTypeDto(
    var id: String = "",
    var typeId: String = "",
    var structureTypeName: String = "",
    var status: StructureTypeStatusEnum = StructureTypeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : BaseDto()
