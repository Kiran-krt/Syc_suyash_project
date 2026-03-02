package com.syc.dashboard.query.vehiclelog.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.vehiclelog.entity.VehicleLog
import com.syc.dashboard.query.vehiclelog.repository.jpa.VehicleLogRepository
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogAllFieldsUpdatedEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogRegisteredEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogStatusUpdatedByIdEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VehicleLogEventHandler @Autowired constructor(
    private val vehicleLogRepository: VehicleLogRepository,
) : EventHandler {

    private fun on(event: VehicleLogRegisteredEvent) {
        val vehicleLog = VehicleLog(
            id = event.id,
            vehicleId = event.vehicleId,
            date = event.date,
            startingMileage = event.startingMileage,
            endingMileage = event.endingMileage,
            startGasRange = event.startGasRange,
            endGasRange = event.endGasRange,
            gasCost = event.gasCost,
            jobCodeId = event.jobCodeId,
            costCodeId = event.costCodeId,
            keysHandover = event.keysHandover,
            staffInitial = event.staffInitial,
            insurancePresent = event.insurancePresent,
            ezPassAvailable = event.ezPassAvailable,
            tollCost = event.tollCost,
            gasRefilled = event.gasRefilled,
            remarks = event.remarks,
            fieldBagBroughtInside = event.fieldBagBroughtInside,
            status = event.status,
            vehicleLogDocuments = event.vehicleLogDocuments,
            createdOn = event.createdOn,
            createdBy = event.createdBy,
            accompany = event.accompany,
        ).buildEntity(event) as VehicleLog
        vehicleLogRepository.save(vehicleLog)
    }

    private fun on(event: VehicleLogAllFieldsUpdatedEvent) {
        val vehicleLogOptional = vehicleLogRepository.findById(event.id)
        if (vehicleLogOptional.isEmpty) {
            return
        }
        vehicleLogOptional.get().vehicleId = event.vehicleId
        vehicleLogOptional.get().date = event.date
        vehicleLogOptional.get().startingMileage = event.startingMileage
        vehicleLogOptional.get().endingMileage = event.endingMileage
        vehicleLogOptional.get().startGasRange = event.startGasRange
        vehicleLogOptional.get().endGasRange = event.endGasRange
        vehicleLogOptional.get().gasCost = event.gasCost
        vehicleLogOptional.get().jobCodeId = event.jobCodeId
        vehicleLogOptional.get().costCodeId = event.costCodeId
        vehicleLogOptional.get().keysHandover = event.keysHandover
        vehicleLogOptional.get().staffInitial = event.staffInitial
        vehicleLogOptional.get().insurancePresent = event.insurancePresent
        vehicleLogOptional.get().ezPassAvailable = event.ezPassAvailable
        vehicleLogOptional.get().tollCost = event.tollCost
        vehicleLogOptional.get().gasRefilled = event.gasRefilled
        vehicleLogOptional.get().remarks = event.remarks
        vehicleLogOptional.get().fieldBagBroughtInside = event.fieldBagBroughtInside
        vehicleLogOptional.get().status = event.status
        vehicleLogOptional.get().vehicleLogDocuments = event.vehicleLogDocuments
        vehicleLogOptional.get().accompany = event.accompany
        vehicleLogRepository.save(vehicleLogOptional.get())
    }

    private fun on(event: VehicleLogStatusUpdatedByIdEvent) {
        val vehicleLogOptional = vehicleLogRepository.findById(event.id)
        if (vehicleLogOptional.isEmpty) {
            return
        }
        vehicleLogOptional.get().status = event.status
        vehicleLogRepository.save(vehicleLogOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is VehicleLogRegisteredEvent -> on(event)
            is VehicleLogAllFieldsUpdatedEvent -> on(event)
            is VehicleLogStatusUpdatedByIdEvent -> on(event)
        }
    }
}
