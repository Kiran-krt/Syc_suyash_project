package com.syc.dashboard.command.projectreport.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class ProjectReportResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
