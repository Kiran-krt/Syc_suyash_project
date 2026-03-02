package com.syc.dashboard.query.document.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_document")
class Document(
    @Id
    val id: String,
    var objectId: String = "",
    var documentEntity: String = "",
    var documentObjectType: DocumentObjectTypeEnum = DocumentObjectTypeEnum.USER,
    val documentName: String = "",
    val extension: String = "",
    val cloudFilePath: String = "",
    var status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEntity()
