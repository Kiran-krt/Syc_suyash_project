package com.syc.dashboard.framework.common.security.dto

import com.syc.dashboard.framework.core.dto.BaseDto

class AuthTokenDto(
    val message: String,
    val tokenData: TokenData? = null,
) : BaseDto()

data class TokenData(
    val access_token: String,
    val expires_in: String = "",
    val refresh_expires_in: Long = -1,
    val refresh_token: String = "",
    val token_type: String = "",
    val id_token: String = "",
    val not_before_policy: Int = -1,
    val session_state: String = "",
    val scope: String = "",
)
