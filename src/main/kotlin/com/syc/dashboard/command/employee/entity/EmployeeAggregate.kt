package com.syc.dashboard.command.employee.entity

import com.syc.dashboard.command.employee.api.commands.*
import com.syc.dashboard.command.employee.exception.EmployeeEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.employee.exception.EmployeeStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import com.syc.dashboard.shared.employee.events.*
import java.util.Date

class EmployeeAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var title: String = ""
    var firstName: String = ""
    var middleName: String = ""
    var lastName: String = ""
    var employeeNumber: String = ""
    var joiningDate: Date = Date()
    var role: UserRole = UserRole.EMPLOYEE
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE
    var managerId: String = ""
    var vacations: Int = 0
    var personalDays: Int = 0
    var email: String = ""
    var password: String = "welcome101"
    var passwordUpdated: Boolean = false
    var loggedIn: Boolean = false
    var mobileDeviceInfo = MobileDeviceInfo()
    var supervisorList: MutableList<String> = mutableListOf()

    class MobileDeviceInfo {
        var deviceUniqueId: String = ""
        var firebasePushToken: String = ""
        var osType: String = ""
        var brand: String = ""
        var country: String = ""
        var deviceId: String = ""
        var emulator = false
        var systemVersion: String = ""
        var timeZone: String = ""
    }

    constructor(command: RegisterEmployeeCommand) : this() {
        raiseEvent(
            EmployeeRegisteredEvent(
                id = command.id,
                title = command.title,
                firstName = command.firstName,
                middleName = command.middleName,
                lastName = command.lastName,
                employeeNumber = command.employeeNumber,
                joiningDate = command.joiningDate,
                role = command.role,
                status = command.status,
                managerId = command.managerId,
                vacations = command.vacations,
                personalDays = command.personalDays,
                email = command.email,
                password = command.password,
                passwordUpdated = false,
                supervisorList = command.supervisorList,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        title = event.title
        firstName = event.firstName
        middleName = event.middleName
        lastName = event.lastName
        employeeNumber = event.employeeNumber
        joiningDate = event.joiningDate
        role = event.role
        status = event.status
        managerId = event.managerId
        vacations = event.vacations
        personalDays = event.personalDays
        email = event.email
        password = event.password
        passwordUpdated = event.passwordUpdated
        supervisorList = event.supervisorList
    }

    fun updatePassword(command: EmployeeUpdatePasswordCommand) {
        if (!active) {
            throw EmployeeEventStreamNotExistInEventStoreException(
                "Employee Update Password Exception!",
            )
        }
        raiseEvent(
            EmployeePasswordUpdatedEvent(
                id = command.id,
                password = command.password,
                passwordUpdated = true,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeePasswordUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        password = event.password
        passwordUpdated = event.passwordUpdated
    }

    fun updateAllFields(command: EmployeeUpdateAllFieldsCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Employee Title Update Exception!",
            )
        }
        raiseEvent(
            EmployeeAllFieldsUpdatedEvent(
                id = command.id,
                title = command.title,
                firstName = command.firstName,
                middleName = command.middleName,
                lastName = command.lastName,
                role = command.role,
                employeeNumber = command.employeeNumber,
                joiningDate = command.joiningDate,
                status = command.status,
                managerId = command.managerId,
                vacations = command.vacations,
                personalDays = command.personalDays,
                supervisorList = command.supervisorList,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        title = event.title
        firstName = event.firstName
        middleName = event.middleName
        lastName = event.lastName
        role = event.role
        employeeNumber = event.employeeNumber
        joiningDate = event.joiningDate
        status = event.status
        managerId = event.managerId
        vacations = event.vacations
        personalDays = event.personalDays
        supervisorList = event.supervisorList
    }

    fun resetPassword(command: EmployeeResetPasswordCommand) {
        if (!active) {
            throw EmployeeEventStreamNotExistInEventStoreException(
                "Employee Reset Password Exception!",
            )
        }
        raiseEvent(
            EmployeePasswordResetEvent(
                id = command.id,
                email = command.email,
                password = "ABCD_new_pwd", // TODO: change his
                passwordUpdated = false,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeePasswordResetEvent) {
        buildAggregateRoot(event)
        password = event.password
        passwordUpdated = event.passwordUpdated
    }

    fun loggedIn(command: EmployeeLogInCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Login status cannot be changed for closed employee!",
            )
        }
        raiseEvent(
            EmployeeLoggedInEvent(
                id = command.id,
                loggedIn = command.loggedIn,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeLoggedInEvent) {
        buildAggregateRoot(event)
        id = event.id
        loggedIn = event.loggedIn
    }

    fun employeeLoggedOut(command: EmployeeLogOutCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Login status cannot be changed for closed employee!",
            )
        }
        raiseEvent(
            EmployeeLoggedOutEvent(
                id = command.id,
                loggedIn = command.loggedIn,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeLoggedOutEvent) {
        buildAggregateRoot(event)
        id = event.id
        loggedIn = event.loggedIn
    }

    fun updateRoleById(command: EmployeeUpdateRoleCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Employee role cannot be updated!",
            )
        }
        raiseEvent(
            EmployeeRoleUpdatedByIdEvent(
                id = command.id,
                role = command.role,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeRoleUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        role = event.role
    }

    fun updateEmployeeProfile(command: EmployeeUpdateProfileCommand) {
        if (!active) {
            throw EmployeeEventStreamNotExistInEventStoreException(
                "Employee Update Profile Exception!",
            )
        }
        raiseEvent(
            EmployeeProfileUpdatedEvent(
                id = command.id,
                firstName = command.firstName,
                lastName = command.lastName,
                password = command.password,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeProfileUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        firstName = event.firstName
        lastName = event.lastName
        password = event.password
    }

    fun updateEmailById(command: EmployeeUpdateEmailCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Employee email cannot be updated!",
            )
        }
        raiseEvent(
            EmployeeEmailUpdatedByIdEvent(
                id = command.id,
                email = command.email,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeEmailUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        email = event.email
    }

    fun updateStatusById(command: EmployeeUpdateStatusCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Employee email cannot be updated!",
            )
        }
        raiseEvent(
            EmployeeStatusUpdatedByIdEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeStatusUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateMobileDeviceInfo(command: EmployeeUpdateMobileDeviceInfoCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Mobile device info cannot be changed for closed employee!",
            )
        }

        raiseEvent(
            EmployeeMobileDeviceInfoUpdatedEvent(
                id = this.id,
                deviceUniqueId = command.deviceUniqueId,
                firebasePushToken = command.firebasePushToken,
                osType = command.osType,
                brand = command.brand,
                country = command.country,
                deviceId = command.deviceId,
                emulator = command.emulator,
                systemVersion = command.systemVersion,
                timeZone = command.timeZone,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeeMobileDeviceInfoUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        mobileDeviceInfo.deviceUniqueId = event.deviceUniqueId
        mobileDeviceInfo.firebasePushToken = event.firebasePushToken
        mobileDeviceInfo.osType = event.osType
        mobileDeviceInfo.brand = event.brand
        mobileDeviceInfo.country = event.country
        mobileDeviceInfo.deviceId = event.deviceId
        mobileDeviceInfo.emulator = event.emulator
        mobileDeviceInfo.systemVersion = event.systemVersion
        mobileDeviceInfo.timeZone = event.timeZone
    }

    fun forgotPassword(command: EmployeeForgotPasswordCommand) {
        if (!active) {
            throw EmployeeStateChangeNotAllowedForInactiveStatusException(
                "Employee Forget Password Exception!",
            )
        }
        raiseEvent(
            EmployeePasswordForgotEvent(
                id = command.id,
                password = command.password,
                passwordText = command.passwordText,
                email = command.email,
                passwordUpdated = true,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmployeePasswordForgotEvent) {
        buildAggregateRoot(event)
        id = event.id
        password = event.password
        passwordUpdated = event.passwordUpdated
        email = event.email
    }
}
