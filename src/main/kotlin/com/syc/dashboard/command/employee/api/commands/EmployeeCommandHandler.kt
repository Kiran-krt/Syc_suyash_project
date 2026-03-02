package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.command.employee.entity.EmployeeAggregate
import com.syc.dashboard.command.employee.exception.EmployeeAlreadyExistException
import com.syc.dashboard.command.employee.exception.EmployeePasswordMisMatchException
import com.syc.dashboard.command.employee.exception.EmployeeRoleInvalidException
import com.syc.dashboard.command.employee.repository.jpa.EmployeeEventStoreRepository
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.utils.PasswordUtils
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.query.employee.exceptions.EmployeeNotFoundException
import com.syc.dashboard.shared.employee.events.EmployeeRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class EmployeeCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<EmployeeAggregate>,
    private val eventStoreRepository: EmployeeEventStoreRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : CommandHandler {

    private fun handle(command: RestoreEmployeeReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: RegisterEmployeeCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataEmail(
            eventType = EmployeeRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        )

        if (event.isNotEmpty()) {
            throw EmployeeAlreadyExistException("Email '${command.email}' is already registered for employee.")
        }
        if (command.password != command.confirmPassword) {
            throw EmployeePasswordMisMatchException("Password mismatch")
        }
        if (command.role == UserRole.ADMIN) {
            throw EmployeeRoleInvalidException("Employee role invalid")
        }

        command.password = bCryptPasswordEncoder.encode(command.password)

        val aggregate = EmployeeAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdatePasswordCommand) {
        if (command.password != command.confirmPassword) {
            throw EmployeePasswordMisMatchException("Password mismatch")
        }

        command.password = bCryptPasswordEncoder.encode(command.password)

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updatePassword(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeResetPasswordCommand) {
        val event = eventStoreRepository.findFirstByEventTypeAndEventDataEmail(
            eventType = EmployeeRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        ) ?: throw EmployeeNotFoundException("Email '${command.email}' not found for employee.")

        val aggregate = eventSourcingHandler.getById(event.aggregateIdentifier)
        aggregate.resetPassword(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeLogInCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataEmail(
            eventType = EmployeeRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        )

        if (event.isEmpty()) {
            throw EmployeeNotFoundException("User is invalid.")
        }

        command.id = event[0].aggregateIdentifier

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.loggedIn(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeLogOutCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.employeeLoggedOut(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdateRoleCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateRoleById(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdateProfileCommand) {
        if (command.password != command.confirmPassword) {
            throw EmployeePasswordMisMatchException("Password mismatch")
        }
        if (command.password.isNotEmpty()) {
            command.password = bCryptPasswordEncoder.encode(command.password)
        }
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateEmployeeProfile(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdateEmailCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateEmailById(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatusById(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: EmployeeUpdateMobileDeviceInfoCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateMobileDeviceInfo(command)
        eventSourcingHandler.save(aggregate)
    }
    private fun handle(command: EmployeeForgotPasswordCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataEmail(
            eventType = EmployeeRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        )

        if (event.isEmpty()) {
            throw EmployeeNotFoundException("Invalid email address.")
        }

        command.id = event[0].aggregateIdentifier
        command.passwordText = PasswordUtils.getRandPassword()
        command.password = bCryptPasswordEncoder.encode(command.passwordText)

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.forgotPassword(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreEmployeeReadDbCommand -> handle(command)
            is RegisterEmployeeCommand -> handle(command)
            is EmployeeUpdateAllFieldsCommand -> handle(command)
            is EmployeeResetPasswordCommand -> handle(command)
            is EmployeeUpdatePasswordCommand -> handle(command)
            is EmployeeLogInCommand -> handle(command)
            is EmployeeLogOutCommand -> handle(command)
            is EmployeeUpdateRoleCommand -> handle(command)
            is EmployeeUpdateProfileCommand -> handle(command)
            is EmployeeUpdateEmailCommand -> handle(command)
            is EmployeeUpdateStatusCommand -> handle(command)
            is EmployeeUpdateMobileDeviceInfoCommand -> handle(command)
            is EmployeeForgotPasswordCommand -> handle(command)
        }
    }
}
