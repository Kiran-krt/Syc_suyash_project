package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassEnum
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassStatusEnum

class MdStandardNumberListDto(
    var id: String = "",
    var structureClass: StructureClassEnum = StructureClassEnum.INLET,
    var mdStandardNumber: String = "",
    var type: String = "",
    var status: StructureClassStatusEnum = StructureClassStatusEnum.ACTIVE,
) : BaseDto()
