package com.syc.dashboard.command.admin.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class AdminResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
