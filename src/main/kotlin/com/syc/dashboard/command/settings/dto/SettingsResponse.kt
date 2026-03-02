package com.syc.dashboard.command.settings.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class SettingsResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
