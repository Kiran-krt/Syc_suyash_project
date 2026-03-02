package com.syc.dashboard.command.timesheet.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class TimesheetResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
