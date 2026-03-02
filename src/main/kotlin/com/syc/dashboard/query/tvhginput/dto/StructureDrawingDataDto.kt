package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import java.util.*

class StructureDrawingDataDto(
    var id: String = "",
    tenantId: String = "",
    var structureInformationId: String = "",
    var existingOrProposedIndex: String = "",
    var mdshaStandardNumber: String = "",
    var typeOfStructure: String = "",
    var structureClass: String = "",
    var station: String = "",
    var offset: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
    var structureListInfo: StructureInformationDto? = null,
) : TenantBaseDto(tenantId = tenantId)
