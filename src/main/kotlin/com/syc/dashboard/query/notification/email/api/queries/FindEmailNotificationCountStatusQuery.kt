package com.syc.dashboard.query.notification.email.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum

class FindEmailNotificationCountStatusQuery(
    var userId: String = "",
    var statusList: List<EmailNotificationStatusEnum>,
) : TenantBaseQuery()
