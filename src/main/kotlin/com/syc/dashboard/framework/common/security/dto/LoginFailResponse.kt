package com.syc.dashboard.framework.common.security.dto

data class LoginFailResponse(
    private val username: String = "Invalid username",
    private val password: String = "Invalid password",
)
