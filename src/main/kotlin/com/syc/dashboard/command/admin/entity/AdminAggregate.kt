package com.syc.dashboard.command.admin.entity

import com.syc.dashboard.command.admin.api.commands.*
import com.syc.dashboard.command.admin.exceptions.AdminEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.admin.exceptions.AdminStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import com.syc.dashboard.shared.admin.events.*
import java.util.*

class AdminAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    private var title: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var gender: String = ""
    private var dateOfBirth: String = ""
    private var employeeNumber: String = ""
    private var loggedIn: Boolean = false
    private var webLoggedIn: Boolean = false
    private var email: String = ""
    private var password: String = "welcome1"
    private var passwordUpdated: Boolean = false
    private var profileUrl: String = ""
    private var profileUrlFileName: String = ""
    private var status: AdminStatusEnum = AdminStatusEnum.ACTIVE
    private var mobileDeviceInfo = MobileDeviceInfo()

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

    constructor(command: RegisterAdminCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            AdminRegisteredEvent(
                id = command.id,
                title = command.title,
                firstName = command.firstName,
                lastName = command.lastName,
                gender = command.gender,
                dateOfBirth = command.dateOfBirth,
                employeeNumber = command.employeeNumber,
                email = command.email,
                password = command.password,
                passwordUpdated = false,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        title = event.title
        firstName = event.firstName
        lastName = event.lastName
        gender = event.gender
        dateOfBirth = event.dateOfBirth
        employeeNumber = event.employeeNumber
        email = event.email
        password = event.password
        passwordUpdated = event.passwordUpdated
    }

    fun updatePassword(command: AdminUpdatePasswordCommand) {
        if (!active) {
            throw AdminEventStreamNotExistInEventStoreException(
                "Admin Update Password Exception!",
            )
        }
        raiseEvent(
            AdminPasswordUpdatedEvent(
                id = command.id,
                password = command.password,
                passwordUpdated = true,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminPasswordUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        password = event.password
        passwordUpdated = event.passwordUpdated
    }

    fun updateFullName(command: AdminUpdateFullNameCommand) {
        if (!active) {
            throw AdminEventStreamNotExistInEventStoreException(
                "Admin FullName Updated Exception!",
            )
        }
        raiseEvent(
            AdminFullNameUpdatedEvent(
                id = command.id,
                firstName = command.firstName,
                lastName = command.lastName,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminFullNameUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        firstName = event.firstName
        lastName = event.lastName
    }

    fun loggedIn(command: AdminLogInCommand) {
        if (!active) {
            throw AdminStateChangeNotAllowedForInactiveStatusException(
                "Login status cannot be changed for closed admin!",
            )
        }
        raiseEvent(
            AdminLoggedInEvent(
                id = command.id,
                loggedIn = command.loggedIn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminLoggedInEvent) {
        buildAggregateRoot(event)
        id = event.id
        loggedIn = event.loggedIn
    }

    fun loggedOut(command: AdminLogOutCommand) {
        if (!active) {
            throw AdminStateChangeNotAllowedForInactiveStatusException(
                "Login status cannot be changed for closed admin!",
            )
        }
        raiseEvent(
            AdminLoggedOutEvent(
                id = command.id,
                loggedIn = command.loggedIn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminLoggedOutEvent) {
        buildAggregateRoot(event)
        id = event.id
        loggedIn = event.loggedIn
    }

    fun updateAdminProfile(command: AdminProfileUpdateCommand) {
        if (!active) {
            throw AdminEventStreamNotExistInEventStoreException(
                "Admin Update Profile Exception!",
            )
        }
        raiseEvent(
            AdminProfileUpdatedEvent(
                id = command.id,
                firstName = command.firstName,
                lastName = command.lastName,
                password = command.password,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminProfileUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        firstName = event.firstName
        lastName = event.lastName
        password = event.password
    }

    fun updateEmailById(command: AdminUpdateEmailCommand) {
        if (!active) {
            throw AdminEventStreamNotExistInEventStoreException(
                "Admin Email Updated Exception!",
            )
        }
        raiseEvent(
            AdminEmailUpdatedByIdEvent(
                id = command.id,
                email = command.email,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminEmailUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        email = event.email
    }

    fun updateStatusById(command: AdminUpdateStatusCommand) {
        if (!active) {
            throw AdminEventStreamNotExistInEventStoreException(
                "Admin Status Updated Exception!",
            )
        }
        raiseEvent(
            AdminStatusUpdatedByIdEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminStatusUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateMobileDeviceInfo(command: AdminUpdateMobileDeviceInfoCommand) {
        if (!active) {
            throw AdminStateChangeNotAllowedForInactiveStatusException(
                "Mobile device info cannot be changed for closed admin!",
            )
        }

        val createdDate = Date()
        raiseEvent(
            AdminMobileDeviceInfoUpdatedEvent(
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
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminMobileDeviceInfoUpdatedEvent) {
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

    fun forgetPassword(command: AdminForgetPasswordCommand) {
        if (!active) {
            throw AdminEventStreamNotExistInEventStoreException(
                "Admin Forget Password Exception!",
            )
        }
        raiseEvent(
            AdminPasswordForgotEvent(
                id = command.id,
                password = command.password,
                passwordText = command.passwordText,
                email = command.email,
                passwordUpdated = true,
            ).buildEvent(command),
        )
    }

    fun apply(event: AdminPasswordForgotEvent) {
        buildAggregateRoot(event)
        id = event.id
        password = event.password
        passwordUpdated = event.passwordUpdated
        email = event.email
    }
}
