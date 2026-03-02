package com.syc.dashboard.query.admin.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.admin.entity.Admin
import com.syc.dashboard.query.admin.entity.AdminMobileInfo
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import com.syc.dashboard.query.admin.repository.jpa.AdminMobileInfoRepository
import com.syc.dashboard.query.admin.repository.jpa.AdminRepository
import com.syc.dashboard.shared.admin.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminEventHandler @Autowired constructor(
    private val adminRepository: AdminRepository,
    private val adminMobileInfoRepository: AdminMobileInfoRepository,
) : EventHandler {

    private fun on(event: AdminRegisteredEvent) {
        val admin = Admin(
            id = event.id,
            title = event.title,
            firstName = event.firstName,
            lastName = event.lastName,
            gender = event.gender,
            dateOfBirth = "",
            employeeNumber = "",
            email = event.email,
            password = event.password,
            passwordUpdated = event.passwordUpdated,
            status = AdminStatusEnum.ACTIVE,
            creationDate = event.createdDate,
        ).buildEntity(event) as Admin

        adminRepository.save(admin)
    }

    private fun on(event: AdminPasswordUpdatedEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().password = event.password
        adminOptional.get().passwordUpdated = event.passwordUpdated
        adminRepository.save(adminOptional.get())
    }

    private fun on(event: AdminFullNameUpdatedEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().firstName = event.firstName
        adminOptional.get().lastName = event.lastName
        adminRepository.save(adminOptional.get())
    }

    private fun on(event: AdminLoggedInEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().loggedIn = event.loggedIn
        adminRepository.save(adminOptional.get())
    }

    private fun on(event: AdminLoggedOutEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().loggedIn = event.loggedIn
        adminRepository.save(adminOptional.get())
    }
    private fun on(event: AdminProfileUpdatedEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().firstName = event.firstName
        adminOptional.get().lastName = event.lastName

        if (event.password.isNotEmpty()) {
            adminOptional.get().password = event.password
        }
        adminRepository.save(adminOptional.get())
    }

    private fun on(event: AdminEmailUpdatedByIdEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().email = event.email
        adminRepository.save(adminOptional.get())
    }
    private fun on(event: AdminStatusUpdatedByIdEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().status = event.status
        adminRepository.save(adminOptional.get())
    }
    private fun on(event: AdminMobileDeviceInfoUpdatedEvent) {
        adminMobileInfoRepository.deleteByTenantIdAndFirebasePushToken(
            tenantId = event.tenantId,
            firebasePushToken = event.firebasePushToken,
        )

        val adminMobileInfo = AdminMobileInfo(
            id = event.id,
            deviceUniqueId = event.deviceUniqueId,
            firebasePushToken = event.firebasePushToken,
            osType = event.osType,
            brand = event.brand,
            country = event.country,
            deviceId = event.deviceId,
            emulator = event.emulator,
            systemVersion = event.systemVersion,
            timeZone = event.timeZone,
            creationDate = event.createdDate,
        ).buildEntity(event) as AdminMobileInfo

        adminMobileInfoRepository.save(adminMobileInfo)
    }

    private fun on(event: AdminPasswordForgotEvent) {
        val adminOptional = adminRepository.findById(event.id)
        if (adminOptional.isEmpty) {
            return
        }
        adminOptional.get().password = event.password
        adminOptional.get().passwordUpdated = event.passwordUpdated
        adminOptional.get().email = event.email
        adminRepository.save(adminOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is AdminRegisteredEvent -> on(event)
            is AdminPasswordUpdatedEvent -> on(event)
            is AdminFullNameUpdatedEvent -> on(event)
            is AdminLoggedInEvent -> on(event)
            is AdminLoggedOutEvent -> on(event)
            is AdminProfileUpdatedEvent -> on(event)
            is AdminEmailUpdatedByIdEvent -> on(event)
            is AdminStatusUpdatedByIdEvent -> on(event)
            is AdminMobileDeviceInfoUpdatedEvent -> on(event)
            is AdminPasswordForgotEvent -> on(event)
        }
    }
}
