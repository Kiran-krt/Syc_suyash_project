package com.syc.dashboard.system.scheduler.timesheet.executor

import com.syc.dashboard.framework.core.scheduler.ScheduleExecuteAware
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TimesheetAutoCreateExecutor : ScheduleExecuteAware {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        log.info("#### TODO prepareAutoCreateTimesheetScheduler")
    }
}
