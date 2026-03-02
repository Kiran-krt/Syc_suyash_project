package com.syc.dashboard.framework.core.controllers

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.framework.core.exceptions.TenantIdNotFoundInRequestHeaderException
import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StringUtils

abstract class RootController {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    var tenantId: String = ""
    var remoteAddress: String = ""
    var remoteHostName: String = ""
    var triggeredBy: String = ""

    fun buildCommand(command: TenantBaseCommand): TenantBaseCommand {
        if (!StringUtils.hasText(tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the controller while building command")
        }

        command.tenantId = tenantId
        command.remoteHostName = remoteHostName
        command.remoteAddress = remoteAddress
        command.triggeredBy = triggeredBy
        return command
    }

    fun buildQuery(query: TenantBaseQuery): TenantBaseQuery {
        if (!StringUtils.hasText(tenantId)) {
            throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the controller while building query")
        }

        query.tenantId = tenantId
        query.remoteHostName = remoteHostName
        query.remoteAddress = remoteAddress
        query.triggeredBy = triggeredBy
        return query
    }
}
