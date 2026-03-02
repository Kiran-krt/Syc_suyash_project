package com.syc.dashboard.query.settings.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.settings.entity.enums.ExpenseTypeStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_settings_expense_type")
class ExpenseType(
    @Id
    val id: String,
    var settingsId: String = "",
    var expenseType: String = "",
    var expenseTypeDescription: String = "",
    var expenseTypeStatus: ExpenseTypeStatusEnum = ExpenseTypeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEntity()
