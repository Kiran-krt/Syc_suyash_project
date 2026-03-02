package com.syc.dashboard.framework.core.aspect.event

import com.syc.dashboard.framework.common.config.NotificationConfig
import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.framework.core.producers.EventProducer
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Aspect
@Configuration
class NotificationEventAspect @Autowired constructor(
    private val eventProducer: EventProducer,
    private val notificationConfig: NotificationConfig,
) : AbstractEventAspect() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @After("execution(* com.syc.dashboard.framework.core.infrastructure.EventHandler.on(..))")
    fun after(joinPoint: JoinPoint) {
        val event = getEvent(joinPoint = joinPoint)

        if (event != null) {
            val notificationEventInfo = notificationConfig.events[event.javaClass.simpleName]
            if (notificationEventInfo != null) {
                val notificationEvent =
                    Class.forName(notificationEventInfo.eventClass).kotlin.java.getDeclaredConstructor()
                        .newInstance() as NotificationBaseEvent
                BeanUtils.copyProperties(event, notificationEvent)
                BeanUtils.copyProperties(notificationEventInfo, notificationEvent)

                eventProducer.produceWithKafka(notificationEvent.javaClass.simpleName, notificationEvent)
            }
        }
    }
}
