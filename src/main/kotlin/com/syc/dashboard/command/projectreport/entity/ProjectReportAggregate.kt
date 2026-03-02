package com.syc.dashboard.command.projectreport.entity

import com.syc.dashboard.command.projectreport.api.commands.*
import com.syc.dashboard.command.projectreport.exceptions.ProjectReportEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.projectreport.exceptions.ProjectReportStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.utils.ReflectionUtils
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.dto.AppendixDto
import com.syc.dashboard.query.projectreport.dto.OutfallPhotoDocumentDto
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import com.syc.dashboard.shared.projectreport.events.*
import java.util.*

class ProjectReportAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var reportType: String = ""
    var projectName: String = ""
    var projectLocation: String = ""
    var projectNumber: String = ""
    var chargeNumber: String = ""
    var permitType: String = ""
    var photoCoverPage: List<DocumentIdDto> = listOf()
    var submissionPhase: String = ""
    var submissionDate: String = ""
    var preparedByCompanyName: String = ""
    var preparedByAddress: String = ""
    var preparedForCompanyName: String = ""
    var preparedForAddress: String = ""
    var preparedForLogo: List<DocumentIdDto> = listOf()
    var preparedForSeal: List<DocumentIdDto> = listOf()
    var peLicence: List<DocumentIdDto> = listOf()
    var peLicenceNumber: String = ""
    var peExpirationDate: String = ""
    var createdBy: String = ""
    var status: ProjectReportStatusEnum = ProjectReportStatusEnum.IN_PROGRESS
    var fieldName: String = ""
    var fieldValue: Any? = null
    var outfallPhotoList: MutableList<OutfallPhotoDocumentDto> = mutableListOf()
    var projectReportId: String = ""
    var documentId: String = ""
    var outfallPhotoStatus: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE
    var outfallPhotoId: String = ""
    var appendixList: MutableList<AppendixDto> = mutableListOf()

    constructor(command: RegisterProjectReportCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            ProjectReportRegisteredEvent(
                id = command.id,
                reportType = command.reportType,
                projectName = command.projectName,
                projectLocation = command.projectLocation,
                projectNumber = command.projectNumber,
                chargeNumber = command.chargeNumber,
                permitType = command.permitType,
                photoCoverPage = command.photoCoverPage,
                submissionPhase = command.submissionPhase,
                submissionDate = command.submissionDate,
                preparedByCompanyName = command.preparedByCompanyName,
                preparedByAddress = command.preparedByAddress,
                preparedForCompanyName = command.preparedForCompanyName,
                preparedForAddress = command.preparedForAddress,
                preparedForLogo = command.preparedForLogo,
                preparedForSeal = command.preparedForSeal,
                peLicence = command.peLicence,
                peLicenceNumber = command.peLicenceNumber,
                peExpirationDate = command.peExpirationDate,
                status = command.status,
                createdDate = createdDate,
                createdBy = command.createdBy,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectReportRegisteredEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        reportType = event.reportType
        projectName = event.projectName
        projectLocation = event.projectLocation
        projectNumber = event.projectNumber
        chargeNumber = event.chargeNumber
        permitType = event.permitType
        photoCoverPage = event.photoCoverPage
        submissionPhase = event.submissionPhase
        submissionDate = event.submissionDate
        preparedByCompanyName = event.preparedByCompanyName
        preparedByAddress = event.preparedByAddress
        preparedForCompanyName = event.preparedForCompanyName
        preparedForAddress = event.preparedForAddress
        preparedForLogo = event.preparedForLogo
        preparedForSeal = event.preparedForSeal
        peLicence = event.peLicence
        peLicenceNumber = event.peLicenceNumber
        peExpirationDate = event.peExpirationDate
        status = event.status
        createdBy = event.createdBy
    }

    fun updateStatus(command: ProjectReportUpdateStatusCommand) {
        if (!active) {
            throw ProjectReportStateChangeNotAllowedForInactiveStatusException(
                "Project report Update Status Exception!",
            )
        }
        raiseEvent(
            ProjectReportStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectReportStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }

    fun updateField(command: ProjectReportUpdateFieldCommand) {
        if (!active) {
            throw ProjectReportEventStreamNotExistInEventStoreException(
                "Project field updated exception!",
            )
        }
        raiseEvent(
            ProjectReportFieldUpdatedEvent(
                id = command.id,
                fieldName = command.fieldName,
                fieldValue = command.fieldValue,
            ).buildEvent(command),
        )
    }

    fun apply(event: ProjectReportFieldUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        ReflectionUtils.setFieldValue(this, event.fieldName, event.fieldValue)
    }

    fun outfallPhotoUpload(command: OutfallPhotoUploadCommand) {
        if (!active) {
            throw ProjectReportStateChangeNotAllowedForInactiveStatusException(
                "Outfall photo upload Exception!",
            )
        }
        raiseEvent(
            OutfallPhotoUploadEvent(
                id = command.outfallPhotoId,
                projectReportId = command.id,
                document = command.document,
                status = command.status,
                caption = command.caption,
                order = command.order,
                uploadedBy = command.uploadedBy,
            ).buildEvent(command),
        )
    }

    fun apply(event: OutfallPhotoUploadEvent) {
        buildAggregateRoot(event)
        active = true
        val photo = OutfallPhotoDocumentDto(
            id = event.id,
            projectReportId = event.projectReportId,
            document = event.document,
            status = event.status,
            caption = event.caption,
            order = event.order,
            uploadedBy = event.uploadedBy,
        )
        outfallPhotoList.add(photo)
    }

    fun updateOutfallPhotoStatus(command: OutfallPhotoUpdateStatusCommand) {
        if (!active) {
            throw ProjectReportStateChangeNotAllowedForInactiveStatusException(
                "OutfallPhoto Update Status Exception!",
            )
        }
        raiseEvent(
            OutfallPhotoStatusUpdatedEvent(
                id = command.id,
                outfallPhotoId = command.outfallPhotoId,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: OutfallPhotoStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        outfallPhotoId = event.outfallPhotoId
        outfallPhotoStatus = event.status
    }

    fun updateOutfallPhotoAllFields(command: OutfallPhotoUpdateAllFieldsCommand) {
        if (!active) {
            throw ProjectReportEventStreamNotExistInEventStoreException(
                "Outfall Photo all fields updated exception!",
            )
        }
        raiseEvent(
            OutfallPhotoAllFieldsUpdatedEvent(
                id = command.id,
                outfallPhotoId = command.outfallPhotoId,
                document = command.document,
                status = command.status,
                caption = command.caption,
                order = command.order,
            ).buildEvent(command),
        )
    }

    fun apply(event: OutfallPhotoAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        active = true
        val photo = OutfallPhotoDocumentDto(
            id = event.id,
            document = event.document,
            status = event.status,
            caption = event.caption,
            order = event.order,
        )
    }

    fun addAppendix(command: AddAppendixCommand) {
        val createdDate = Date()
        if (!active) {
            throw ProjectReportStateChangeNotAllowedForInactiveStatusException(
                "Appendix add Exception!",
            )
        }
        raiseEvent(
            AppendixAddedEvent(
                id = command.appendixId,
                projectReportId = command.id,
                name = command.name,
                order = command.order,
                status = command.status,
                createdBy = command.createdBy,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: AppendixAddedEvent) {
        buildAggregateRoot(event)
        active = true
        val appendix = AppendixDto(
            id = event.id,
            projectReportId = event.projectReportId,
            name = event.name,
            order = event.order,
            status = event.status,
            createdBy = event.createdBy,
            createdDate = event.createdDate,
        )
        appendixList.add(appendix)
    }

    fun updateAppendixAllFields(command: AppendixUpdateAllFieldsCommand) {
        if (!active) {
            throw ProjectReportEventStreamNotExistInEventStoreException(
                "Appendix all fields updated exception!",
            )
        }
        raiseEvent(
            AppendixAllFieldsUpdatedEvent(
                id = command.id,
                appendixId = command.appendixId,
                name = command.name,
                order = command.order,
                status = command.status,
                content = command.content,
            ).buildEvent(command),
        )
    }

    fun apply(event: AppendixAllFieldsUpdatedEvent) {
        buildAggregateRoot(event)
        active = true
        AppendixDto(
            id = event.id,
            name = event.name,
            order = event.order,
            status = event.status,
            content = event.content,
        )
    }

    fun updateAppendixStatus(command: AppendixUpdateStatusCommand) {
        if (!active) {
            throw ProjectReportStateChangeNotAllowedForInactiveStatusException(
                "Appendix Update Status Exception!",
            )
        }
        raiseEvent(
            AppendixStatusUpdatedEvent(
                id = command.id,
                appendixId = command.appendixId,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: AppendixStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        AppendixDto(
            id = event.appendixId,
            status = event.status,
        )
    }
}
