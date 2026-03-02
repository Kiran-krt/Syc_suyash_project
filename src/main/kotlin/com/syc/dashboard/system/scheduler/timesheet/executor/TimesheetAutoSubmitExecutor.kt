package com.syc.dashboard.system.scheduler.timesheet.executor

import com.syc.dashboard.command.timesheet.api.commands.TimesheetUpdateStatusCommand
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.framework.core.scheduler.ScheduleExecuteAware
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetByStatusListAndBeforeTodaysDateQuery
import com.syc.dashboard.query.timesheet.dto.TimesheetDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TimesheetAutoSubmitExecutor @Autowired constructor(
    @Value("\${syc.system.tenantIds:syc}")
    private val tenantIds: List<String> = listOf("syc"), // TODO: get array of tenants used in system
    private val queryDispatcher: QueryDispatcher,
    private val commandDispatcher: CommandDispatcher,
) : ScheduleExecuteAware {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        tenantIds.forEach { tenantId ->
            val query = FindTimesheetByStatusListAndBeforeTodaysDateQuery(
                status = listOf(
                    TimesheetStatusEnum.IN_PROGRESS,
                ),
            )
            query.tenantId = tenantId
            query.remoteAddress = "SYSTEM"
            query.remoteHostName = "SYSTEM"
            query.triggeredBy = UserRole.SYSTEM.name

            queryDispatcher.send(query = query)
                .map { it as TimesheetDto }
                .map {
                    val command = TimesheetUpdateStatusCommand(
                        tenantId = it.tenantId,
                        id = it.id,
                        status = TimesheetStatusEnum.SUBMITTED_BY_SYSTEM,
                    )
                    command.remoteAddress = query.remoteAddress
                    command.remoteHostName = query.remoteHostName
                    command.triggeredBy = query.triggeredBy

                    commandDispatcher.send(command)
                }
                .subscribe()
        }
    }
}
