package com.syc.dashboard.query.projectreport.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import java.util.*

class ProjectReportDto(
    var id: String = "",
    tenantId: String = "",
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
    val createdDate: Date = Date(),
    var createdByInfo: EmployeeDto? = null,
) : TenantBaseDto(tenantId = tenantId)
