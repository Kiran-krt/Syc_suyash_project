package com.syc.dashboard.command.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class TvhgInputResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
