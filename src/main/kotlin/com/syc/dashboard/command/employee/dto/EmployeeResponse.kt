package com.syc.dashboard.command.employee.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class EmployeeResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
