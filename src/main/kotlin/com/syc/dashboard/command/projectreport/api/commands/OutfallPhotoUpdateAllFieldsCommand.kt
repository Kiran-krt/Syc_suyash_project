package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum

class OutfallPhotoUpdateAllFieldsCommand(
    id: String = "",
    var outfallPhotoId: String = "",
    var document: List<DocumentIdDto> = listOf(),
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
    var caption: String = "",
    var order: String = "",
) : TenantBaseCommand(id = id)
