package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import java.util.Date

class EmployeeRegisteredEvent(
    id: String,
    val title: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val employeeNumber: String = "",
    val joiningDate: Date = Date(),
    val role: UserRole = UserRole.EMPLOYEE,
    val status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    val managerId: String = "",
    val vacations: Int = 0,
    val personalDays: Int = 0,
    val email: String = "",
    val password: String = "welcome101",
    val passwordUpdated: Boolean = false,
    val supervisorList: MutableList<String> = mutableListOf(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeeRegisteredEvent"
    }
}
