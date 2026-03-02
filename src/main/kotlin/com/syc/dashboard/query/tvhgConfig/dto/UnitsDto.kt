package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.UnitStatusEnum
import java.util.*

class UnitsDto(
    var id: String = "",
    var unitName: String = "",
    var status: UnitStatusEnum = UnitStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : BaseDto()
