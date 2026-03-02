package com.syc.dashboard.system.scheduler.timesheet

import com.syc.dashboard.system.scheduler.timesheet.executor.TimesheetAutoCreateExecutor
import com.syc.dashboard.system.scheduler.timesheet.executor.TimesheetAutoSubmitExecutor
import com.syc.dashboard.system.scheduler.timesheet.executor.TimesheetReminderNotificationExecutor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class TimesheetScheduler @Autowired constructor(
    private val timesheetAutoSubmitExecutor: TimesheetAutoSubmitExecutor,
    private val timesheetAutoCreateExecutor: TimesheetAutoCreateExecutor,
    private val timesheetReminderNotificationExecutor: TimesheetReminderNotificationExecutor,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 00 08 * * SAT", zone = "EST") // every Saturday 8 am and EST timezone
    fun prepareAutoApproveTimesheetScheduler() = timesheetAutoSubmitExecutor.execute()

    @Scheduled(cron = "0 00 11 * * THU,FRI", zone = "EST") // every Thursday and Friday at 11 am and EST timezone
    fun prepareAutoCreateTimesheetScheduler() = timesheetAutoCreateExecutor.execute()

    @Scheduled(cron = "0 0 15 ? * THU", zone = "EST") // Every Thursday at 3 PM EST
    @Scheduled(cron = "0 0 10 ? * FRI", zone = "EST") // Every Friday at 10 AM EST
    fun prepareAutoNotificationTimesheetScheduler() = timesheetReminderNotificationExecutor.execute()
}
