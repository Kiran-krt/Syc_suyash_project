package com.syc.dashboard.query.notification.email.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import org.springframework.data.domain.Sort

class PageableFindEmailNotificationByUserIdAndStatusQuery(
    var userId: String = "",
    var status: List<EmailNotificationStatusEnum>? = null,
    val page: Int = 0,
    val limit: Int = 10,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
