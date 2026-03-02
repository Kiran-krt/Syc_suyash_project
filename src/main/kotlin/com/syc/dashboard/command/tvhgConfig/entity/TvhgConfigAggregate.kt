package com.syc.dashboard.command.tvhgConfig.entity

import com.syc.dashboard.command.tvhgConfig.api.commands.*
import com.syc.dashboard.command.tvhgConfig.exception.TvhgConfigStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.tvhgConfig.dto.*
import com.syc.dashboard.shared.tvhgconfig.events.*
import java.util.*

class TvhgConfigAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var createdBy: String = ""
    var units: MutableList<UnitsDto> = mutableListOf()
    var designStorm: MutableList<DesignStormDto> = mutableListOf()
    var structureType: MutableList<StructureTypeDto> = mutableListOf()
    var inletControlData: MutableList<InletControlDataDto> = mutableListOf()
    var outletStructureType: OutletStructureTypeDto? = null
    var pipeTypeList: MutableList<PipeTypeDto> = mutableListOf()
    var pipeMaterialList: MutableList<PipeMaterialDto> = mutableListOf()
    var mdStandardNumberList: MutableList<MdStandardNumberListDto> = mutableListOf()

    constructor(command: RegisterTvhgConfigCommand) : this() {
        val createdOn = Date()
        raiseEvent(
            TvhgConfigRegisteredEvent(
                id = command.id,
                createdBy = command.createdBy,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TvhgConfigRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        createdBy = event.createdBy
    }

    fun addUnits(command: AddUnitsInTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Units cannot be added!",
            )
        }

        raiseEvent(
            AddedUnitsInTvhgConfigEvent(
                id = command.id,
                unitId = command.unitId,
                unitName = command.unitName,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedUnitsInTvhgConfigEvent) {
        buildAggregateRoot(event)
        units.add(
            UnitsDto(
                id = event.unitId,
                unitName = event.unitName,
                status = event.status,
            ),
        )
    }

    fun addDesignStorm(command: AddDesignStormTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Design storm cannot be added!",
            )
        }

        raiseEvent(
            AddedDesignStormTvhgConfigEvent(
                id = command.id,
                designStormId = command.designStormId,
                designStormName = command.designStormName,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedDesignStormTvhgConfigEvent) {
        buildAggregateRoot(event)
        designStorm.add(
            DesignStormDto(
                id = event.designStormId,
                designStormName = event.designStormName,
                status = event.status,
            ),
        )
    }

    fun addStructureType(command: AddStructureTypeInTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Structure type cannot be added!",
            )
        }
        raiseEvent(
            AddedStructureTypeInTvhgConfigEvent(
                id = command.id,
                structureTypeId = command.structureTypeId,
                typeId = command.typeId,
                structureTypeName = command.structureTypeName,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedStructureTypeInTvhgConfigEvent) {
        buildAggregateRoot(event)
        structureType.add(
            StructureTypeDto(
                id = event.structureTypeId,
                typeId = event.typeId,
                structureTypeName = event.structureTypeName,
                status = event.status,
            ),
        )
    }

    fun addInletControlData(command: AddInletControlDataTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Inlet control data cannot be added!",
            )
        }
        raiseEvent(
            AddedInletControlDataTvhgConfigEvent(
                id = command.id,
                inletControlDataId = command.inletControlDataId,
                inletId = command.inletId,
                pathNumber = command.pathNumber,
                inletControlDataName = command.inletControlDataName,
                cparameter = command.cparameter,
                yparameter = command.yparameter,
                kparameter = command.kparameter,
                mparameter = command.mparameter,
                equationForm = command.equationForm,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedInletControlDataTvhgConfigEvent) {
        buildAggregateRoot(event)
        inletControlData.add(
            InletControlDataDto(
                id = event.inletControlDataId,
                inletId = event.inletId,
                pathNumber = event.pathNumber,
                inletControlDataName = event.inletControlDataName,
                cparameter = event.cparameter,
                yparameter = event.yparameter,
                kparameter = event.kparameter,
                mparameter = event.mparameter,
                equationForm = event.equationForm,
                status = event.status,
            ),
        )
    }

    fun addOutletStructureType(command: AddOutletStructureTypeTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Tvhg config outlet structure type cannot be added!",
            )
        }

        raiseEvent(
            AddedOutletStructureTypeTvhgConfigEvent(
                id = command.id,
                outletStructureTypeId = command.outletStructureTypeId,
                outletStructureType = command.outletStructureType,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedOutletStructureTypeTvhgConfigEvent) {
        buildAggregateRoot(event)
        outletStructureType = OutletStructureTypeDto(
            id = event.outletStructureTypeId,
            outletStructureType = event.outletStructureType,
            status = event.status,
        )
    }

    fun addPipeMaterial(command: AddPipeMaterialTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Pipe material cannot be added!",
            )
        }

        raiseEvent(
            AddedPipeMaterialTvhgConfigEvent(
                id = command.id,
                pipeMaterialId = command.pipeMaterialId,
                pipeMaterialType = command.pipeMaterialType,
                typeId = command.typeId,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedPipeMaterialTvhgConfigEvent) {
        buildAggregateRoot(event)
        pipeMaterialList.add(
            PipeMaterialDto(
                id = event.pipeMaterialId,
                pipeMaterialType = event.pipeMaterialType,
                typeId = event.typeId,
                status = event.status,
            ),
        )
    }

    fun addPipeType(command: AddPipeTypeTvhgConfigCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Pipe type cannot be added!",
            )
        }
        raiseEvent(
            AddedPipeTypeTvhgConfigEvent(
                id = command.id,
                pipeTypeId = command.pipeTypeId,
                typeId = command.typeId,
                description = command.description,
                status = command.status,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedPipeTypeTvhgConfigEvent) {
        buildAggregateRoot(event)
        pipeTypeList.add(
            PipeTypeDto(
                id = event.pipeTypeId,
                typeId = event.typeId,
                description = event.description,
                status = event.status,
            ),
        )
    }

    fun updateDesignStormAllFields(command: UpdateDesignStormAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Design Storm Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            DesignStormAllFieldsUpdatedEvent(
                id = command.id,
                designStormId = command.designStormId,
                designStormName = command.designStormName,
                status = command.status,

            ).buildEvent(command),
        )
    }
    fun apply(event: DesignStormAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val designStormDto =
            designStorm.find { designStormDto -> designStormDto.id == event.designStormId }

        designStormDto?.designStormName = event.designStormName
        designStormDto?.status = event.status
    }

    fun updateInletControlDataAllFields(command: UpdateInletControlDataAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "InletControlData Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            InletControlDataAllFieldsUpdatedEvent(
                id = command.id,
                inletControlDataId = command.inletControlDataId,
                inletId = command.inletId,
                pathNumber = command.pathNumber,
                inletControlDataName = command.inletControlDataName,
                cparameter = command.cparameter,
                yparameter = command.yparameter,
                kparameter = command.kparameter,
                mparameter = command.mparameter,
                equationForm = command.equationForm,
                status = command.status,

            ).buildEvent(command),
        )
    }

    fun apply(event: InletControlDataAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val inletControlDataDto =
            inletControlData.find { inletControlDataDto -> inletControlDataDto.id == event.inletControlDataId }

        inletControlDataDto?.inletId = event.inletId
        inletControlDataDto?.pathNumber = event.pathNumber
        inletControlDataDto?.inletControlDataName = event.inletControlDataName
        inletControlDataDto?.cparameter = event.cparameter
        inletControlDataDto?.yparameter = event.yparameter
        inletControlDataDto?.kparameter = event.kparameter
        inletControlDataDto?.mparameter = event.mparameter
        inletControlDataDto?.equationForm = event.equationForm
        inletControlDataDto?.status = event.status
    }

    fun updatePipeMaterialAllFields(command: UpdatePipeMaterialAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "PipeMaterial All Fields Update Exception!",
            )
        }
        raiseEvent(
            PipeMaterialAllFieldsUpdatedEvent(
                id = command.id,
                pipeMaterialId = command.pipeMaterialId,
                pipeMaterialType = command.pipeMaterialType,
                typeId = command.typeId,
                status = command.status,

            ).buildEvent(command),
        )
    }

    fun apply(event: PipeMaterialAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val pipeMaterialDto =
            pipeMaterialList.find { pipeMaterialDto -> pipeMaterialDto.id == event.pipeMaterialId }
        pipeMaterialDto?.pipeMaterialType = event.pipeMaterialType
        pipeMaterialDto?.typeId = event.typeId
        pipeMaterialDto?.status = event.status
    }

    fun updatePipeTypeAllFields(command: UpdatePipeTypeAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "PipeType All Fields Update Exception!",
            )
        }
        raiseEvent(
            PipeTypeAllFieldsUpdatedEvent(
                id = command.id,
                pipeTypeId = command.pipeTypeId,
                typeId = command.typeId,
                description = command.description,
                status = command.status,

            ).buildEvent(command),
        )
    }

    fun apply(event: PipeTypeAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val pipeTypeDto =
            pipeTypeList.find { pipeTypeDto -> pipeTypeDto.id == event.pipeTypeId }
        pipeTypeDto?.typeId = event.typeId
        pipeTypeDto?.description = event.description
        pipeTypeDto?.status = event.status
    }

    fun updateStructureTypeAllFields(command: UpdateStructureTypeAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Structure Type All Fields Update Exception!",
            )
        }
        raiseEvent(
            StructureTypeAllFieldsUpdatedEvent(
                id = command.id,
                structureTypeId = command.structureTypeId,
                typeId = command.typeId,
                structureTypeName = command.structureTypeName,
                status = command.status,

            ).buildEvent(command),
        )
    }

    fun apply(event: StructureTypeAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val structureTypeDto =
            structureType.find { structureTypeDto -> structureTypeDto.id == event.structureTypeId }
        structureTypeDto?.typeId = event.typeId
        structureTypeDto?.structureTypeName = event.structureTypeName
        structureTypeDto?.status = event.status
    }

    fun updateUnitsFields(command: UpdateUnitsAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Units All Fields Update Exception!",
            )
        }
        raiseEvent(
            UnitsAllFieldsUpdatedEvent(
                id = command.id,
                unitId = command.unitId,
                unitName = command.unitName,
                status = command.status,

            ).buildEvent(command),
        )
    }

    fun apply(event: UnitsAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val unitsDto =
            units.find { unitsDto -> unitsDto.id == event.unitId }
        unitsDto?.unitName = event.unitName
        unitsDto?.status = event.status
    }

    fun updateOutletStructureTypeAllFields(command: UpdateOutletStructureTypeAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "OutletStructureType All Fields Update Exception!",
            )
        }
        raiseEvent(
            OutletStructureTypeAllFieldsUpdatedEvent(
                id = command.id,
                outletStructureTypeId = command.outletStructureTypeId,
                outletStructureType = command.outletStructureType,
                status = command.status,

            ).buildEvent(command),
        )
    }

    fun apply(event: OutletStructureTypeAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        outletStructureType = OutletStructureTypeDto(
            id = event.outletStructureTypeId,
            outletStructureType = event.outletStructureType,
            status = event.status,
        )
    }

    fun addMdStandardNumber(command: AddMdStandardNumberTvhgConfigCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "MD Standard number cannot be added!",
            )
        }
        raiseEvent(
            AddedMdStandardNumberTvhgConfigEvent(
                id = command.id,
                mdStandardNumberId = command.mdStandardNumberId,
                structureClass = command.structureClass,
                mdStandardNumber = command.mdStandardNumber,
                type = command.type,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedMdStandardNumberTvhgConfigEvent) {
        buildAggregateRoot(event)
        mdStandardNumberList.add(
            MdStandardNumberListDto(
                id = event.mdStandardNumberId,
                structureClass = event.structureClass,
                mdStandardNumber = event.mdStandardNumber,
                type = event.type,
                status = event.status,
            ),
        )
    }

    fun updateMdStandardNumberAllFields(command: UpdateMdStandardNumberAllFieldsCommand) {
        if (!active) {
            throw TvhgConfigStateChangeNotAllowedForInactiveStatusException(
                "Md Standard Number All Fields Update Exception!",
            )
        }
        raiseEvent(
            MdStandardNumberAllFieldsUpdatedEvent(
                id = command.id,
                mdStandardNumberId = command.mdStandardNumberId,
                structureClass = command.structureClass,
                mdStandardNumber = command.mdStandardNumber,
                type = command.type,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: MdStandardNumberAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val mdStandardNumberDto = mdStandardNumberList.find { mdStandardNumberDto -> mdStandardNumberDto.id == event.mdStandardNumberId }
        mdStandardNumberDto?.structureClass = event.structureClass
        mdStandardNumberDto?.mdStandardNumber = event.mdStandardNumber
        mdStandardNumberDto?.type = event.type
        mdStandardNumberDto?.status = event.status
    }
}
