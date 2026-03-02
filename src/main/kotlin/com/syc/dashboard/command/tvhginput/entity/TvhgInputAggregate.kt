package com.syc.dashboard.command.tvhginput.entity

import com.syc.dashboard.command.tvhginput.api.commands.*
import com.syc.dashboard.command.tvhginput.exception.TvhgInputEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.tvhginput.exception.TvhgInputStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.utils.ReflectionUtils
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.tvhginput.dto.*
import com.syc.dashboard.query.tvhginput.dto.InletControlParameterDto
import com.syc.dashboard.query.tvhginput.dto.PipeDrawingInformationDto
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum
import com.syc.dashboard.shared.tvhginput.events.*
import com.syc.dashboard.shared.tvhginput.events.AddedInletControlParameterEvent
import java.util.*

class TvhgInputAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var name: String = ""
    var description: String = ""
    var status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE
    var projectTitle: String = ""
    var createdBy: String = ""
    var createdOn: Date = Date()
    private var projectInformation: ProjectInformationDto? = null
    private var structureList: MutableList<StructureInformationDto> = mutableListOf()
    private var hydrologicInformation: HydrologicInformationDto? = null
    private var pipeList: MutableList<PipeInformationDto> = mutableListOf()
    private var structureDrawingDataList: MutableList<StructureDrawingDataDto> = mutableListOf()
    private var inletControlParameterList: MutableList<InletControlParameterDto> = mutableListOf()
    private var outletDrawingInformation: OutletDrawingInformationDto? = null
    private var pipeDrawingInformationList: MutableList<PipeDrawingInformationDto> = mutableListOf()
    private var flowPathDrawingInformation: MutableList<FlowPathDrawingDtoInformationDto> = mutableListOf()
    private var distanceElevationData: MutableList<DistanceElevationDataDto> = mutableListOf()

    constructor(command: RegisterTvhgInputCommand) : this() {
        raiseEvent(
            TvhgInputRegisteredEvent(
                id = command.id,
                name = command.name,
                description = command.description,
                status = command.status,
                projectTitle = command.projectTitle,
                createdBy = command.createdBy,
                createdOn = command.createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: TvhgInputRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        name = event.name
        description = event.description
        status = event.status
        projectTitle = event.projectTitle
        createdBy = event.createdBy
        createdOn = event.createdOn
    }

    fun updateStatus(command: TvhgInputUpdateStatusCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg Input Update Status Exception!",
            )
        }
        raiseEvent(
            TvhgInputStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: TvhgInputStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateAllFields(command: TvhgInputUpdateAllFieldsCommand) {
        if (!active) {
            throw TvhgInputEventStreamNotExistInEventStoreException(
                "Tvhg input all fields updated exception!",
            )
        }
        raiseEvent(
            TvhgInputAllFieldsUpdatedEvent(
                id = command.id,
                name = command.name,
                status = command.status,
                description = command.description,
                projectTitle = command.projectTitle,
                createdBy = command.createdBy,
            ).buildEvent(command),
        )
    }

    fun apply(event: TvhgInputAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        name = event.name
        status = event.status
        description = event.description
        projectTitle = event.projectTitle
        createdBy = event.createdBy
    }

    fun addProjectInformation(command: AddProjectInformationInTvhgInputCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg input cannot be added!",
            )
        }

        raiseEvent(
            AddedProjectInformationInTvhgInputEvent(
                id = command.id,
                title = command.title,
                numberOfStructures = command.numberOfStructures,
                numberOfFlowPaths = command.numberOfFlowPaths,
                tailwaterElevationOutlet = command.tailwaterElevationOutlet,
                hydrologicInformation = command.hydrologicInformation,
                drawingInformation = command.drawingInformation,
                choiceOfUnitsId = command.choiceOfUnitsId,
                status = command.status,
                createdBy = command.createdBy,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedProjectInformationInTvhgInputEvent) {
        buildAggregateRoot(event)
        projectInformation = ProjectInformationDto(
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
    }

    fun addStructureInformation(command: AddStructureInformationCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Structure cannot be added!",
            )
        }

        raiseEvent(
            StructureInformationAddedEvent(
                id = command.id,
                structureInformationId = command.structureInformationId,
                structureId = command.structureId,
                structureNumber = command.structureNumber,
                structureTypeId = command.structureTypeId,
                overflowElevation = command.overflowElevation,
                contributionArea = command.contributionArea,
                runoffCoefficient = command.runoffCoefficient,
                timeOfConcentration = command.timeOfConcentration,
                status = command.status,
                createdBy = command.createdBy,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: StructureInformationAddedEvent) {
        buildAggregateRoot(event)
        structureList.add(
            StructureInformationDto(
                id = event.structureInformationId,
                tenantId = event.tenantId,
                structureId = event.structureId,
                structureNumber = event.structureNumber,
                structureTypeId = event.structureTypeId,
                overflowElevation = event.overflowElevation,
                contributionArea = event.contributionArea,
                runoffCoefficient = event.runoffCoefficient,
                timeOfConcentration = event.timeOfConcentration,
                status = event.status,
                createdBy = event.createdBy,
                createdOn = event.createdOn,
            ),
        )
    }

    fun updateStructureInformationAllFields(command: UpdateStructureInformationAllFieldCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Structure Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            StructureInformationAllFieldUpdatedEvent(
                id = command.id,
                structureInformationId = command.structureInformationId,
                structureId = command.structureId,
                structureNumber = command.structureNumber,
                structureTypeId = command.structureTypeId,
                overflowElevation = command.overflowElevation,
                contributionArea = command.contributionArea,
                runoffCoefficient = command.runoffCoefficient,
                timeOfConcentration = command.timeOfConcentration,
                status = command.status,
                createdBy = command.createdBy,
            ).buildEvent(command),
        )
    }

    fun apply(event: StructureInformationAllFieldUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val structureInformationDto =
            structureList.find { structureInformationDto -> structureInformationDto.id == event.structureInformationId }

        structureInformationDto?.structureNumber = event.structureNumber
        structureInformationDto?.structureId = event.structureId
        structureInformationDto?.structureTypeId = event.structureTypeId
        structureInformationDto?.overflowElevation = event.overflowElevation
        structureInformationDto?.contributionArea = event.contributionArea
        structureInformationDto?.runoffCoefficient = event.runoffCoefficient
        structureInformationDto?.timeOfConcentration = event.timeOfConcentration
        structureInformationDto?.status = event.status
        structureInformationDto?.createdBy = event.createdBy
    }

    fun updateProjectInformationAllFields(command: UpdateProjectInformationAllFieldCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Project Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            ProjectInformationAllFieldUpdatedEvent(
                id = command.id,
                fieldName = command.fieldName,
                fieldValue = command.fieldValue,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectInformationAllFieldUpdatedEvent) {
        buildAggregateRoot(event)
        active = true

        if (projectInformation == null) {
            projectInformation = ProjectInformationDto()
            log.info(" Project Information Updated for id: ${event.id}.")
        }

        try {
            ReflectionUtils.setFieldValue(projectInformation!!, event.fieldName, event.fieldValue)
        } catch (e: Exception) {
            log.warn("Failed to update project information field '${event.fieldName}': ${e.message}")
        }
    }

    fun addHydrologicInformation(command: AddHydrologicInformationCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg input  hydrologic information cannot be added!",
            )
        }

        raiseEvent(
            HydrologicInformationAddedEvent(
                id = command.id,
                designStormId = command.designStormId,
                zeroToTenMinuteDuration = command.zeroToTenMinuteDuration,
                tenToFourtyMinuteDuration = command.tenToFourtyMinuteDuration,
                fourtyToOneHundredFiftyMinuteDuration = command.fourtyToOneHundredFiftyMinuteDuration,
                createdBy = command.createdBy,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: HydrologicInformationAddedEvent) {
        buildAggregateRoot(event)
        hydrologicInformation = HydrologicInformationDto(
            designStormId = event.designStormId,
            zeroToTenMinuteDuration = event.zeroToTenMinuteDuration,
            tenToFourtyMinuteDuration = event.tenToFourtyMinuteDuration,
            fourtyToOneHundredFiftyMinuteDuration = event.fourtyToOneHundredFiftyMinuteDuration,
            createdBy = event.createdBy,
        )
    }

    fun updateHydrologicInformationAllFields(command: UpdateHydrologicInformationAllFieldCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Hydrologic Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            HydrologicInformationAllFieldUpdatedEvent(
                id = command.id,
                fieldName = command.fieldName,
                fieldValue = command.fieldValue,
            ).buildEvent(command),
        )
    }

    fun apply(event: HydrologicInformationAllFieldUpdatedEvent) {
        buildAggregateRoot(event)
        active = true

        if (hydrologicInformation == null) {
            hydrologicInformation = HydrologicInformationDto()
            log.info(" Hydrologic Information Updated for id: ${event.id}.")
        }

        try {
            ReflectionUtils.setFieldValue(hydrologicInformation!!, event.fieldName, event.fieldValue)
        } catch (e: Exception) {
            log.warn("Failed to update hydrologic information field '${event.fieldName}': ${e.message}")
        }
    }

    fun addPipeInformation(command: AddPipeInformationCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg input pipe information cannot be added!",
            )
        }

        raiseEvent(
            AddedPipeInformationTvhgInputEvent(
                id = command.id,
                pipeInformationId = command.pipeInformationId,
                pipeId = command.pipeId,
                pipeNumber = command.pipeNumber,
                downstreamStructureNumber = command.downstreamStructureNumber,
                upstreamStructureNumber = command.upstreamStructureNumber,
                downstreamInvertElevation = command.downstreamInvertElevation,
                upstreamInvertElevation = command.upstreamInvertElevation,
                pipeTypeId = command.pipeTypeId,
                roughnessCoefficient = command.roughnessCoefficient,
                pipeLength = command.pipeLength,
                intersectionAngle = command.intersectionAngle,
                discharge = command.discharge,
                status = command.status,
                createdBy = command.createdBy,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedPipeInformationTvhgInputEvent) {
        buildAggregateRoot(event)
        pipeList.add(
            PipeInformationDto(
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
            ),
        )
    }

    fun addStructureDrawingData(command: AddStructureDrawingDataCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg input structure drawing data cannot be added!",
            )
        }

        raiseEvent(
            StructureDrawingDataAddedEvent(
                id = command.id,
                structureDrawingDataId = command.structureDrawingDataId,
                structureInformationId = command.structureInformationId,
                existingOrProposedIndex = command.existingOrProposedIndex,
                mdshaStandardNumber = command.mdshaStandardNumber,
                typeOfStructure = command.typeOfStructure,
                structureClass = command.structureClass,
                station = command.station,
                offset = command.offset,
                createdBy = command.createdBy,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: StructureDrawingDataAddedEvent) {
        buildAggregateRoot(event)
        structureDrawingDataList.add(
            StructureDrawingDataDto(
                existingOrProposedIndex = event.existingOrProposedIndex,
                structureInformationId = event.structureInformationId,
                mdshaStandardNumber = event.mdshaStandardNumber,
                typeOfStructure = event.typeOfStructure,
                structureClass = event.structureClass,
                station = event.station,
                offset = event.offset,
                createdBy = event.createdBy,
            ),
        )
    }

    fun updatePipeInformationAllFields(command: UpdatePipeInformationAllFieldCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Pipe Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            PipeInformationAllFieldUpdatedEvent(
                id = command.id,
                pipeInformationId = command.pipeInformationId,
                pipeId = command.pipeId,
                pipeNumber = command.pipeNumber,
                downstreamStructureNumber = command.downstreamStructureNumber,
                upstreamStructureNumber = command.upstreamStructureNumber,
                downstreamInvertElevation = command.downstreamInvertElevation,
                upstreamInvertElevation = command.upstreamInvertElevation,
                pipeTypeId = command.pipeTypeId,
                roughnessCoefficient = command.roughnessCoefficient,
                pipeLength = command.pipeLength,
                intersectionAngle = command.intersectionAngle,
                discharge = command.discharge,
                status = command.status,
                createdBy = command.createdBy,

            ).buildEvent(command),
        )
    }

    fun apply(event: PipeInformationAllFieldUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val pipeInformationDto =
            pipeList.find { pipeInformationDto -> pipeInformationDto.id == event.pipeInformationId }

        pipeInformationDto?.pipeNumber = event.pipeNumber
        pipeInformationDto?.pipeId = event.pipeId
        pipeInformationDto?.downstreamStructureNumber = event.downstreamStructureNumber
        pipeInformationDto?.upstreamStructureNumber = event.upstreamStructureNumber
        pipeInformationDto?.downstreamInvertElevation = event.downstreamInvertElevation
        pipeInformationDto?.upstreamInvertElevation = event.upstreamInvertElevation
        pipeInformationDto?.pipeTypeId = event.pipeTypeId
        pipeInformationDto?.roughnessCoefficient = event.roughnessCoefficient
        pipeInformationDto?.pipeLength = event.pipeLength
        pipeInformationDto?.intersectionAngle = event.intersectionAngle
        pipeInformationDto?.discharge = event.discharge
        pipeInformationDto?.status = event.status
        pipeInformationDto?.createdBy = event.createdBy
    }

    fun updateStructureDrawingDataAllFields(command: UpdateStructureDrawingDataAllFieldsCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Structure Drawing Data All Fields Update Exception!",
            )
        }
        raiseEvent(
            StructureDrawingDataAllFieldsUpdatedEvent(
                id = command.id,
                structureDrawingDataId = command.structureDrawingDataId,
                structureInformationId = command.structureInformationId,
                existingOrProposedIndex = command.existingOrProposedIndex,
                mdshaStandardNumber = command.mdshaStandardNumber,
                typeOfStructure = command.typeOfStructure,
                structureClass = command.structureClass,
                station = command.station,
                offset = command.offset,
                createdBy = command.createdBy,
            ).buildEvent(command),
        )
    }

    fun apply(event: StructureDrawingDataAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val structureDrawingDataDto =
            structureDrawingDataList.find { structureDrawingDataDto -> structureDrawingDataDto.id == event.structureDrawingDataId }

        structureDrawingDataDto?.existingOrProposedIndex = event.existingOrProposedIndex
        structureDrawingDataDto?.structureInformationId = event.structureInformationId
        structureDrawingDataDto?.mdshaStandardNumber = event.mdshaStandardNumber
        structureDrawingDataDto?.typeOfStructure = event.typeOfStructure
        structureDrawingDataDto?.structureClass = event.structureClass
        structureDrawingDataDto?.station = event.station
        structureDrawingDataDto?.offset = event.offset
        structureDrawingDataDto?.createdBy = event.createdBy
    }

    fun addInletControlParameter(command: AddInletControlParameterCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Inlet Control Parameter cannot be added!",
            )
        }

        raiseEvent(
            AddedInletControlParameterEvent(
                id = command.id,
                inletControlParameterId = command.inletControlParameterId,
                cparameter = command.cparameter,
                yparameter = command.yparameter,
                kparameter = command.kparameter,
                mparameter = command.mparameter,
                equationForm = command.equationForm,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedInletControlParameterEvent) {
        buildAggregateRoot(event)
        inletControlParameterList.add(
            InletControlParameterDto(
                id = event.inletControlParameterId,
                cparameter = event.cparameter,
                yparameter = event.yparameter,
                kparameter = event.kparameter,
                mparameter = event.mparameter,
                equationForm = event.equationForm,
            ),
        )
    }

    fun addOutletDrawingInformation(command: AddOutletDrawingInformationCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg input outlet drawing information cannot be added!",
            )
        }

        val distanceElevationDataList = command.distanceElevationData.map {
            DistanceElevationDataDto(
                id = UUID.randomUUID().toString(),
                distanceFromOutlet = it.distanceFromOutlet,
                elevation = it.elevation,
            )
        }

        raiseEvent(
            AddedOutletDrawingInformationEvent(
                id = command.id,
                outletStructureTypeId = command.outletStructureTypeId,
                lengthOfRipRap = command.lengthOfRipRap,
                classOfRipRap = command.classOfRipRap,
                distanceElevationData = distanceElevationDataList.toMutableList(),
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedOutletDrawingInformationEvent) {
        buildAggregateRoot(event)
        active = true

        outletDrawingInformation = OutletDrawingInformationDto(
            outletStructureTypeId = event.outletStructureTypeId,
            lengthOfRipRap = event.lengthOfRipRap,
            classOfRipRap = event.classOfRipRap,
            distanceElevationData = event.distanceElevationData,
        )
    }

    fun addPipeDrawingInformation(command: AddPipeDrawingInformationCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Pipe drawing information cannot be added!",
            )
        }

        raiseEvent(
            AddedPipeDrawingInformationEvent(
                id = command.id,
                pipeDrawingInformationId = command.pipeDrawingInformationId,
                pipeInformationId = command.pipeInformationId,
                pipeMaterialId = command.pipeMaterialId,
                distanceBetweenStructures = command.distanceBetweenStructures,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedPipeDrawingInformationEvent) {
        buildAggregateRoot(event)
        pipeDrawingInformationList.add(
            PipeDrawingInformationDto(
                id = event.pipeDrawingInformationId,
                pipeInformationId = event.pipeInformationId,
                pipeMaterialId = event.pipeMaterialId,
                distanceBetweenStructures = event.distanceBetweenStructures,
            ),
        )
    }

    fun updateFlowPathDrawingInformation(command: UpdateFlowPathDrawingInformationAllFieldsCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Tvhg input flow path drawing cannot be added!",
            )
        }

        raiseEvent(
            FlowPathDrawingInformationAllFieldsUpdatedEvent(
                id = command.id,
                flowPathDrawingInformationId = command.flowPathDrawingInformationId,
                inletControlDataId = command.inletControlDataId,
                pathTitle = command.pathTitle,
            ).buildEvent(command),
        )
    }

    fun apply(event: FlowPathDrawingInformationAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        active = true

        val flowPathDrawingInformationDto =
            flowPathDrawingInformation.find { flowPathDrawingInformationDto -> flowPathDrawingInformationDto.id == event.flowPathDrawingInformationId }

        flowPathDrawingInformationDto?.inletControlDataId = event.inletControlDataId
        flowPathDrawingInformationDto?.pathTitle = event.pathTitle
    }

    fun updatePipeDrawingInformationAllFields(command: UpdatePipeDrawingInformationAllFieldsCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Pipe Drawing Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            PipeDrawingInformationAllFieldsUpdatedEvent(
                id = command.id,
                pipeDrawingInformationId = command.pipeDrawingInformationId,
                pipeInformationId = command.pipeInformationId,
                pipeMaterialId = command.pipeMaterialId,
                distanceBetweenStructures = command.distanceBetweenStructures,
            ).buildEvent(command),
        )
    }

    fun apply(event: PipeDrawingInformationAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val pipeDrawingInformationDto =
            pipeDrawingInformationList.find { pipeDrawingInformationDto -> pipeDrawingInformationDto.id == event.pipeDrawingInformationId }

        pipeDrawingInformationDto?.pipeMaterialId = event.pipeMaterialId
        pipeDrawingInformationDto?.pipeInformationId = event.pipeInformationId
        pipeDrawingInformationDto?.distanceBetweenStructures = event.distanceBetweenStructures
    }

    fun updateInletControlParameterAllFields(command: UpdateInletControlParameterAllFieldsCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Inlet Control Parameter All Fields Update Exception!",
            )
        }
        raiseEvent(
            InletControlParameterAllFieldsUpdatedEvent(
                id = command.id,
                inletControlParameterId = command.inletControlParameterId,
                boundaryConditions = command.boundaryConditions,
                cparameter = command.cparameter,
                yparameter = command.yparameter,
                kparameter = command.kparameter,
                mparameter = command.mparameter,
                equationForm = command.equationForm,
            ).buildEvent(command),
        )
    }

    fun apply(event: InletControlParameterAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val inletControlParameterListDto =
            inletControlParameterList.find { inletControlParameterListDto -> inletControlParameterListDto.id == event.inletControlParameterId }

        inletControlParameterListDto?.boundaryConditions = event.boundaryConditions
        inletControlParameterListDto?.cparameter = event.cparameter
        inletControlParameterListDto?.kparameter = event.kparameter
        inletControlParameterListDto?.yparameter = event.yparameter
        inletControlParameterListDto?.mparameter = event.mparameter
        inletControlParameterListDto?.equationForm = event.equationForm
    }

    fun updateOutletDrawingInformationAllField(command: UpdateOutletDrawingInformationAllFieldCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Outlet Drawing Information All Fields Update Exception!",
            )
        }
        raiseEvent(
            OutletDrawingInformationAllFieldUpdatedEvent(
                id = command.id,
                fieldName = command.fieldName,
                fieldValue = command.fieldValue,
            ).buildEvent(command),
        )
    }

    fun apply(event: OutletDrawingInformationAllFieldUpdatedEvent) {
        buildAggregateRoot(event)
        active = true

        if (outletDrawingInformation == null) {
            outletDrawingInformation = OutletDrawingInformationDto()
            log.info(" Outlet Drawing Information Updated for id: ${event.id}.")
        }

        try {
            ReflectionUtils.setFieldValue(outletDrawingInformation!!, event.fieldName, event.fieldValue)
        } catch (e: Exception) {
            log.warn("Failed to update outlet drawing information field '${event.fieldName}': ${e.message}")
        }
    }

    fun addFlowPathDrawingInformation(command: AddFlowPathDrawingInformationCommand) {
        val createdOn = Date()
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Flow path drawing information cannot be added!",
            )
        }

        raiseEvent(
            AddedFlowPathDrawingInformationEvent(
                id = command.id,
                flowPathDrawingInformationId = command.flowPathDrawingInformationId,
                inletControlDataId = command.inletControlDataId,
                pathTitle = command.pathTitle,
                createdOn = createdOn,
            ).buildEvent(command),
        )
    }

    fun apply(event: AddedFlowPathDrawingInformationEvent) {
        buildAggregateRoot(event)
        flowPathDrawingInformation.add(
            FlowPathDrawingDtoInformationDto(
                id = event.flowPathDrawingInformationId,
                tenantId = event.tenantId,
                inletControlDataId = event.inletControlDataId,
                pathTitle = event.pathTitle,
                createdOn = event.createdOn,
            ),
        )
    }

    fun updateDistanceElevationData(command: UpdateOutletDrawingInformationElevationDataAllFieldsCommand) {
        if (!active) {
            throw TvhgInputStateChangeNotAllowedForInactiveStatusException(
                "Distance Elevation data All Fields Update Exception!",
            )
        }
        raiseEvent(
            OutletDrawingInformationElevationDataAllFieldsUpdatedEvent(
                id = command.id,
                distanceElevationId = command.distanceElevationId,
                distanceFromOutlet = command.distanceFromOutlet,
                elevation = command.elevation,
            ).buildEvent(command),
        )
    }
    fun apply(event: OutletDrawingInformationElevationDataAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id

        val distanceElevationDataDto = outletDrawingInformation?.distanceElevationData?.find { it.id == event.distanceElevationId }

        distanceElevationDataDto?.distanceFromOutlet = event.distanceFromOutlet
        distanceElevationDataDto?.elevation = event.elevation
    }
}
