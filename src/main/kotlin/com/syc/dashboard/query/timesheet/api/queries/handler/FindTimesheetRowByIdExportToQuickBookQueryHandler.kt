package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.common.config.QuickBookConfig
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.settings.repository.jpa.SettingsRepository
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetRowByIdExportToQuickBookQuery
import com.syc.dashboard.query.timesheet.dto.TimesheetRowExportToQuickBookDto
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetRowReactiveRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

@Component
class FindTimesheetRowByIdExportToQuickBookQueryHandler(
    private val timesheetRowReactiveRepository: TimesheetRowReactiveRepository,
    private val settingsRepository: SettingsRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindTimesheetRowByIdExportToQuickBookQuery
        return timesheetRowReactiveRepository
            .findByTenantIdAndTimesheetIdWithJobCodeAndCostCodeDetailsExportToExcel(
                tenantId = query.tenantId,
                timesheetId = query.timesheetId,
            )
            .map { timesheet ->
                val timesheetRowDto =
                    EntityDtoConversion.copyFromJson(timesheet, TimesheetRowExportToQuickBookDto::class.java)
                if (timesheet.jobCodeInfo != null) {
                    timesheetRowDto.jobCodeInfo =
                        EntityDtoConversion.copyFromJson(timesheet.jobCodeInfo!!, JobCodeDto::class.java)
                }
                if (timesheet.costCodeInfo != null) {
                    timesheetRowDto.costCodeInfo =
                        EntityDtoConversion.copyFromJson(timesheet.costCodeInfo!!, CostCodeDto::class.java)
                }
                timesheetRowDto
            }
    }

    private fun createIIFFile(timesheetRows: List<TimesheetRowExportToQuickBookDto>): ByteArray {
        val timeActRows = timesheetRows.flatMap { timesheetRowDto ->
            timesheetRowDto.weeklyDetails.filter { it.numberOfHours > 0 }.map { dayDetail ->
                val projectCode = timesheetRowDto.projectInfo?.projectCode ?: ""
                val projectQuickBookDescription = timesheetRowDto.projectInfo?.quickBookDescription ?: ""
                val jobCode = timesheetRowDto.jobCodeInfo?.code ?: ""
                val costCode = timesheetRowDto.costCodeInfo?.code ?: ""
                val quickBookDescription = timesheetRowDto.jobCodeInfo?.quickBookDescription ?: ""

                val combinedCode = "$jobCode-$costCode"

                var displayJobCode =
                    "$projectCode-$projectQuickBookDescription:$jobCode-$quickBookDescription:$costCode"

                var item = "Consulting Expense"

                when {
                    combinedCode in listOf(
                        "700000-FLHODY",
                        "700000-HOLIDY",
                        "700000-PERSTM",
                        "700000-VACATN",
                    ) -> {
                        displayJobCode = "Admin"
                        item = "Admin"
                    }

                    jobCode == "800000" && costCode == "ADMINT" -> {
                        displayJobCode = "Admin"
                        item = "Admin"
                    }

                    jobCode == "800000" && costCode == "TRAING" -> {
                        displayJobCode = "Training and Learning"
                        item = "Training"
                    }

                    jobCode == "900001" && costCode == "MRKTNG" -> {
                        displayJobCode = "Marketing"
                        item = "Marketing"
                    }

                    jobCode == "900001" && (
                        costCode.startsWith("MES") ||
                            costCode.startsWith("KB") ||
                            costCode.startsWith("BCS") ||
                            costCode.startsWith("0GPROP")
                        ) -> {
                        displayJobCode = "$jobCode - Proposal:$costCode"
                        item = "Proposal"
                    }
                }

                val emp = "${timesheetRowDto.employeeInfo?.firstName ?: ""} ${timesheetRowDto.employeeInfo?.lastName ?: ""}"
                val settings = settingsRepository.findByTenantId(tenantId = timesheetRowDto.tenantId)

                val pItemPrefix = when (combinedCode) {
                    "700000-FLHODY" -> "Floating Holiday"
                    "700000-HOLIDY" -> "Holiday Pay"
                    "700000-PERSTM" -> "Sick"
                    "700000-VACATN" -> "Vacation"
                    else -> ""
                }

                val quarterName = settings.get().yearlyQuarterInfo!!.yearlyQuarterName

                val pItem = listOf(pItemPrefix, quarterName)
                    .filter { !it.isNullOrBlank() }
                    .joinToString(" ")

                val duration = dayDetail.numberOfHours
                val note = timesheetRowDto.costCodeInfo?.description ?: ""
                val date = dayDetail.day

                val proj = ""

                val xferToPayroll = "N"
                val billingStatus = "1"

                "TIMEACT\t$date\t\"$displayJobCode\"\t\"$emp\"\t\"$item\"\t\"$pItem\"\t$duration\t$proj\t\"$note\"\t$xferToPayroll\t$billingStatus"
            }
        }.joinToString("\n")

        val iifContent = QuickBookConfig.HEADER + QuickBookConfig.TIME_ACT_HEADER + timeActRows
        val outputStream = ByteArrayOutputStream()
        val writer = OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
        writer.write(iifContent)
        writer.flush()
        writer.close()
        return outputStream.toByteArray()
    }

    fun handleAndGenerateIIFFile(query: FindTimesheetRowByIdExportToQuickBookQuery): Mono<ByteArray> {
        return handle(query)
            .collectList()
            .map { timesheetRow ->
                val timesheetRows = timesheetRow.map { it as TimesheetRowExportToQuickBookDto }
                createIIFFile(timesheetRows)
            }
    }
}
