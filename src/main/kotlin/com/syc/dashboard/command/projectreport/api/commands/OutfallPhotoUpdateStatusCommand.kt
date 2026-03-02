package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum

class OutfallPhotoUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var outfallPhotoId: String = "",
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
