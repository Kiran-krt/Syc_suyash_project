package com.syc.dashboard.query.projectreport.infrastructure.handlers

import com.syc.dashboard.framework.common.utils.ReflectionUtils
import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.projectreport.dto.AppendixDto
import com.syc.dashboard.query.projectreport.dto.OutfallPhotoDocumentDto
import com.syc.dashboard.query.projectreport.entity.ProjectReport
import com.syc.dashboard.query.projectreport.repository.jpa.ProjectReportRepository
import com.syc.dashboard.shared.projectreport.events.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectReportEventHandler @Autowired constructor(
    private val projectReportRepository: ProjectReportRepository,
) : EventHandler {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun on(event: ProjectReportRegisteredEvent) {
        val projectReport = ProjectReport(
            id = event.id,
            reportType = event.reportType,
            projectName = event.projectName,
            projectLocation = event.projectLocation,
            projectNumber = event.projectNumber,
            chargeNumber = event.chargeNumber,
            permitType = event.permitType,
            photoCoverPage = event.photoCoverPage,
            submissionPhase = event.submissionPhase,
            submissionDate = event.submissionDate,
            preparedByCompanyName = event.preparedByCompanyName,
            preparedByAddress = event.preparedByAddress,
            preparedForCompanyName = event.preparedForCompanyName,
            preparedForAddress = event.preparedForAddress,
            preparedForLogo = event.preparedForLogo,
            preparedForSeal = event.preparedForSeal,
            peLicence = event.peLicence,
            peLicenceNumber = event.peLicenceNumber,
            peExpirationDate = event.peExpirationDate,
            status = event.status,
            createdBy = event.createdBy,
            createdDate = event.createdDate,
        ).buildEntity(event) as ProjectReport
        projectReportRepository.save(projectReport)
    }

    private fun on(event: ProjectReportStatusUpdatedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.id)
        if (projectReportOptional.isEmpty) {
            return
        }
        projectReportOptional.get().status = event.status
        projectReportRepository.save(projectReportOptional.get())
    }

    private fun on(event: ProjectReportFieldUpdatedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.id)
        if (projectReportOptional.isEmpty) {
            return
        }
        val projectReport = projectReportOptional.get()
        try {
            ReflectionUtils.setFieldValue(projectReport, event.fieldName, event.fieldValue)
            projectReportRepository.save(projectReport)
            log.debug("Field '${event.fieldName}' updated to '${event.fieldValue}'")
        } catch (e: Exception) {
            log.warn("Failed to update field '${event.fieldName}': ${e.message} '${event.id}:${event.id}'")
        }
    }

    private fun on(event: OutfallPhotoUploadEvent) {
        val projectReportOptional = projectReportRepository.findById(event.projectReportId)
        if (projectReportOptional.isEmpty) {
            return
        }
        val projectReport = projectReportOptional.get()
        val outfallPhotoDocumentDto = OutfallPhotoDocumentDto(
            id = event.id,
            projectReportId = event.projectReportId,
            document = event.document,
            status = event.status,
            caption = event.caption,
            order = event.order,
            uploadedBy = event.uploadedBy,
        )
        projectReport.outfallPhotoList += outfallPhotoDocumentDto
        projectReportRepository.save(projectReport)
    }

    private fun on(event: OutfallPhotoStatusUpdatedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.id)
        if (projectReportOptional.isEmpty) {
            return
        }

        val projectReport = projectReportOptional.get()
        val outfallPhotoOptional = projectReport.outfallPhotoList.firstOrNull { it.id == event.outfallPhotoId }

        if (outfallPhotoOptional != null) {
            outfallPhotoOptional.status = event.status
            projectReportRepository.save(projectReport)
        }
    }

    private fun on(event: OutfallPhotoAllFieldsUpdatedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.id)
        if (projectReportOptional.isEmpty) {
            return
        }
        val projectReport = projectReportOptional.get()
        val existingOutfallPhoto = projectReport.outfallPhotoList.find { it.id == event.outfallPhotoId }
        if (existingOutfallPhoto != null) {
            existingOutfallPhoto.document = event.document
            existingOutfallPhoto.status = event.status
            existingOutfallPhoto.caption = event.caption
            existingOutfallPhoto.order = event.order
        }

        projectReportRepository.save(projectReport)
    }

    private fun on(event: AppendixAddedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.projectReportId)
        if (projectReportOptional.isEmpty) {
            return
        }
        val projectReport = projectReportOptional.get()
        val appendixDto = AppendixDto(
            tenantId = event.tenantId,
            id = event.id,
            projectReportId = event.projectReportId,
            name = event.name,
            order = event.order,
            status = event.status,
            createdBy = event.createdBy,
            createdDate = event.createdDate,
        )
        projectReport.appendixList += appendixDto
        projectReportRepository.save(projectReport)
    }

    private fun on(event: AppendixAllFieldsUpdatedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.id)
        if (projectReportOptional.isEmpty) {
            return
        }
        val projectReport = projectReportOptional.get()
        val existingAppendix = projectReport.appendixList.find { it.id == event.appendixId }
        if (existingAppendix != null) {
            existingAppendix.name = event.name
            existingAppendix.order = event.order
            existingAppendix.status = event.status
            existingAppendix.content = event.content
            existingAppendix.order = event.order
        }

        projectReportRepository.save(projectReport)
    }

    private fun on(event: AppendixStatusUpdatedEvent) {
        val projectReportOptional = projectReportRepository.findById(event.id)
        if (projectReportOptional.isEmpty) {
            return
        }

        projectReportOptional.get().appendixList.forEach { appendix ->
            if (appendix.id == event.appendixId) {
                appendix.status = event.status
                projectReportRepository.save(projectReportOptional.get())
            }
        }
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is ProjectReportRegisteredEvent -> on(event)
            is ProjectReportStatusUpdatedEvent -> on(event)
            is ProjectReportFieldUpdatedEvent -> on(event)
            is OutfallPhotoUploadEvent -> on(event)
            is OutfallPhotoStatusUpdatedEvent -> on(event)
            is OutfallPhotoAllFieldsUpdatedEvent -> on(event)
            is AppendixAddedEvent -> on(event)
            is AppendixAllFieldsUpdatedEvent -> on(event)
            is AppendixStatusUpdatedEvent -> on(event)
        }
    }
}
