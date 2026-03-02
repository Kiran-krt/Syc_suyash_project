package com.syc.dashboard.command.vehiclelog.entity

import com.syc.dashboard.command.vehiclelog.api.commands.RegisterVehicleLogCommand
import com.syc.dashboard.command.vehiclelog.api.commands.VehicleLogUpdateAllFieldsCommand
import com.syc.dashboard.command.vehiclelog.api.commands.VehicleLogUpdateStatusByIdCommand
import com.syc.dashboard.command.vehiclelog.exceptions.VehicleLogStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogAllFieldsUpdatedEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogRegisteredEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogStatusUpdatedByIdEvent
import java.util.*

class VehicleLogAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    private var vehicleId: String = ""
    private var date: String = ""
    private var startingMileage: String = ""
    private var endingMileage: String = ""
    private var startGasRange: String = ""
    private var endGasRange: String = ""
    private var gasCost: String = ""
    private var jobCodeId: String = ""
    private var costCodeId: String = ""
    private var keysHandover: Boolean = false
    private var staffInitial: String = ""
    private var insurancePresent: Boolean = false
    private var ezPassAvailable: Boolean = false
    private var tollCost: String = ""
    private var gasRefilled: Boolean = false
    private var remarks: String = ""
    private var fieldBagBroughtInside: Boolean = false
    private var status: VehicleLogStatusEnum = VehicleLogStatusEnum.REVIEW
    private var vehicleLogDocuments: List<DocumentIdDto> = listOf()
    private var createdBy: String = ""
    private var accompany: MutableList<String> = mutableListOf()

    constructor(command: RegisterVehicleLogCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            VehicleLogRegisteredEvent(
                id = command.id,
                vehicleId = command.vehicleId,
                date = command.date,
                startingMileage = command.startingMileage,
                endingMileage = command.endingMileage,
                startGasRange = command.startGasRange,
                endGasRange = command.endGasRange,
                gasCost = command.gasCost,
                jobCodeId = command.jobCodeId,
                costCodeId = command.costCodeId,
                keysHandover = command.keysHandover,
                staffInitial = command.staffInitial,
                insurancePresent = command.insurancePresent,
                ezPassAvailable = command.ezPassAvailable,
                tollCost = command.tollCost,
                gasRefilled = command.gasRefilled,
                remarks = command.remarks,
                fieldBagBroughtInside = command.fieldBagBroughtInside,
                status = command.status,
                vehicleLogDocuments = command.vehicleLogDocuments,
                createdBy = command.createdBy,
                accompany = command.accompany,
                createdOn = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: VehicleLogRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        vehicleId = event.vehicleId
        date = event.date
        startingMileage = event.startingMileage
        endingMileage = event.endingMileage
        startGasRange = event.startGasRange
        endGasRange = event.endGasRange
        gasCost = event.gasCost
        jobCodeId = event.jobCodeId
        costCodeId = event.costCodeId
        keysHandover = event.keysHandover
        staffInitial = event.staffInitial
        insurancePresent = event.insurancePresent
        ezPassAvailable = event.ezPassAvailable
        tollCost = event.tollCost
        gasRefilled = event.gasRefilled
        remarks = event.remarks
        fieldBagBroughtInside = event.fieldBagBroughtInside
        status = event.status
        vehicleLogDocuments = event.vehicleLogDocuments
        createdBy = event.createdBy
        accompany = event.accompany
    }

    fun updateAllFields(command: VehicleLogUpdateAllFieldsCommand) {
        if (!active) {
            throw VehicleLogStateChangeNotAllowedForInactiveStatusException(
                "Vehicle Log Update Exception!",
            )
        }
        raiseEvent(
            VehicleLogAllFieldsUpdatedEvent(
                id = command.id,
                vehicleId = command.vehicleId,
                date = command.date,
                startingMileage = command.startingMileage,
                endingMileage = command.endingMileage,
                startGasRange = command.startGasRange,
                endGasRange = command.endGasRange,
                gasCost = command.gasCost,
                jobCodeId = command.jobCodeId,
                costCodeId = command.costCodeId,
                keysHandover = command.keysHandover,
                staffInitial = command.staffInitial,
                insurancePresent = command.insurancePresent,
                ezPassAvailable = command.ezPassAvailable,
                tollCost = command.tollCost,
                gasRefilled = command.gasRefilled,
                remarks = command.remarks,
                fieldBagBroughtInside = command.fieldBagBroughtInside,
                status = command.status,
                vehicleLogDocuments = command.vehicleLogDocuments,
                accompany = command.accompany,
            ).buildEvent(command),
        )
    }

    fun apply(event: VehicleLogAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        vehicleId = event.vehicleId
        date = event.date
        startingMileage = event.startingMileage
        endingMileage = event.endingMileage
        startGasRange = event.startGasRange
        endGasRange = event.endGasRange
        gasCost = event.gasCost
        jobCodeId = event.jobCodeId
        costCodeId = event.costCodeId
        keysHandover = event.keysHandover
        staffInitial = event.staffInitial
        insurancePresent = event.insurancePresent
        ezPassAvailable = event.ezPassAvailable
        tollCost = event.tollCost
        gasRefilled = event.gasRefilled
        remarks = event.remarks
        fieldBagBroughtInside = event.fieldBagBroughtInside
        status = event.status
        vehicleLogDocuments = event.vehicleLogDocuments
        accompany = event.accompany
    }

    fun updateStatusById(command: VehicleLogUpdateStatusByIdCommand) {
        if (!active) {
            throw VehicleLogStateChangeNotAllowedForInactiveStatusException(
                "Vehicle Log Update Status Exception!",
            )
        }
        raiseEvent(
            VehicleLogStatusUpdatedByIdEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: VehicleLogStatusUpdatedByIdEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }
}
