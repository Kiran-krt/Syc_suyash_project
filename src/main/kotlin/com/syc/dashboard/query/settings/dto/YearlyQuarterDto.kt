package com.syc.dashboard.query.settings.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto

class YearlyQuarterDto(
    var settingsId: String = "",
    tenantId: String = "",
    var yearlyQuarterName: String = "",
    var yearlyQuarterDescription: String = "",
) : TenantBaseDto(tenantId = tenantId)
