package com.syc.dashboard.query.systemconfig.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.systemconfig.entity.enums.SystemConfigStatusEnum

class UISystemConfigDto(
    tenantId: String = "",
    var appName: String = "",
    var logo: MutableList<DocumentIdDto> = mutableListOf(),
    var status: SystemConfigStatusEnum = SystemConfigStatusEnum.ACTIVE,
) : TenantBaseDto(tenantId = tenantId)
