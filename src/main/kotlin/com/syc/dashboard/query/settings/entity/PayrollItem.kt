package com.syc.dashboard.query.settings.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.settings.entity.enums.PayrollItemStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "q_settings_payroll_item")
class PayrollItem(
    @Id
    val id: String,
    var settingsId: String = "",
    var payrollItem: String = "",
    var payrollItemDescription: String = "",
    var payrollItemStatus: PayrollItemStatusEnum = PayrollItemStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEntity()
