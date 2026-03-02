package com.syc.dashboard.query.settings.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.settings.entity.*
import com.syc.dashboard.query.settings.entity.ExpenseType
import com.syc.dashboard.query.settings.repository.jpa.ExpenseTypeRepository
import com.syc.dashboard.query.settings.repository.jpa.PayrollItemRepository
import com.syc.dashboard.query.settings.repository.jpa.SettingsRepository
import com.syc.dashboard.query.settings.repository.jpa.VehicleInfoRepository
import com.syc.dashboard.shared.settings.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SettingsEventHandler @Autowired constructor(
    private val settingsRepository: SettingsRepository,
    private val expenseTypeRepository: ExpenseTypeRepository,
    private val payrollItemRepository: PayrollItemRepository,
    private val vehicleInfoRepository: VehicleInfoRepository,
) : EventHandler {
    private fun on(event: SettingsRegisteredEvent) {
        val systemSettings = Settings.SystemSettings(
            dateFormat = event.dateFormat,
            timeZone = event.timeZone,
            timesheetDelayInHours = event.timesheetDelayInHours,
        )

        val mileageRateInfo = Settings.MileageRateInfo(
            mileageRateLabel = event.mileageRateLabel,
            mileageRateDescription = event.mileageRateDescription,
            mileageRateValue = event.mileageRateValue,
        )

        val yearlyQuarterInfo = Settings.YearlyQuarterInfo(
            yearlyQuarterName = event.yearlyQuarterName,
            yearlyQuarterDescription = event.yearlyQuarterDescription,
        )

        val settings = Settings(
            id = event.id,
            status = event.status,
            createdBy = event.createdBy,
            createdOn = event.createdDate,
            systemSettings = systemSettings,
            mileageRateInfo = mileageRateInfo,
            yearlyQuarterInfo = yearlyQuarterInfo,
        ).buildEntity(event) as Settings

        settingsRepository.save(settings)
    }

    private fun on(event: ExpenseTypeAddedEvent) {
        val expenseType = ExpenseType(
            id = event.id,
            settingsId = event.settingsId,
            expenseType = event.expenseType,
            expenseTypeDescription = event.expenseTypeDescription,
            expenseTypeStatus = event.expenseTypeStatus,
        ).buildEntity(event) as ExpenseType
        expenseTypeRepository.save(expenseType)
    }

    private fun on(event: SettingsDateFormatUpdatedEvent) {
        val settingsOptional = settingsRepository.findById(event.id)
        if (settingsOptional.isEmpty) {
            return
        }
        settingsOptional.get().systemSettings.dateFormat = event.dateFormat
        settingsRepository.save(settingsOptional.get())
    }

    private fun on(event: SettingsTimeZoneUpdatedEvent) {
        val settingsOptional = settingsRepository.findById(event.id)
        if (settingsOptional.isEmpty) {
            return
        }
        settingsOptional.get().systemSettings.timeZone = event.timeZone
        settingsRepository.save(settingsOptional.get())
    }

    private fun on(event: SettingsTimesheetDelayInHoursUpdatedEvent) {
        val settingsOptional = settingsRepository.findById(event.id)
        if (settingsOptional.isEmpty) {
            return
        }
        settingsOptional.get().systemSettings.timesheetDelayInHours = event.timesheetDelayInHours
        settingsRepository.save(settingsOptional.get())
    }

    private fun on(event: SettingsDeletedEvent) {
        val settingsOptional = settingsRepository.findById(event.id)
        if (settingsOptional.isEmpty) {
            return
        }
        settingsOptional.get().status = event.status
        settingsRepository.save(settingsOptional.get())
    }
    private fun on(event: SettingsStatusUpdatedEvent) {
        val settingsOptional = settingsRepository.findById(event.id)
        if (settingsOptional.isEmpty) {
            return
        }
        settingsOptional.get().status = event.status
        settingsRepository.save(settingsOptional.get())
    }

    private fun on(event: SettingsMileageRateUpdatedEvent) {
        val mileageRateOptional = settingsRepository.findById(event.id)
        if (mileageRateOptional.isEmpty) {
            return
        }
        mileageRateOptional.get().mileageRateInfo.mileageRateLabel = event.mileageRateLabel
        mileageRateOptional.get().mileageRateInfo.mileageRateDescription = event.mileageRateDescription
        mileageRateOptional.get().mileageRateInfo.mileageRateValue = event.mileageRateValue
        settingsRepository.save(mileageRateOptional.get())
    }

    private fun on(event: ExpenseTypeAllFieldsUpdatedEvent) {
        val expenseTypeOptional = expenseTypeRepository.findById(event.id)
        if (expenseTypeOptional.isEmpty) {
            return
        }
        expenseTypeOptional.get().expenseType = event.expenseType
        expenseTypeOptional.get().expenseTypeDescription = event.expenseTypeDescription
        expenseTypeOptional.get().expenseTypeStatus = event.expenseTypeStatus
        expenseTypeRepository.save(expenseTypeOptional.get())
    }

    private fun on(event: PayrollItemAddedEvent) {
        val payrollItem = PayrollItem(
            id = event.id,
            settingsId = event.settingsId,
            payrollItem = event.payrollItem,
            payrollItemDescription = event.payrollItemDescription,
            payrollItemStatus = event.payrollItemStatus,
        ).buildEntity(event) as PayrollItem
        payrollItemRepository.save(payrollItem)
    }

    private fun on(event: PayrollItemAllFieldsUpdatedEvent) {
        val payrollItemOptional = payrollItemRepository.findById(event.id)
        if (payrollItemOptional.isEmpty) {
            return
        }
        payrollItemOptional.get().payrollItem = event.payrollItem
        payrollItemOptional.get().payrollItemDescription = event.payrollItemDescription
        payrollItemOptional.get().payrollItemStatus = event.payrollItemStatus
        payrollItemRepository.save(payrollItemOptional.get())
    }

    private fun on(event: SettingsYearlyQuarterUpdatedEvent) {
        val yearlyQuarterOptional = settingsRepository.findById(event.id)
        if (yearlyQuarterOptional.isEmpty) {
            return
        }

        val settings = yearlyQuarterOptional.get()

        // Initialize nested field if it's null
        if (settings.yearlyQuarterInfo == null) {
            settings.yearlyQuarterInfo = Settings.YearlyQuarterInfo()
        }
        yearlyQuarterOptional.get().yearlyQuarterInfo?.yearlyQuarterName = event.yearlyQuarterName
        yearlyQuarterOptional.get().yearlyQuarterInfo?.yearlyQuarterDescription = event.yearlyQuarterDescription
        settingsRepository.save(settings)
    }

    private fun on(event: VehicleInfoRegisteredEvent) {
        val vehicleInfo = VehicleInfo(
            id = event.id,
            settingsId = event.settingsId,
            vehicleName = event.vehicleName,
            vehicleModel = event.vehicleModel,
            vehicleNumber = event.vehicleNumber,
            vehicleInsurance = event.vehicleInsurance,
            vehicleStatus = event.vehicleStatus,
        ).buildEntity(event) as VehicleInfo
        vehicleInfoRepository.save(vehicleInfo)
    }

    private fun on(event: VehicleInfoAllFieldsUpdatedEvent) {
        val vehicleInfoOptional = vehicleInfoRepository.findById(event.id)
        if (vehicleInfoOptional.isEmpty) {
            return
        }
        vehicleInfoOptional.get().vehicleName = event.vehicleName
        vehicleInfoOptional.get().vehicleModel = event.vehicleModel
        vehicleInfoOptional.get().vehicleNumber = event.vehicleNumber
        vehicleInfoOptional.get().vehicleInsurance = event.vehicleInsurance
        vehicleInfoOptional.get().vehicleStatus = event.vehicleStatus
        vehicleInfoRepository.save(vehicleInfoOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is SettingsRegisteredEvent -> on(event)
            is SettingsDateFormatUpdatedEvent -> on(event)
            is SettingsTimeZoneUpdatedEvent -> on(event)
            is SettingsTimesheetDelayInHoursUpdatedEvent -> on(event)
            is SettingsDeletedEvent -> on(event)
            is SettingsStatusUpdatedEvent -> on(event)
            is SettingsMileageRateUpdatedEvent -> on(event)
            is ExpenseTypeAddedEvent -> on(event)
            is ExpenseTypeAllFieldsUpdatedEvent -> on(event)
            is PayrollItemAddedEvent -> on(event)
            is PayrollItemAllFieldsUpdatedEvent -> on(event)
            is SettingsYearlyQuarterUpdatedEvent -> on(event)
            is VehicleInfoRegisteredEvent -> on(event)
            is VehicleInfoAllFieldsUpdatedEvent -> on(event)
        }
    }
}
