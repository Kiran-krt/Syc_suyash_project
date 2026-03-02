package com.syc.dashboard.framework.core.aspect.event

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature

abstract class AbstractEventAspect {
    fun getEvent(joinPoint: JoinPoint, parameterName: String = "event"): TenantBaseEvent? {
        val methodSig: MethodSignature = joinPoint.signature as MethodSignature
        val args = joinPoint.args
        val idx: Int = methodSig.parameterNames.indexOf(parameterName)
        return if (args.size > idx) {
            args[idx] as TenantBaseEvent
        } else {
            null
        }
    }

    fun getQuery(joinPoint: JoinPoint, parameterName: String = "query"): TenantBaseQuery? {
        val methodSig: MethodSignature = joinPoint.signature as MethodSignature
        val args = joinPoint.args
        val idx: Int = methodSig.parameterNames.indexOf(parameterName)
        return if (args.size > idx) {
            args[idx] as TenantBaseQuery
        } else {
            null
        }
    }
}
