package com.syc.dashboard.query.document.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum
import java.util.*

class DocumentDto(
    var id: String = "",
    tenantId: String = "",
    var objectId: String = "",
    var documentEntity: String = "",
    var documentObjectType: DocumentObjectTypeEnum = DocumentObjectTypeEnum.USER,
    var documentName: String = "",
    var extension: String = "",
    var cloudFilePath: String = "",
    var status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
