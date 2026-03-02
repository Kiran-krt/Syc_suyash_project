package com.syc.dashboard.framework.common.security.user

import com.syc.dashboard.framework.common.security.dto.UsernamePasswordPrincipal
import com.syc.dashboard.framework.common.security.dto.enums.UserRole

interface UserPrincipal {
    val id: String
    val firstName: String
    val lastName: String
    val password: String
    fun getRole(): UserRole
    fun getUsername(): String
}

fun UserPrincipal.toUsernamePasswordPrincipal(): UsernamePasswordPrincipal =
    UsernamePasswordPrincipal(
        username = getUsername(),
        password = password,
        role = getRole().name,
        accountExpired = false,
        accountLocked = false,
        credentialsExpired = false,
        enabled = true,
        firstName = firstName,
        lastName = lastName,
        email = "",
    )
