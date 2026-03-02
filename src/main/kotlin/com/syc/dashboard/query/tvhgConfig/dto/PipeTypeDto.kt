package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeTypeStatusEnum
import java.util.*

class PipeTypeDto(
    var id: String = "",
    var typeId: String = "",
    var description: String = "",
    var status: PipeTypeStatusEnum = PipeTypeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : BaseDto()
