package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum

class RegisterProjectReportCommand(
    id: String = "",
    val reportType: String = "",
    val projectName: String = "",
    val projectLocation: String = "",
    val projectNumber: String = "",
    val chargeNumber: String = "",
    val permitType: String = "",
    val photoCoverPage: List<DocumentIdDto> = listOf(),
    val submissionPhase: String = "",
    val submissionDate: String = "",
    val preparedByCompanyName: String = "",
    val preparedByAddress: String = "",
    val preparedForCompanyName: String = "",
    val preparedForAddress: String = "",
    val preparedForLogo: List<DocumentIdDto> = listOf(),
    val preparedForSeal: List<DocumentIdDto> = listOf(),
    val peLicence: List<DocumentIdDto> = listOf(),
    val peLicenceNumber: String = "",
    val peExpirationDate: String = "",
    val status: ProjectReportStatusEnum = ProjectReportStatusEnum.IN_PROGRESS,
    var createdBy: String = "",
) : TenantBaseCommand(id = id)
