package com.syc.dashboard.command.settings.entity

import com.syc.dashboard.command.settings.api.commands.*
import com.syc.dashboard.command.settings.exceptions.ExpenseTypeStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.command.settings.exceptions.PayrollItemStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.command.settings.exceptions.SettingsStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.command.settings.exceptions.VehicleInfoStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.settings.entity.enums.ExpenseTypeStatusEnum
import com.syc.dashboard.query.settings.entity.enums.PayrollItemStatusEnum
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum
import com.syc.dashboard.shared.settings.events.*
import java.util.*

class SettingsAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var systemSettings = SystemSettings()
    var mileageRateInfo = MileageRateInfo()
    var status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE
    var createdBy: String = ""
    var expenseTypeList: MutableList<ExpenseTypeInfo> = mutableListOf()
    var payrollItemList: MutableList<PayrollItemInfo> = mutableListOf()
    var yearlyQuarterInfo = YearlyQuarterInfo()
    var vehicleInfoList: MutableList<VehicleInfo> = mutableListOf()

    class SystemSettings {
        var dateFormat: String = "MM/dd/YYYY"
        var timeZone: String = "EST"
        var timesheetDelayInHours: Int = 8
        var createdOn: Date = Date()
    }
    class MileageRateInfo {
        var mileageRateLabel: String = ""
        var mileageRateDescription: String = ""
        var mileageRateValue: Double = 0.655
    }

    class ExpenseTypeInfo(
        var id: String = "",
        var settingsId: String = "",
        var expenseType: String = "",
        var expenseTypeDescription: String = "",
        var expenseTypeStatus: ExpenseTypeStatusEnum = ExpenseTypeStatusEnum.ACTIVE,
    )

    class PayrollItemInfo(
        var id: String = "",
        var settingsId: String = "",
        var payrollItem: String = "",
        var payrollItemDescription: String = "",
        var payrollItemStatus: PayrollItemStatusEnum = PayrollItemStatusEnum.ACTIVE,
    )

    class YearlyQuarterInfo(
        var yearlyQuarterName: String = "",
        var yearlyQuarterDescription: String = "",
    )

    class VehicleInfo(
        var id: String = "",
        var settingsId: String = "",
        var vehicleName: String = "",
        var vehicleModel: String = "",
        var vehicleNumber: String = "",
        var vehicleInsurance: Boolean = false,
        var vehicleStatus: VehicleStatusEnum = VehicleStatusEnum.ACTIVE,
    )

    constructor(command: RegisterSettingsCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            SettingsRegisteredEvent(
                id = command.id,
                dateFormat = command.dateFormat,
                timeZone = command.timeZone,
                timesheetDelayInHours = command.timesheetDelayInHours,
                status = command.status,
                createdBy = command.createdBy,
                mileageRateLabel = command.mileageRateLabel,
                mileageRateDescription = command.mileageRateDescription,
                mileageRateValue = command.mileageRateValue,
                yearlyQuarterName = command.yearlyQuarterName,
                yearlyQuarterDescription = command.yearlyQuarterDescription,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        systemSettings.dateFormat = event.dateFormat
        systemSettings.timeZone = event.timeZone
        systemSettings.timesheetDelayInHours = event.timesheetDelayInHours
        status = event.status
        mileageRateInfo.mileageRateLabel = event.mileageRateLabel
        mileageRateInfo.mileageRateDescription = event.mileageRateDescription
        mileageRateInfo.mileageRateValue = event.mileageRateValue
        yearlyQuarterInfo.yearlyQuarterName = event.yearlyQuarterName
        yearlyQuarterInfo.yearlyQuarterDescription = event.yearlyQuarterDescription
        createdBy = event.createdBy
    }

    fun addExpenseType(command: AddExpenseTypeCommand) {
        if (!active) {
            throw ExpenseTypeStateChangeNotAllowedForInactiveStatusException(
                "Expense type cannot be added!",
            )
        }
        raiseEvent(
            ExpenseTypeAddedEvent(
                id = command.id,
                settingsId = command.settingsId,
                expenseType = command.expenseType,
                expenseTypeDescription = command.expenseTypeDescription,
                expenseTypeStatus = command.expenseTypeStatus,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseTypeAddedEvent) {
        buildAggregateRoot(event)
        expenseTypeList.add(
            ExpenseTypeInfo(
                id = event.id,
                settingsId = event.settingsId,
                expenseType = event.expenseType,
                expenseTypeDescription = event.expenseTypeDescription,
                expenseTypeStatus = event.expenseTypeStatus,
            ),
        )
    }

    fun updateDateFormat(command: SettingsUpdateDateFormatCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings dateformat cannot be updated!",
            )
        }
        raiseEvent(
            SettingsDateFormatUpdatedEvent(
                id = command.id,
                dateFormat = command.dateFormat,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsDateFormatUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        systemSettings.dateFormat = event.dateFormat
    }

    fun updateTimeZone(command: SettingsUpdateTimeZoneCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings timezone cannot be updated!",
            )
        }
        raiseEvent(
            SettingsTimeZoneUpdatedEvent(
                id = command.id,
                timeZone = command.timeZone,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsTimeZoneUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        systemSettings.timeZone = event.timeZone
    }

    fun updateTimesheetDelayInHours(command: SettingsUpdateTimesheetDelayInHoursCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings timesheet delay in hours cannot be updated!",
            )
        }
        raiseEvent(
            SettingsTimesheetDelayInHoursUpdatedEvent(
                id = command.id,
                timesheetDelayInHours = command.timesheetDelayInHours,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsTimesheetDelayInHoursUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        systemSettings.timesheetDelayInHours = event.timesheetDelayInHours
    }

    fun deleteSettings(command: DeleteSettingsCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings can not be deleted!",
            )
        }
        raiseEvent(
            SettingsDeletedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsDeletedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateSettingsStatus(command: SettingsUpdateStatusByIdCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings Status cannot be updated!",
            )
        }
        raiseEvent(
            SettingsStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateMileageRate(command: SettingsUpdateMileageRateCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings mileage rate cannot be updated!",
            )
        }
        raiseEvent(
            SettingsMileageRateUpdatedEvent(
                id = command.id,
                mileageRateLabel = command.mileageRateLabel,
                mileageRateDescription = command.mileageRateDescription,
                mileageRateValue = command.mileageRateValue,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsMileageRateUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        mileageRateInfo.mileageRateLabel = event.mileageRateLabel
        mileageRateInfo.mileageRateDescription = event.mileageRateDescription
        mileageRateInfo.mileageRateValue = event.mileageRateValue
    }

    fun updateExpenseTypeAllFields(command: ExpenseTypeUpdateAllFieldsCommand) {
        if (!active) {
            throw ExpenseTypeStateChangeNotAllowedForInactiveStatusException(
                "Expense type all fields cannot be updated!",
            )
        }
        raiseEvent(
            ExpenseTypeAllFieldsUpdatedEvent(
                id = command.id,
                settingsId = command.settingsId,
                expenseType = command.expenseType,
                expenseTypeDescription = command.expenseTypeDescription,
                expenseTypeStatus = command.expenseTypeStatus,
            ).buildEvent(command),
        )
    }

    fun apply(event: ExpenseTypeAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        val expenseTypeInfo = expenseTypeList.find { expenseTypeInfo -> expenseTypeInfo.id == event.id }
        if (expenseTypeInfo != null) {
            expenseTypeInfo.settingsId = event.settingsId
            expenseTypeInfo.expenseType = event.expenseType
            expenseTypeInfo.expenseTypeDescription = event.expenseTypeDescription
            expenseTypeInfo.expenseTypeStatus = event.expenseTypeStatus
        }
    }

    fun addPayrollItem(command: AddPayrollItemCommand) {
        if (!active) {
            throw PayrollItemStateChangeNotAllowedForInactiveStatusException(
                "Payroll Item cannot be added!",
            )
        }
        raiseEvent(
            PayrollItemAddedEvent(
                id = command.id,
                settingsId = command.settingsId,
                payrollItem = command.payrollItem,
                payrollItemDescription = command.payrollItemDescription,
                payrollItemStatus = command.payrollItemStatus,
            ).buildEvent(command),
        )
    }

    fun apply(event: PayrollItemAddedEvent) {
        buildAggregateRoot(event)
        payrollItemList.add(
            PayrollItemInfo(
                id = event.id,
                settingsId = event.settingsId,
                payrollItem = event.payrollItem,
                payrollItemDescription = event.payrollItemDescription,
                payrollItemStatus = event.payrollItemStatus,
            ),
        )
    }

    fun updatePayrollItemAllFields(command: PayrollItemUpdateAllFieldsCommand) {
        if (!active) {
            throw PayrollItemStateChangeNotAllowedForInactiveStatusException(
                "Payroll Item all fields cannot be updated!",
            )
        }
        raiseEvent(
            PayrollItemAllFieldsUpdatedEvent(
                id = command.id,
                settingsId = command.settingsId,
                payrollItem = command.payrollItem,
                payrollItemDescription = command.payrollItemDescription,
                payrollItemStatus = command.payrollItemStatus,
            ).buildEvent(command),
        )
    }

    fun apply(event: PayrollItemAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        val payrollItemInfo = payrollItemList.find { payrollItemInfo -> payrollItemInfo.id == event.id }
        if (payrollItemInfo != null) {
            payrollItemInfo.settingsId = event.settingsId
            payrollItemInfo.payrollItem = event.payrollItem
            payrollItemInfo.payrollItemDescription = event.payrollItemDescription
            payrollItemInfo.payrollItemStatus = event.payrollItemStatus
        }
    }

    fun updateYearlyQuarter(command: SettingsUpdateYearlyQuarterCommand) {
        if (!active) {
            throw SettingsStateChangeNotAllowedForInactiveStatusException(
                "Settings yearly quarter cannot be updated!",
            )
        }
        raiseEvent(
            SettingsYearlyQuarterUpdatedEvent(
                id = command.id,
                yearlyQuarterName = command.yearlyQuarterName,
                yearlyQuarterDescription = command.yearlyQuarterDescription,
            ).buildEvent(command),
        )
    }

    fun apply(event: SettingsYearlyQuarterUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        yearlyQuarterInfo.yearlyQuarterName = event.yearlyQuarterName
        yearlyQuarterInfo.yearlyQuarterDescription = event.yearlyQuarterDescription
    }

    fun registerVehicleInfo(command: RegisterVehicleInfoCommand) {
        if (!active) {
            throw VehicleInfoStateChangeNotAllowedForInactiveStatusException(
                "Vehicle info cannot be added!",
            )
        }
        raiseEvent(
            VehicleInfoRegisteredEvent(
                id = command.id,
                settingsId = command.settingsId,
                vehicleName = command.vehicleName,
                vehicleModel = command.vehicleModel,
                vehicleNumber = command.vehicleNumber,
                vehicleInsurance = command.vehicleInsurance,
                vehicleStatus = command.vehicleStatus,
            ).buildEvent(command),
        )
    }

    fun apply(event: VehicleInfoRegisteredEvent) {
        buildAggregateRoot(event)
        vehicleInfoList.add(
            VehicleInfo(
                id = event.id,
                settingsId = event.settingsId,
                vehicleName = event.vehicleName,
                vehicleModel = event.vehicleModel,
                vehicleNumber = event.vehicleNumber,
                vehicleInsurance = event.vehicleInsurance,
                vehicleStatus = event.vehicleStatus,
            ),
        )
    }

    fun updateVehicleInfoAllFields(command: VehicleInfoUpdateAllFieldsCommand) {
        if (!active) {
            throw VehicleInfoStateChangeNotAllowedForInactiveStatusException(
                "Vehicle info all fields cannot be updated!",
            )
        }
        raiseEvent(
            VehicleInfoAllFieldsUpdatedEvent(
                id = command.id,
                settingsId = command.settingsId,
                vehicleName = command.vehicleName,
                vehicleModel = command.vehicleModel,
                vehicleNumber = command.vehicleNumber,
                vehicleInsurance = command.vehicleInsurance,
                vehicleStatus = command.vehicleStatus,
            ).buildEvent(command),
        )
    }

    fun apply(event: VehicleInfoAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        val vehicleInfo = vehicleInfoList.find { vehicleInfo -> vehicleInfo.id == event.id }
        if (vehicleInfo != null) {
            vehicleInfo.settingsId = event.settingsId
            vehicleInfo.vehicleName = event.vehicleName
            vehicleInfo.vehicleModel = event.vehicleModel
            vehicleInfo.vehicleNumber = event.vehicleNumber
            vehicleInfo.vehicleInsurance = event.vehicleInsurance
            vehicleInfo.vehicleStatus = event.vehicleStatus
        }
    }
}
