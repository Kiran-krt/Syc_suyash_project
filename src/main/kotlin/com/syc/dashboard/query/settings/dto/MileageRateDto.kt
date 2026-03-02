package com.syc.dashboard.query.settings.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto

class MileageRateDto(
    var settingsId: String = "",
    tenantId: String = "",
    var mileageRateLabel: String = "",
    var mileageRateDescription: String = "",
    var mileageRateValue: Double = 0.655,
) : TenantBaseDto(tenantId = tenantId)
