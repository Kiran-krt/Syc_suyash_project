package com.syc.dashboard.query.projectreport.dto

import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum

class OutfallPhotoDocumentDto(
    var id: String = "",
    var projectReportId: String = "",
    var document: List<DocumentIdDto> = listOf(),
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
    var caption: String = "",
    var order: String = "",
    var uploadedBy: String = "",
)
