package com.syc.dashboard.command.document.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum
import java.util.*

class DocumentUploadCommand(
    id: String = "",
    var objectId: String = "",
    val documentEntity: String = "",
    val documentObjectType: DocumentObjectTypeEnum = DocumentObjectTypeEnum.USER,
    var documentName: String = "",
    var extension: String = "",
    var cloudFilePath: String = "",
    val status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseCommand(id = id)
