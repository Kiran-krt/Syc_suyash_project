package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.command.settings.entity.SettingsAggregate
import com.syc.dashboard.command.settings.exceptions.SettingsWithTenantIdAlreadyExistException
import com.syc.dashboard.command.settings.repository.jpa.SettingsEventStoreRepository
import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.shared.settings.events.SettingsRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SettingsCommandHandler @Autowired constructor(
    private val eventSourcingHandler: EventSourcingHandler<SettingsAggregate>,
    private val eventStoreRepository: SettingsEventStoreRepository,
) : CommandHandler {

    private fun handle(command: RestoreSettingsReadDbCommand) {
        eventSourcingHandler.republishEvents()
    }

    private fun handle(command: RegisterSettingsCommand) {
        val event = eventStoreRepository.findByEventTypeAndEventDataTenantId(
            eventType = SettingsRegisteredEvent::class.java.typeName,
            tenantId = command.tenantId,
        )
        if (event.isNotEmpty()) {
            throw SettingsWithTenantIdAlreadyExistException("Settings with tenantId '${command.tenantId}' is already registered.")
        }
        val aggregate = SettingsAggregate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SettingsUpdateDateFormatCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateDateFormat(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SettingsUpdateTimeZoneCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimeZone(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SettingsUpdateTimesheetDelayInHoursCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateTimesheetDelayInHours(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: DeleteSettingsCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.deleteSettings(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SettingsUpdateStatusByIdCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateSettingsStatus(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SettingsUpdateMileageRateCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateMileageRate(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddExpenseTypeCommand) {
        val aggregate = eventSourcingHandler.getById(command.settingsId)
        aggregate.addExpenseType(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: ExpenseTypeUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.settingsId)
        aggregate.updateExpenseTypeAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: AddPayrollItemCommand) {
        val aggregate = eventSourcingHandler.getById(command.settingsId)
        aggregate.addPayrollItem(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: PayrollItemUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.settingsId)
        aggregate.updatePayrollItemAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: SettingsUpdateYearlyQuarterCommand) {
        val aggregate = eventSourcingHandler.getById(command.id)
        aggregate.updateYearlyQuarter(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: RegisterVehicleInfoCommand) {
        val aggregate = eventSourcingHandler.getById(command.settingsId)
        aggregate.registerVehicleInfo(command)
        eventSourcingHandler.save(aggregate)
    }

    private fun handle(command: VehicleInfoUpdateAllFieldsCommand) {
        val aggregate = eventSourcingHandler.getById(command.settingsId)
        aggregate.updateVehicleInfoAllFields(command)
        eventSourcingHandler.save(aggregate)
    }

    override fun <T : BaseCommand> handle(command: T) {
        when (command) {
            is RestoreSettingsReadDbCommand -> handle(command)
            is RegisterSettingsCommand -> handle(command)
            is SettingsUpdateDateFormatCommand -> handle(command)
            is SettingsUpdateTimeZoneCommand -> handle(command)
            is SettingsUpdateTimesheetDelayInHoursCommand -> handle(command)
            is DeleteSettingsCommand -> handle(command)
            is SettingsUpdateStatusByIdCommand -> handle(command)
            is SettingsUpdateMileageRateCommand -> handle(command)
            is AddExpenseTypeCommand -> handle(command)
            is ExpenseTypeUpdateAllFieldsCommand -> handle(command)
            is AddPayrollItemCommand -> handle(command)
            is PayrollItemUpdateAllFieldsCommand -> handle(command)
            is SettingsUpdateYearlyQuarterCommand -> handle(command)
            is RegisterVehicleInfoCommand -> handle(command)
            is VehicleInfoUpdateAllFieldsCommand -> handle(command)
        }
    }
}
