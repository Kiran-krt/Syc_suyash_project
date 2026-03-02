package com.syc.dashboard.query.tvhgConfig.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassEnum

class FindMdStandardNumberByIdQuery(
    var structureClass: StructureClassEnum = StructureClassEnum.INLET,
) : TenantBaseQuery()
