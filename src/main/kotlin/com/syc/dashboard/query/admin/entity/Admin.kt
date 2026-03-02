package com.syc.dashboard.query.admin.entity

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.user.UserPrincipal
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_admin")
class Admin(
    @Id
    override val id: String,
    var title: String,
    override var firstName: String,
    override var lastName: String,
    var gender: String,
    var dateOfBirth: String = "",
    var employeeNumber: String = "",
    var email: String,
    override var password: String = "welcome1",
    var passwordUpdated: Boolean = false,
    var profileUrl: String = "",
    var status: AdminStatusEnum,
    var loggedIn: Boolean = false,
    val creationDate: Date,
) : TenantBaseEntity(), UserPrincipal {
    override fun getRole(): UserRole = UserRole.ADMIN

    override fun getUsername(): String = email
}
