package com.syc.dashboard.query.timesheet.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.settings.dto.PayrollItemDto

class DayDetailsDto(
    var timesheetId: String = "",
    tenantId: String = "",
    var timesheetRowId: String = "",
    var day: String = "",
    var numberOfHours: Double = 0.0,
    var comment: String = "",
    var payrollItemId: String = "",
    var payrollItemInfo: PayrollItemDto? = null,
) : TenantBaseDto(tenantId = tenantId)
