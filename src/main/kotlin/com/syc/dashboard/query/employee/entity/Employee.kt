package com.syc.dashboard.query.employee.entity

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.user.UserPrincipal
import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_employee")
class Employee(
    @Id
    override val id: String,
    var title: String = "",
    override var firstName: String = "",
    var middleName: String = "",
    override var lastName: String = "",
    var employeeNumber: String = "",
    var joiningDate: Date = Date(),
    private var role: UserRole = UserRole.EMPLOYEE,
    var managerId: String = "",
    var managerInfo: BaseEntity? = null,
    var vacations: Int = 0,
    var personalDays: Int = 0,
    var email: String = "",
    override var password: String = "welcome101",
    var passwordUpdated: Boolean = false,
    var loggedIn: Boolean = false,
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    var supervisorList: MutableList<String> = mutableListOf(),
) : TenantBaseEntity(), UserPrincipal {
    override fun getRole(): UserRole = role

    fun setRole(role: UserRole) {
        this.role = role
    }

    override fun getUsername(): String = email
}
