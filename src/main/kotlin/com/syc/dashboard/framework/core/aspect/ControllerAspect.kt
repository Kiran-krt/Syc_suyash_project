package com.syc.dashboard.framework.core.aspect

import com.syc.dashboard.framework.common.security.SecurityConstants
import com.syc.dashboard.framework.common.security.jwt.JwtUtils
import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.exceptions.TenantIdNotFoundInRequestHeaderException
import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.StringUtils

@Aspect
@Configuration
class ControllerAspect @Autowired constructor(
    private val jwtUtils: JwtUtils,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Before(
        "execution(* com.syc.dashboard..controllers..*(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping))",
    )
    fun before(joinPoint: JoinPoint) {
        val request = getArgByParameterName(joinPoint = joinPoint, parameterName = "request")

        if (request != null && request is ServerHttpRequest && joinPoint.target is RootController) {
            val rootController = joinPoint.target as RootController

            var tenantId = request.headers[SecurityConstants.TENANT_ID_HEADER]?.get(0)

            if (tenantId == null) {
                tenantId = request.queryParams[SecurityConstants.TENANT_ID_HEADER]?.get(0)
                    ?: throw TenantIdNotFoundInRequestHeaderException("Tenant ID not present in the request header")
            }

            rootController.tenantId = tenantId
            rootController.remoteAddress = request.remoteAddress?.hostString ?: "NOT_FOUND"
            rootController.remoteHostName = request.remoteAddress?.hostName ?: "NOT_FOUND"

            val jwt = jwtUtils.getJWTFromRequest2(request)
            rootController.triggeredBy = if (StringUtils.hasText(jwt)) jwtUtils.getUserIdFromJWT(jwt) else ""

            val command = getArgByParameterName(joinPoint = joinPoint, parameterName = "command")
            if (command != null && command is TenantBaseCommand) {
                rootController.buildCommand(command)
            }

            val query = getArgByParameterName(joinPoint = joinPoint, parameterName = "query")
            if (query != null && query is TenantBaseQuery) {
                rootController.buildQuery(query)
            }
        }
    }

    private fun getArgByParameterName(joinPoint: JoinPoint, parameterName: String): Any? {
        val methodSig: MethodSignature = joinPoint.signature as MethodSignature
        val args = joinPoint.args
        val idx: Int = methodSig.parameterNames.indexOf(parameterName)
        return if (idx > -1 && args.size > idx) {
            args[idx]
        } else {
            null
        }
    }
}
