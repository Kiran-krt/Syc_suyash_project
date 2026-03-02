package com.syc.dashboard.query.notification.mobile.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import org.springframework.data.domain.Sort

class PageableFindMobileNotificationByUserIdAndStatusQuery(
    var userId: String = "",
    var status: List<MobileNotificationStatusEnum>? = null,
    val page: Int = 0,
    val limit: Int = 10,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
