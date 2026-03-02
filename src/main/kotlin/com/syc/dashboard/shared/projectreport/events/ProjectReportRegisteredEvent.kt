package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import java.util.*

class ProjectReportRegisteredEvent(
    id: String,
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
    val createdDate: Date,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ProjectReportRegisteredEvent"
    }
}
