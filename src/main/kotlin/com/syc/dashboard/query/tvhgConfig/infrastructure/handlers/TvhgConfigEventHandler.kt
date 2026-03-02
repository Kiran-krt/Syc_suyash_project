package com.syc.dashboard.query.tvhgConfig.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.tvhgConfig.dto.*
import com.syc.dashboard.query.tvhgConfig.entity.TvhgConfig
import com.syc.dashboard.query.tvhgConfig.repository.jpa.TvhgConfigRepository
import com.syc.dashboard.shared.tvhgconfig.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TvhgConfigEventHandler @Autowired constructor(
    private val tvhgConfigRepository: TvhgConfigRepository,
) : EventHandler {

    private fun on(event: TvhgConfigRegisteredEvent) {
        val tvhgConfig = TvhgConfig(
            id = event.id,
            createdBy = event.createdBy,
            createdOn = event.createdOn,
        ).buildEntity(event) as TvhgConfig
        tvhgConfigRepository.save(tvhgConfig)
    }

    private fun on(event: AddedUnitsInTvhgConfigEvent) {
        val unitsOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val unitsDto = UnitsDto(
            id = event.unitId,
            unitName = event.unitName,
            status = event.status,
            createdOn = event.createdOn,
        )

        unitsOptional.units.add(unitsDto)

        tvhgConfigRepository.save(unitsOptional)
    }

    private fun on(event: AddedDesignStormTvhgConfigEvent) {
        val designStormOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val designStormDto = DesignStormDto(
            id = event.designStormId,
            designStormName = event.designStormName,
            status = event.status,
            createdOn = event.createdOn,
        )

        designStormOptional.designStorm.add(designStormDto)

        tvhgConfigRepository.save(designStormOptional)
    }

    private fun on(event: AddedStructureTypeInTvhgConfigEvent) {
        val structureTypeOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        val structureTypeDto = StructureTypeDto(
            id = event.structureTypeId,
            typeId = event.typeId,
            structureTypeName = event.structureTypeName,
            status = event.status,
            createdOn = event.createdOn,
        )
        structureTypeOptional.structureType.add(structureTypeDto)

        tvhgConfigRepository.save(structureTypeOptional)
    }

    private fun on(event: AddedInletControlDataTvhgConfigEvent) {
        val inletControlDataOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        val inletControlDataDto = InletControlDataDto(
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
            createdOn = event.createdOn,
        )
        inletControlDataOptional.inletControlData.add(inletControlDataDto)

        tvhgConfigRepository.save(inletControlDataOptional)
    }

    private fun on(event: AddedOutletStructureTypeTvhgConfigEvent) {
        val outletStructureTypeOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val outletStructureTypeDto = OutletStructureTypeDto(
            id = event.outletStructureTypeId,
            outletStructureType = event.outletStructureType,
            status = event.status,
        )

        outletStructureTypeOptional.outletStructureType.add(outletStructureTypeDto)

        tvhgConfigRepository.save(outletStructureTypeOptional)
    }

    private fun on(event: AddedPipeMaterialTvhgConfigEvent) {
        val pipeMaterialOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val pipeMaterialDto = PipeMaterialDto(
            id = event.pipeMaterialId,
            pipeMaterialType = event.pipeMaterialType,
            typeId = event.typeId,
            status = event.status,
            createdOn = event.createdOn,
        )
        pipeMaterialOptional.pipeMaterialList.add(pipeMaterialDto)

        tvhgConfigRepository.save(pipeMaterialOptional)
    }

    private fun on(event: AddedPipeTypeTvhgConfigEvent) {
        val pipeTypeOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        val pipeTypeDto = PipeTypeDto(
            id = event.pipeTypeId,
            typeId = event.typeId,
            description = event.description,
            status = event.status,
            createdOn = event.createdOn,
        )
        pipeTypeOptional.pipeTypeList.add(pipeTypeDto)

        tvhgConfigRepository.save(pipeTypeOptional)
    }

    private fun on(event: DesignStormAllFieldsUpdatedEvent) {
        val designStormOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        designStormOptional.designStorm.find { it.id == event.designStormId }?.apply {
            designStormName = event.designStormName
            status = event.status
        }

        designStormOptional.designStorm.map {
            DesignStormDto(
                it.id,
                it.tenantId,
                it.designStormName,
                it.status,
            )
        }

        tvhgConfigRepository.save(designStormOptional)
    }

    private fun on(event: InletControlDataAllFieldsUpdatedEvent) {
        val inletControlDataOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        inletControlDataOptional.inletControlData.find { it.id == event.inletControlDataId }?.apply {
            inletId = event.inletId
            inletControlDataName = event.inletControlDataName
            cparameter = event.cparameter
            yparameter = event.yparameter
            kparameter = event.kparameter
            mparameter = event.mparameter
            equationForm = event.equationForm
            status = event.status
        }

        inletControlDataOptional.inletControlData.map {
            InletControlDataDto(
                it.id,
                it.tenantId,
                it.inletId,
                it.pathNumber,
                it.inletControlDataName,
                it.cparameter,
                it.yparameter,
                it.kparameter,
                it.mparameter,
                it.equationForm,
                it.status,
            )
        }

        tvhgConfigRepository.save(inletControlDataOptional)
    }

    private fun on(event: PipeMaterialAllFieldsUpdatedEvent) {
        val pipeMaterialOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        pipeMaterialOptional.pipeMaterialList.find { it.id == event.pipeMaterialId }?.apply {
            pipeMaterialType = event.pipeMaterialType
            typeId = event.typeId
            status = event.status
        }

        pipeMaterialOptional.pipeMaterialList.map {
            PipeMaterialDto(
                it.id,
                it.tenantId,
                it.pipeMaterialType,
                it.typeId,
                it.status,
            )
        }
        tvhgConfigRepository.save(pipeMaterialOptional)
    }

    private fun on(event: PipeTypeAllFieldsUpdatedEvent) {
        val pipeTypeOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        pipeTypeOptional.pipeTypeList.find { it.id == event.pipeTypeId }?.apply {
            typeId = event.typeId
            description = event.description
            status = event.status
        }

        pipeTypeOptional.pipeTypeList.map {
            PipeTypeDto(
                it.id,
                it.typeId,
                it.description,
                it.status,
            )
        }
        tvhgConfigRepository.save(pipeTypeOptional)
    }

    private fun on(event: StructureTypeAllFieldsUpdatedEvent) {
        val structureTypeOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        structureTypeOptional.structureType.find { it.id == event.structureTypeId }?.apply {
            typeId = event.typeId
            structureTypeName = event.structureTypeName
            status = event.status
        }

        structureTypeOptional.structureType.map {
            StructureTypeDto(
                it.id,
                it.typeId,
                it.structureTypeName,
                it.status,
            )
        }
        tvhgConfigRepository.save(structureTypeOptional)
    }

    private fun on(event: UnitsAllFieldsUpdatedEvent) {
        val unitsOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        unitsOptional.units.find { it.id == event.unitId }?.apply {
            unitName = event.unitName
            status = event.status
        }

        unitsOptional.units.map {
            UnitsDto(
                it.id,
                it.unitName,
                it.status,
            )
        }
        tvhgConfigRepository.save(unitsOptional)
    }

    private fun on(event: OutletStructureTypeAllFieldsUpdatedEvent) {
        val outletStructureTypeOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        outletStructureTypeOptional.outletStructureType.find { it.id == event.outletStructureTypeId }?.apply {
            outletStructureType = event.outletStructureType
            status = event.status
        }

        outletStructureTypeOptional.outletStructureType.map {
            OutletStructureTypeDto(
                it.id,
                it.outletStructureType,
                it.status,
            )
        }
        tvhgConfigRepository.save(outletStructureTypeOptional)
    }

    private fun on(event: AddedMdStandardNumberTvhgConfigEvent) {
        val mdStandardNumberOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        val mdStandardNumberListDto = MdStandardNumberListDto(
            id = event.mdStandardNumberId,
            structureClass = event.structureClass,
            mdStandardNumber = event.mdStandardNumber,
            type = event.type,
            status = event.status,

        )
        mdStandardNumberOptional.mdStandardNumberList.add(mdStandardNumberListDto)

        tvhgConfigRepository.save(mdStandardNumberOptional)
    }

    private fun on(event: MdStandardNumberAllFieldsUpdatedEvent) {
        val mdStandardNumberOptional = tvhgConfigRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        mdStandardNumberOptional.mdStandardNumberList.find { it.id == event.mdStandardNumberId }?.apply {
            structureClass = event.structureClass
            mdStandardNumber = event.mdStandardNumber
            type = event.type
            status = event.status
        }
        mdStandardNumberOptional.mdStandardNumberList.map {
            MdStandardNumberListDto(
                it.id,
                it.structureClass,
                it.mdStandardNumber,
                it.type,
                it.status,
            )
        }

        tvhgConfigRepository.save(mdStandardNumberOptional)
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is TvhgConfigRegisteredEvent -> on(event)
            is AddedUnitsInTvhgConfigEvent -> on(event)
            is AddedDesignStormTvhgConfigEvent -> on(event)
            is AddedStructureTypeInTvhgConfigEvent -> on(event)
            is AddedInletControlDataTvhgConfigEvent -> on(event)
            is AddedOutletStructureTypeTvhgConfigEvent -> on(event)
            is AddedPipeMaterialTvhgConfigEvent -> on(event)
            is AddedPipeTypeTvhgConfigEvent -> on(event)
            is DesignStormAllFieldsUpdatedEvent -> on(event)
            is InletControlDataAllFieldsUpdatedEvent -> on(event)
            is PipeMaterialAllFieldsUpdatedEvent -> on(event)
            is PipeTypeAllFieldsUpdatedEvent -> on(event)
            is StructureTypeAllFieldsUpdatedEvent -> on(event)
            is UnitsAllFieldsUpdatedEvent -> on(event)
            is OutletStructureTypeAllFieldsUpdatedEvent -> on(event)
            is AddedMdStandardNumberTvhgConfigEvent -> on(event)
            is MdStandardNumberAllFieldsUpdatedEvent -> on(event)
        }
    }
}
