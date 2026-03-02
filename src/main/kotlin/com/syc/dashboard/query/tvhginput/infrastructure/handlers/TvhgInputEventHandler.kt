package com.syc.dashboard.query.tvhginput.infrastructure.handlers

import com.syc.dashboard.framework.common.utils.ReflectionUtils
import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.tvhginput.dto.*
import com.syc.dashboard.query.tvhginput.dto.InletControlParameterDto
import com.syc.dashboard.query.tvhginput.entity.TvhgInput
import com.syc.dashboard.query.tvhginput.repository.jpa.TvhgInputRepository
import com.syc.dashboard.shared.tvhginput.events.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TvhgInputEventHandler @Autowired constructor(
    private val tvhgInputRepository: TvhgInputRepository,
) : EventHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun on(event: TvhgInputRegisteredEvent) {
        val tvhgInput = TvhgInput(
            id = event.id,
            name = event.name,
            description = event.description,
            status = event.status,
            projectTitle = event.projectTitle,
            createdBy = event.createdBy,
            createdOn = event.createdOn,
        ).buildEntity(event) as TvhgInput
        tvhgInputRepository.save(tvhgInput)
    }

    private fun on(event: TvhgInputStatusUpdatedEvent) {
        val tvhgInputOptional = tvhgInputRepository.findById(event.id)
        if (tvhgInputOptional.isEmpty) {
            return
        }
        tvhgInputOptional.get().status = event.status
        tvhgInputRepository.save(tvhgInputOptional.get())
    }

    private fun on(event: TvhgInputAllFieldsUpdatedEvent) {
        val tvhgInputOptional = tvhgInputRepository.findById(event.id)
        if (tvhgInputOptional.isEmpty) {
            return
        }
        tvhgInputOptional.get().name = event.name
        tvhgInputOptional.get().description = event.description
        tvhgInputOptional.get().status = event.status
        tvhgInputOptional.get().projectTitle = event.projectTitle
        tvhgInputOptional.get().createdBy = event.createdBy
        tvhgInputRepository.save(tvhgInputOptional.get())
    }

    private fun on(event: AddedProjectInformationInTvhgInputEvent) {
        val projectInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val projectInformationDto = ProjectInformationDto(
            title = event.title,
            numberOfStructures = event.numberOfStructures,
            numberOfFlowPaths = event.numberOfFlowPaths,
            tailwaterElevationOutlet = event.tailwaterElevationOutlet,
            hydrologicInformation = event.hydrologicInformation,
            drawingInformation = event.drawingInformation,
            choiceOfUnitsId = event.choiceOfUnitsId,
            createdBy = event.createdBy,
            status = event.status,
        )
        projectInformationOptional.projectInformation = projectInformationDto

        tvhgInputRepository.save(projectInformationOptional)
    }

    private fun on(event: StructureInformationAddedEvent) {
        val structureOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val structureInformationDto = StructureInformationDto(
            id = event.structureInformationId,
            structureId = event.structureId,
            structureNumber = event.structureNumber,
            structureTypeId = event.structureTypeId,
            overflowElevation = event.overflowElevation,
            contributionArea = event.contributionArea,
            runoffCoefficient = event.runoffCoefficient,
            timeOfConcentration = event.timeOfConcentration,
            status = event.status,
        )
        structureOptional.structureList.add(structureInformationDto)

        tvhgInputRepository.save(structureOptional)
    }

    private fun on(event: StructureInformationAllFieldUpdatedEvent) {
        val structureInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        structureInformationOptional.structureList.find { it.id == event.structureInformationId }?.apply {
            structureNumber = event.structureNumber
            structureId = event.structureId
            structureTypeId = event.structureTypeId
            overflowElevation = event.overflowElevation
            contributionArea = event.contributionArea
            runoffCoefficient = event.runoffCoefficient
            timeOfConcentration = event.timeOfConcentration
            status = event.status
            createdBy = event.createdBy
        }

        structureInformationOptional.structureList.map {
            StructureInformationDto(
                it.id,
                it.tenantId,
                it.structureId,
                it.structureNumber,
                it.structureTypeId,
                it.overflowElevation,
                it.contributionArea,
                it.runoffCoefficient,
                it.timeOfConcentration,
                it.status,
                it.createdBy,
            )
        }

        tvhgInputRepository.save(structureInformationOptional)
    }

    private fun on(event: ProjectInformationAllFieldUpdatedEvent) {
        val projectInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        var projectInformation = projectInformationOptional.projectInformation

        if (projectInformation == null) {
            projectInformation = ProjectInformationDto()
            projectInformationOptional.projectInformation = projectInformation
        }
        if (event.fieldName == "numberOfStructures") {
            val newNumberOfStructures = event.fieldValue.toString().toIntOrNull() ?: 0
            val existingStructures = projectInformationOptional.structureList
            val existingPipes = projectInformationOptional.pipeList
            val existingStructureDrawings = projectInformationOptional.structureDrawingDataList
            val existingPipeDrawing = projectInformationOptional.pipeDrawingInformationList

            val existingStructureCount = existingStructures.size
            val existingPipeCount = existingPipes.size
            val existingStructureDrawingDataCount = existingStructureDrawings.size
            val existingPipeDrawingCount = existingPipeDrawing.size

            if (newNumberOfStructures > existingStructureCount) {
                val additionalStructures = List(newNumberOfStructures - existingStructureCount) {
                    StructureInformationDto(id = UUID.randomUUID().toString())
                }
                projectInformationOptional.structureList.addAll(additionalStructures)

                val additionalPipes = List(newNumberOfStructures - 1 - existingPipeCount) {
                    PipeInformationDto(id = UUID.randomUUID().toString())
                }
                projectInformationOptional.pipeList = (existingPipes + additionalPipes).toMutableList()

                if (newNumberOfStructures > existingStructureDrawingDataCount) {
                    val additionalStructureDrawings = List(newNumberOfStructures - existingStructureDrawingDataCount) {
                        StructureDrawingDataDto(id = UUID.randomUUID().toString())
                    }
                    projectInformationOptional.structureDrawingDataList.addAll(additionalStructureDrawings)

                    val additionalExistingPipeDrawings = List(newNumberOfStructures - 1 - existingPipeDrawingCount) {
                        PipeDrawingInformationDto(id = UUID.randomUUID().toString())
                    }
                    projectInformationOptional.pipeDrawingInformationList =
                        (existingPipeDrawing + additionalExistingPipeDrawings).toMutableList()
                }
            } else if (newNumberOfStructures < existingStructureCount || newNumberOfStructures < existingStructureDrawingDataCount) {
                projectInformationOptional.structureList = existingStructures.take(newNumberOfStructures).toMutableList()
                projectInformationOptional.pipeList = existingPipes.take(newNumberOfStructures - 1).toMutableList()
                projectInformationOptional.structureDrawingDataList = existingStructureDrawings.take(newNumberOfStructures).toMutableList()
                projectInformationOptional.pipeDrawingInformationList = existingPipeDrawing.take(newNumberOfStructures - 1).toMutableList()
            }
        }
        // Updating inlet control data based on numberOfFlowPaths

        if (event.fieldName == "numberOfFlowPaths") {
            val newNumberOfFlowPaths = event.fieldValue.toString().toIntOrNull() ?: 0

            val existingNumberOfFlowPaths = projectInformationOptional.inletControlParameterList

            val existingNumberOfFlowPathsCount = existingNumberOfFlowPaths.size

            if (newNumberOfFlowPaths > existingNumberOfFlowPathsCount) {
                val additionalNumberOfFlowPaths = List(newNumberOfFlowPaths - existingNumberOfFlowPathsCount) {
                    InletControlParameterDto(id = UUID.randomUUID().toString())
                }
                projectInformationOptional.inletControlParameterList.addAll(additionalNumberOfFlowPaths)
            } else if (newNumberOfFlowPaths < existingNumberOfFlowPathsCount) {
                projectInformationOptional.inletControlParameterList =
                    existingNumberOfFlowPaths.take(newNumberOfFlowPaths).toMutableList()
            }
        }

        try {
            ReflectionUtils.setFieldValue(
                projectInformationOptional.projectInformation!!,
                event.fieldName,
                event.fieldValue,
            )
            tvhgInputRepository.save(projectInformationOptional)
            log.debug("Field '${event.fieldName}' in project information updated to '${event.fieldValue}' for id: ${event.id}")
        } catch (e: Exception) {
            log.warn("Failed to update project information field '${event.fieldName}': ${e.message} for id: ${event.id}")
        }
    }

    private fun on(event: HydrologicInformationAddedEvent) {
        val hydrologicInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val hydrologicInformationDto = HydrologicInformationDto(
            designStormId = event.designStormId,
            zeroToTenMinuteDuration = event.zeroToTenMinuteDuration,
            tenToFourtyMinuteDuration = event.tenToFourtyMinuteDuration,
            fourtyToOneHundredFiftyMinuteDuration = event.fourtyToOneHundredFiftyMinuteDuration,
            createdBy = event.createdBy,
        )
        hydrologicInformationOptional.hydrologicInformation = hydrologicInformationDto

        tvhgInputRepository.save(hydrologicInformationOptional)
    }

    private fun on(event: HydrologicInformationAllFieldUpdatedEvent) {
        val hydrologicInformationOptional = tvhgInputRepository.findByTenantIdAndId(
            tenantId = event.tenantId,
            id = event.id,
        )
        var hydrologicInformation = hydrologicInformationOptional.hydrologicInformation

        if (hydrologicInformation == null) {
            hydrologicInformation = HydrologicInformationDto()
            hydrologicInformationOptional.hydrologicInformation = hydrologicInformation
        }

        try {
            ReflectionUtils.setFieldValue(hydrologicInformation, event.fieldName, event.fieldValue)

            tvhgInputRepository.save(hydrologicInformationOptional)

            log.debug("Field '${event.fieldName}' in hydrologic information updated to '${event.fieldValue}' for id: ${event.id}")
        } catch (e: Exception) {
            log.warn("Failed to update hydrologic information field '${event.fieldName}' with value '${event.fieldValue}': ${e.message} for id: ${event.id}")
        }
    }

    private fun on(event: AddedPipeInformationTvhgInputEvent) {
        val pipeInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val pipeInformationDto = PipeInformationDto(
            id = event.pipeInformationId,
            pipeId = event.pipeId,
            pipeNumber = event.pipeNumber,
            downstreamStructureNumber = event.downstreamStructureNumber,
            upstreamStructureNumber = event.upstreamStructureNumber,
            downstreamInvertElevation = event.downstreamInvertElevation,
            upstreamInvertElevation = event.upstreamInvertElevation,
            pipeTypeId = event.pipeTypeId,
            roughnessCoefficient = event.roughnessCoefficient,
            pipeLength = event.pipeLength,
            intersectionAngle = event.intersectionAngle,
            discharge = event.discharge,
            status = event.status,
            createdBy = event.createdBy,
        )
        pipeInformationOptional.pipeList.add(pipeInformationDto)

        tvhgInputRepository.save(pipeInformationOptional)
    }

    private fun on(event: StructureDrawingDataAddedEvent) {
        val structureDrawingDataOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val structureDrawingDataDto = StructureDrawingDataDto(
            id = event.structureDrawingDataId,
            structureInformationId = event.structureInformationId,
            existingOrProposedIndex = event.existingOrProposedIndex,
            mdshaStandardNumber = event.mdshaStandardNumber,
            typeOfStructure = event.typeOfStructure,
            structureClass = event.structureClass,
            station = event.station,
            offset = event.offset,
            createdBy = event.createdBy,
        )
        structureDrawingDataOptional.structureDrawingDataList.add(structureDrawingDataDto)

        tvhgInputRepository.save(structureDrawingDataOptional)
    }

    private fun on(event: PipeInformationAllFieldUpdatedEvent) {
        val pipeInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        pipeInformationOptional.pipeList.find { it.id == event.pipeInformationId }?.apply {
            pipeNumber = event.pipeNumber
            pipeId = event.pipeId
            downstreamStructureNumber = event.downstreamStructureNumber
            upstreamStructureNumber = event.upstreamStructureNumber
            downstreamInvertElevation = event.downstreamInvertElevation
            upstreamInvertElevation = event.upstreamInvertElevation
            pipeTypeId = event.pipeTypeId
            roughnessCoefficient = event.roughnessCoefficient
            pipeLength = event.pipeLength
            intersectionAngle = event.intersectionAngle
            discharge = event.discharge
            status = event.status
            createdBy = event.createdBy
        }

        pipeInformationOptional.pipeList.map {
            PipeInformationDto(
                it.id,
                it.pipeNumber,
                it.pipeId,
                it.downstreamStructureNumber,
                it.upstreamStructureNumber,
                it.downstreamInvertElevation,
                it.upstreamInvertElevation,
                it.pipeTypeId,
                it.roughnessCoefficient,
                it.pipeLength,
                it.intersectionAngle,
                it.discharge,
                it.status,
                it.createdBy,
            )
        }

        tvhgInputRepository.save(pipeInformationOptional)
    }

    private fun on(event: StructureDrawingDataAllFieldsUpdatedEvent) {
        val structureDrawingDataOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        structureDrawingDataOptional.structureDrawingDataList.find { it.id == event.structureDrawingDataId }?.apply {
            existingOrProposedIndex = event.existingOrProposedIndex
            structureInformationId = event.structureInformationId
            mdshaStandardNumber = event.mdshaStandardNumber
            typeOfStructure = event.typeOfStructure
            structureClass = event.structureClass
            station = event.station
            offset = event.offset
            createdBy = event.createdBy
        }

        structureDrawingDataOptional.structureDrawingDataList.map {
            StructureDrawingDataDto(
                it.id,
                it.tenantId,
                it.structureInformationId,
                it.existingOrProposedIndex,
                it.mdshaStandardNumber,
                it.typeOfStructure,
                it.structureClass,
                it.station,
                it.offset,
                it.createdBy,
            )
        }

        tvhgInputRepository.save(structureDrawingDataOptional)
    }

    private fun on(event: AddedInletControlParameterEvent) {
        val inletControlParameterOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        val inletControlParameterDto = InletControlParameterDto(
            id = event.inletControlParameterId,
            cparameter = event.cparameter,
            yparameter = event.yparameter,
            kparameter = event.kparameter,
            mparameter = event.mparameter,
            equationForm = event.equationForm,
            createdOn = event.createdOn,
        )
        inletControlParameterOptional.inletControlParameterList.add(inletControlParameterDto)

        tvhgInputRepository.save(inletControlParameterOptional)
    }

    private fun on(event: AddedOutletDrawingInformationEvent) {
        val outletDrawingInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val outletDrawingInformation = outletDrawingInformationOptional.outletDrawingInformation

        if (outletDrawingInformation == null) {
            val outletDrawingInformationDto = OutletDrawingInformationDto(
                outletStructureTypeId = event.outletStructureTypeId,
                lengthOfRipRap = event.lengthOfRipRap,
                classOfRipRap = event.classOfRipRap,
                distanceElevationData = event.distanceElevationData,
            )
            outletDrawingInformationOptional.outletDrawingInformation = outletDrawingInformationDto
        }
        outletDrawingInformation?.distanceElevationData?.addAll(event.distanceElevationData)

        tvhgInputRepository.save(outletDrawingInformationOptional)
    }

    private fun on(event: AddedPipeDrawingInformationEvent) {
        val pipeDrawingInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val pipeDrawingInformationDto = PipeDrawingInformationDto(
            id = event.pipeDrawingInformationId,
            pipeInformationId = event.pipeInformationId,
            pipeMaterialId = event.pipeMaterialId,
            distanceBetweenStructures = event.distanceBetweenStructures,
            createdOn = event.createdOn,
        )
        pipeDrawingInformationOptional.pipeDrawingInformationList.add(pipeDrawingInformationDto)

        tvhgInputRepository.save(pipeDrawingInformationOptional)
    }

    private fun on(event: FlowPathDrawingInformationAllFieldsUpdatedEvent) {
        val flowPathDrawingInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        flowPathDrawingInformationOptional.flowPathDrawingInformation.find { it.id == event.flowPathDrawingInformationId }?.apply {
            inletControlDataId = event.inletControlDataId
            pathTitle = event.pathTitle
        }

        flowPathDrawingInformationOptional.flowPathDrawingInformation.map {
            FlowPathDrawingDtoInformationDto(
                it.id,
                it.inletControlDataId,
                it.pathTitle,
            )
        }
        tvhgInputRepository.save(flowPathDrawingInformationOptional)
    }

    private fun on(event: PipeDrawingInformationAllFieldsUpdatedEvent) {
        val pipeDrawingInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        pipeDrawingInformationOptional.pipeDrawingInformationList.find { it.id == event.pipeDrawingInformationId }?.apply {
            pipeMaterialId = event.pipeMaterialId
            pipeInformationId = event.pipeInformationId
            distanceBetweenStructures = event.distanceBetweenStructures
        }

        pipeDrawingInformationOptional.pipeDrawingInformationList.map {
            PipeDrawingInformationDto(
                it.id,
                it.pipeMaterialId,
                it.pipeInformationId,
                it.distanceBetweenStructures,
            )
        }

        tvhgInputRepository.save(pipeDrawingInformationOptional)
    }

    private fun on(event: InletControlParameterAllFieldsUpdatedEvent) {
        val inletControlParameterOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )
        inletControlParameterOptional.inletControlParameterList.find { it.id == event.inletControlParameterId }?.apply {
            boundaryConditions = event.boundaryConditions
            cparameter = event.cparameter
            yparameter = event.yparameter
            kparameter = event.kparameter
            mparameter = event.mparameter
            equationForm = event.equationForm
        }

        inletControlParameterOptional.inletControlParameterList.map {
            InletControlParameterDto(
                it.id,
                it.boundaryConditions,
                it.cparameter,
                it.yparameter,
                it.kparameter,
                it.mparameter,
                it.equationForm,
            )
        }

        tvhgInputRepository.save(inletControlParameterOptional)
    }

    private fun on(event: OutletDrawingInformationAllFieldUpdatedEvent) {
        val outletDrawingInformationOptional = tvhgInputRepository.findByTenantIdAndId(
            tenantId = event.tenantId,
            id = event.id,
        )
        var outletDrawingInformation = outletDrawingInformationOptional.outletDrawingInformation

        if (outletDrawingInformation == null) {
            outletDrawingInformation = OutletDrawingInformationDto()
            outletDrawingInformationOptional.outletDrawingInformation = outletDrawingInformation
        }

        try {
            ReflectionUtils.setFieldValue(outletDrawingInformation, event.fieldName, event.fieldValue)

            tvhgInputRepository.save(outletDrawingInformationOptional)

            log.debug("Field '${event.fieldName}' in outlet drawing information updated to '${event.fieldValue}' for id: ${event.id}")
        } catch (e: Exception) {
            log.warn("Failed to update outlet drawing information field '${event.fieldName}' with value '${event.fieldValue}': ${e.message} for id: ${event.id}")
        }
    }

    private fun on(event: AddedFlowPathDrawingInformationEvent) {
        val flowPathDrawingInformationOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        val flowPathDrawingInformationDto = FlowPathDrawingDtoInformationDto(
            id = event.flowPathDrawingInformationId,
            inletControlDataId = event.inletControlDataId,
            pathTitle = event.pathTitle,
        )
        flowPathDrawingInformationOptional.flowPathDrawingInformation.add(flowPathDrawingInformationDto)

        tvhgInputRepository.save(flowPathDrawingInformationOptional)
    }

    private fun on(event: OutletDrawingInformationElevationDataAllFieldsUpdatedEvent) {
        val distanceElevationDataOptional = tvhgInputRepository
            .findByTenantIdAndId(
                tenantId = event.tenantId,
                id = event.id,
            )

        distanceElevationDataOptional.outletDrawingInformation?.distanceElevationData?.find { it.id == event.distanceElevationId }?.apply {
            distanceFromOutlet = event.distanceFromOutlet
            elevation = event.elevation
        }

        tvhgInputRepository.save(distanceElevationDataOptional)
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is TvhgInputRegisteredEvent -> on(event)
            is TvhgInputStatusUpdatedEvent -> on(event)
            is TvhgInputAllFieldsUpdatedEvent -> on(event)
            is AddedProjectInformationInTvhgInputEvent -> on(event)
            is StructureInformationAddedEvent -> on(event)
            is StructureInformationAllFieldUpdatedEvent -> on(event)
            is ProjectInformationAllFieldUpdatedEvent -> on(event)
            is HydrologicInformationAddedEvent -> on(event)
            is HydrologicInformationAllFieldUpdatedEvent -> on(event)
            is AddedPipeInformationTvhgInputEvent -> on(event)
            is StructureDrawingDataAddedEvent -> on(event)
            is PipeInformationAllFieldUpdatedEvent -> on(event)
            is StructureDrawingDataAllFieldsUpdatedEvent -> on(event)
            is AddedInletControlParameterEvent -> on(event)
            is AddedOutletDrawingInformationEvent -> on(event)
            is AddedPipeDrawingInformationEvent -> on(event)
            is FlowPathDrawingInformationAllFieldsUpdatedEvent -> on(event)
            is PipeDrawingInformationAllFieldsUpdatedEvent -> on(event)
            is InletControlParameterAllFieldsUpdatedEvent -> on(event)
            is OutletDrawingInformationAllFieldUpdatedEvent -> on(event)
            is AddedFlowPathDrawingInformationEvent -> on(event)
            is OutletDrawingInformationElevationDataAllFieldsUpdatedEvent -> on(event)
        }
    }
}
