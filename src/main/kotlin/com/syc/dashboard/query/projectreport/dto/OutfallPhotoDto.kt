package com.syc.dashboard.query.projectreport.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum
import java.util.*

class OutfallPhotoDto(
    var id: String = "",
    tenantId: String = "",
    var projectReportId: String = "",
    var document: List<DocumentIdDto> = listOf(),
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
    var caption: String = "",
    var order: String = "",
    var uploadedBy: String = "",
    var uploadedByInfo: AdminDto? = null,
) : TenantBaseDto(tenantId = tenantId)
