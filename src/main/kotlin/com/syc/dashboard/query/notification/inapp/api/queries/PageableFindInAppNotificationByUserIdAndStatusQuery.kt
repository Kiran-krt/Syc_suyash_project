package com.syc.dashboard.query.notification.inapp.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import org.springframework.data.domain.Sort

class PageableFindInAppNotificationByUserIdAndStatusQuery(
    var userId: String = "",
    var status: List<InAppNotificationStatusEnum>? = null,
    val page: Int = 0,
    val limit: Int = 10,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
