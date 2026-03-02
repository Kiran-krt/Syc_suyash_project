package com.syc.dashboard.query.notification.inapp.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum

class FindInAppNotificationCountStatusQuery(
    var userId: String = "",
    var statusList: List<InAppNotificationStatusEnum>,
) : TenantBaseQuery()
