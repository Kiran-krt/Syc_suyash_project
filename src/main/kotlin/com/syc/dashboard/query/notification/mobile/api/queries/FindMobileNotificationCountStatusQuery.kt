package com.syc.dashboard.query.notification.mobile.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum

class FindMobileNotificationCountStatusQuery(
    var userId: String = "",
    var statusList: List<MobileNotificationStatusEnum>,
) : TenantBaseQuery()
