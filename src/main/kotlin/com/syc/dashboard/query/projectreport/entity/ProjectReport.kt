package com.syc.dashboard.query.projectreport.entity

import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.dto.AppendixDto
import com.syc.dashboard.query.projectreport.dto.OutfallPhotoDocumentDto
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_project_report")
class ProjectReport(
    @Id
    val id: String,
    var reportType: String = "",
    var projectName: String = "",
    var projectLocation: String = "",
    var projectNumber: String = "",
    var chargeNumber: String = "",
    var permitType: String = "",
    var photoCoverPage: List<DocumentIdDto> = listOf(),
    var submissionPhase: String = "",
    var submissionDate: String = "",
    var preparedByCompanyName: String = "",
    var preparedByAddress: String = "",
    var preparedForCompanyName: String = "",
    var preparedForAddress: String = "",
    var preparedForLogo: List<DocumentIdDto> = listOf(),
    var preparedForSeal: List<DocumentIdDto> = listOf(),
    var peLicence: List<DocumentIdDto> = listOf(),
    var peLicenceNumber: String = "",
    var peExpirationDate: String = "",
    var status: ProjectReportStatusEnum = ProjectReportStatusEnum.IN_PROGRESS,
    var createdBy: String = "",
    var createdByInfo: BaseEntity? = null,
    val createdDate: Date = Date(),
    var outfallPhotoList: List<OutfallPhotoDocumentDto> = listOf(),
    var uploadedByInfo: AdminDto? = null,
    var appendixList: List<AppendixDto> = listOf(),
) : TenantBaseEntity()
