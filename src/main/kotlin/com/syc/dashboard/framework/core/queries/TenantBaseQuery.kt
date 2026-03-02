package com.syc.dashboard.framework.core.queries

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class TenantBaseQuery(
    @JsonIgnore
    var tenantId: String = "",
    @JsonIgnore
    var remoteAddress: String = "",
    @JsonIgnore
    var remoteHostName: String = "",
    @JsonIgnore
    var triggeredBy: String = "",
) : BaseQuery()
