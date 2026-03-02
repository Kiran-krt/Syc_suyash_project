package com.syc.dashboard.query.systemconfig.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.systemconfig.entity.enums.SystemConfigStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_system_config")
class SystemConfig(
    @Id
    val id: String = "",
    var appName: String = "",
    var logo: MutableList<DocumentIdDto> = mutableListOf(),
    var status: SystemConfigStatusEnum = SystemConfigStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseEntity()
