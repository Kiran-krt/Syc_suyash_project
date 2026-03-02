package com.syc.dashboard.command.jobcode.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class JobCodeResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
