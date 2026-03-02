package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum
import java.util.*

class RegisterTvhgInputCommand(
    id: String = "",
    val name: String = "",
    val description: String = "",
    val status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
    var projectTitle: String = "",
    val createdBy: String = "",
    val createdOn: Date = Date(),
) : TenantBaseCommand(id = id)
