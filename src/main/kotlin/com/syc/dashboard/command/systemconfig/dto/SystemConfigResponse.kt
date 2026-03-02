package com.syc.dashboard.command.systemconfig.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class SystemConfigResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
