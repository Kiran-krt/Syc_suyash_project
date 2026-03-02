package com.syc.dashboard.framework.common.security.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UsernamePasswordPrincipal(
    private val username: String,
    private val password: String,
    private val role: String,
    private val accountExpired: Boolean,
    private val accountLocked: Boolean,
    private val credentialsExpired: Boolean,
    private val enabled: Boolean,
    val firstName: String,
    val lastName: String,
    val email: String,
) :
    UserDetails {

    override fun getAuthorities(): MutableCollection<GrantedAuthority> =
        setOf(role).map(::SimpleGrantedAuthority).toMutableSet()

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = !accountExpired

    override fun isAccountNonLocked(): Boolean = !accountLocked

    override fun isCredentialsNonExpired(): Boolean = !credentialsExpired

    override fun isEnabled(): Boolean = enabled
}
