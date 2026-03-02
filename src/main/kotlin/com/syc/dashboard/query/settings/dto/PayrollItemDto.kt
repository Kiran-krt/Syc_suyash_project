package com.syc.dashboard.query.settings.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.settings.entity.enums.PayrollItemStatusEnum

class PayrollItemDto(
    var id: String = "",
    tenantId: String = "",
    var settingsId: String = "",
    var payrollItem: String = "",
    var payrollItemDescription: String = "",
    var payrollItemStatus: PayrollItemStatusEnum = PayrollItemStatusEnum.ACTIVE,
) : TenantBaseDto(tenantId = tenantId)
