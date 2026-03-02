package com.syc.dashboard.framework.core.dto

class ErrorResponse(
    message: String,
    val errorCode: String,
) : BaseResponse(message = message)
