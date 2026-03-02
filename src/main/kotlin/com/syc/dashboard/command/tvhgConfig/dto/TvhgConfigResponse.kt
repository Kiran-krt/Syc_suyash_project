package com.syc.dashboard.command.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class TvhgConfigResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
