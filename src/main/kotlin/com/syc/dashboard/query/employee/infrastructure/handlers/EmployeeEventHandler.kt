package com.syc.dashboard.query.employee.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.employee.entity.Employee
import com.syc.dashboard.query.employee.entity.EmployeeMobileInfo
import com.syc.dashboard.query.employee.repository.jpa.EmployeeMobileInfoRepository
import com.syc.dashboard.query.employee.repository.jpa.EmployeeRepository
import com.syc.dashboard.shared.employee.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeEventHandler @Autowired constructor(
    private val employeeRepository: EmployeeRepository,
    private val employeeMobileInfoRepository: EmployeeMobileInfoRepository,
) : EventHandler {

    private fun on(event: EmployeeRegisteredEvent) {
        val employee = Employee(
            id = event.id,
            title = event.title,
            firstName = event.firstName,
            middleName = event.middleName,
            lastName = event.lastName,
            employeeNumber = event.employeeNumber,
            joiningDate = event.joiningDate,
            role = event.role,
            managerId = event.managerId,
            vacations = event.vacations,
            personalDays = event.personalDays,
            email = event.email,
            password = event.password,
            passwordUpdated = event.passwordUpdated,
            status = event.status,
            supervisorList = event.supervisorList,
        ).buildEntity(event) as Employee

        employeeRepository.save(employee)
    }

    private fun on(event: EmployeePasswordUpdatedEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().password = event.password
        employeeOptional.get().passwordUpdated = event.passwordUpdated
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeAllFieldsUpdatedEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().title = event.title
        employeeOptional.get().firstName = event.firstName
        employeeOptional.get().middleName = event.middleName
        employeeOptional.get().lastName = event.lastName
        employeeOptional.get().setRole(event.role)
        employeeOptional.get().employeeNumber = event.employeeNumber
        employeeOptional.get().joiningDate = event.joiningDate
        employeeOptional.get().status = event.status
        employeeOptional.get().managerId = event.managerId
        employeeOptional.get().vacations = event.vacations
        employeeOptional.get().personalDays = event.personalDays
        employeeOptional.get().supervisorList = event.supervisorList
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeePasswordResetEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().password = event.password
        employeeOptional.get().passwordUpdated = event.passwordUpdated
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeLoggedInEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().loggedIn = event.loggedIn
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeLoggedOutEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().loggedIn = event.loggedIn
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeRoleUpdatedByIdEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().setRole(event.role)
        employeeRepository.save(employeeOptional.get())
    }
    private fun on(event: EmployeeProfileUpdatedEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().firstName = event.firstName
        employeeOptional.get().lastName = event.lastName

        if (event.password.isNotEmpty()) {
            employeeOptional.get().password = event.password
        }
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeEmailUpdatedByIdEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().email = event.email
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeStatusUpdatedByIdEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().status = event.status
        employeeRepository.save(employeeOptional.get())
    }

    private fun on(event: EmployeeMobileDeviceInfoUpdatedEvent) {
        employeeMobileInfoRepository.deleteByTenantIdAndFirebasePushToken(
            tenantId = event.tenantId,
            firebasePushToken = event.firebasePushToken,
        )

        val employeeMobileInfo = EmployeeMobileInfo(
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
        ).buildEntity(event) as EmployeeMobileInfo

        employeeMobileInfoRepository.save(employeeMobileInfo)
    }

    private fun on(event: EmployeePasswordForgotEvent) {
        val employeeOptional = employeeRepository.findById(event.id)
        if (employeeOptional.isEmpty) {
            return
        }
        employeeOptional.get().password = event.password
        employeeOptional.get().passwordUpdated = event.passwordUpdated
        employeeOptional.get().email = event.email
        employeeRepository.save(employeeOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is EmployeeRegisteredEvent -> on(event)
            is EmployeeAllFieldsUpdatedEvent -> on(event)
            is EmployeePasswordResetEvent -> on(event)
            is EmployeePasswordUpdatedEvent -> on(event)
            is EmployeeLoggedInEvent -> on(event)
            is EmployeeLoggedOutEvent -> on(event)
            is EmployeeRoleUpdatedByIdEvent -> on(event)
            is EmployeeProfileUpdatedEvent -> on(event)
            is EmployeeEmailUpdatedByIdEvent -> on(event)
            is EmployeeStatusUpdatedByIdEvent -> on(event)
            is EmployeeMobileDeviceInfoUpdatedEvent -> on(event)
            is EmployeePasswordForgotEvent -> on(event)
        }
    }
}
