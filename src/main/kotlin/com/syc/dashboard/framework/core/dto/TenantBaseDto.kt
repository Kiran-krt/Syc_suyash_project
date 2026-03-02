package com.syc.dashboard.framework.core.dto

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class TenantBaseDto(
    @JsonIgnore
    var tenantId: String,
) : BaseDto()
