package com.syc.dashboard.command.project.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class ProjectResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
