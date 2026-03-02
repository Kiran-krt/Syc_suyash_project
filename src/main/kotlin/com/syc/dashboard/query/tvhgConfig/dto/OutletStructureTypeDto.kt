package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.OutletStructureTypeStatusEnum
import java.util.*

class OutletStructureTypeDto(
    var id: String = "",
    var outletStructureType: String = "",
    var status: OutletStructureTypeStatusEnum = OutletStructureTypeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : BaseDto()
