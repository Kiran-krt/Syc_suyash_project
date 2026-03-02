package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.command.admin.entity.AdminAggregate
import com.syc.dashboard.command.admin.exceptions.AdminAlreadyExistException
import com.syc.dashboard.command.admin.exceptions.AdminNotExistException
import com.syc.dashboard.command.admin.exceptions.AdminPasswordMisMatchException
import com.syc.dashboard.command.admin.repository.jpa.AdminEventStoreRepository
import com.syc.dashboard.framework.common.utils.PasswordUtils
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.admin.events.AdminRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<AdminAggregate>,
    private val eventStoreRepository: AdminEventStoreRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : CommandHandler {

    private fun handle(command: RestoreAdminReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: RegisterAdminCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataEmail(
            eventType = AdminRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        )

        if (event.isNotEmpty()) {
            throw AdminAlreadyExistException("Email '${command.email}' is already registered.")
        }

        command.password = bCryptPasswordEncoder.encode(command.password)

        val aggregate = AdminAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminUpdatePasswordCommand) {
        command.password = bCryptPasswordEncoder.encode(command.password)

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updatePassword(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminUpdateFullNameCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateFullName(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminLogInCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataEmail(
            eventType = AdminRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        )

        if (event.isEmpty()) {
            throw AdminNotExistException("User is invalid.")
        }

        command.id = event[0].aggregateIdentifier

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.loggedIn(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminLogOutCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.loggedOut(command)
        eventSourcingHandler.save(aggregate)
    }
    private fun handle(command: AdminProfileUpdateCommand) {
        if (command.password != command.confirmPassword) {
            throw AdminPasswordMisMatchException("Password mismatch")
        }
        if (command.password.isNotEmpty()) {
            command.password = bCryptPasswordEncoder.encode(command.password)
        }

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateAdminProfile(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminUpdateEmailCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateEmailById(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminUpdateStatusCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateStatusById(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminUpdateMobileDeviceInfoCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateMobileDeviceInfo(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AdminForgetPasswordCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataEmail(
            eventType = AdminRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
            email = command.email,
        )

        if (event.isEmpty()) {
            throw AdminNotExistException("Invalid email address.")
        }

        command.id = event[0].aggregateIdentifier
        command.passwordText = PasswordUtils.getRandPassword()
        command.password = bCryptPasswordEncoder.encode(command.passwordText)

        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.forgetPassword(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreAdminReadDbCommand -> handle(command)
            is RegisterAdminCommand -> handle(command)
            is AdminUpdatePasswordCommand -> handle(command)
            is AdminUpdateFullNameCommand -> handle(command)
            is AdminLogInCommand -> handle(command)
            is AdminLogOutCommand -> handle(command)
            is AdminProfileUpdateCommand -> handle(command)
            is AdminUpdateEmailCommand -> handle(command)
            is AdminUpdateStatusCommand -> handle(command)
            is AdminUpdateMobileDeviceInfoCommand -> handle(command)
            is AdminForgetPasswordCommand -> handle(command)
        }
    }
}
