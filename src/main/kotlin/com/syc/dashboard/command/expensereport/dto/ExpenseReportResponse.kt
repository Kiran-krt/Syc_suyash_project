package com.syc.dashboard.command.expensereport.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class ExpenseReportResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
