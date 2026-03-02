package com.syc.dashboard.command.document.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class DocumentResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
