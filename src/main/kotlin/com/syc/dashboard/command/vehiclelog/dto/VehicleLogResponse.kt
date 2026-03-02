package com.syc.dashboard.command.vehiclelog.dto

import com.syc.dashboard.framework.core.dto.BaseResponse

class VehicleLogResponse(
    message: String,
    val id: String,
) : BaseResponse(
    message = message,
)
