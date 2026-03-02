package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.common.utils.DateUtils
import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.common.utils.ExcelUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.timesheet.api.queries.SearchTimesheetRowsByFilterExportToExcelQuery
import com.syc.dashboard.query.timesheet.dto.TimeSheetRowsSearchRowExportToExcelDto
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetRowReactiveRepository
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchTimesheetRowsByFilterExportToExcelQueryHandler(
    private val timesheetRowReactiveRepository: TimesheetRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchTimesheetRowsByFilterExportToExcelQuery

        return timesheetRowReactiveRepository
            .findByTenantIdAndJobCodeAndWeekEndingPeriodAndExportToExcel(
                tenantId = query.tenantId,
                jobcode = query.jobcode,
                startDate = query.startDate,
                endDate = query.endDate,
            )
            .map {
                EntityDtoConversion.copyFromJson(it, TimesheetRowDto::class.java)
            }
            .collectList()
            .map { reportRowList ->
                val timeSheetRowsSearchRowExportToExcelDto = TimeSheetRowsSearchRowExportToExcelDto()
                val entriesWithHeaders = mutableListOf<List<ExcelCell>>()

                val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

                val sortedReportRows = reportRowList.sortedBy {
                    it.weeklyDetails.firstOrNull()?.day?.let { dateStr ->
                        LocalDate.parse(dateStr, dateFormatter)
                    }
                }
                val groupedReportRows = sortedReportRows.groupBy {
                    Pair(
                        it.employeeInfo?.firstName + " " + it.employeeInfo?.lastName,
                        it.weeklyDetails.firstOrNull()?.day,
                    )
                }
                groupedReportRows.forEach { (_, groupedList) ->
                    val firstEntry = groupedList.first()
                    val dynamicWeeklyHeaders = firstEntry.weeklyDetails.map {
                        ExcelUtils.formatCell(
                            cellValue = "${it.day}\n${DateUtils.getDayOfWeek(it.day)}",
                            wrapText = true,
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        )
                    }

                    val headers: List<ExcelCell> = listOf(
                        ExcelUtils.formatCell(
                            "Sr No",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                        ExcelUtils.formatCell(
                            "Job Code & Cost Code",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                        ExcelUtils.formatCell(
                            "Cost Code Description",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                        ExcelUtils.formatCell(
                            "Employee Name",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                    ) + dynamicWeeklyHeaders + listOf(
                        ExcelUtils.formatCell(
                            "Total",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                    )

                    entriesWithHeaders.add(headers)

                    groupedList.forEachIndexed { index, timeSheetRowsSearchRowDto ->
                        val weeklyCells = timeSheetRowsSearchRowDto.weeklyDetails.map {
                            ExcelUtils.formatCell(it.numberOfHours.toString())
                        }
                        val totalHours = timeSheetRowsSearchRowDto.weeklyDetails.sumOf { it.numberOfHours }
                        val dataRow = listOf(
                            ExcelUtils.formatCell((index + 1).toString()),
                            ExcelUtils.formatCell("${timeSheetRowsSearchRowDto.jobCodeInfo?.code ?: ""} ${timeSheetRowsSearchRowDto.costCodeInfo?.code ?: ""}"),
                            ExcelUtils.formatCell(timeSheetRowsSearchRowDto.costCodeInfo?.description ?: ""),
                            ExcelUtils.formatCell("${timeSheetRowsSearchRowDto.employeeInfo?.firstName ?: ""} ${timeSheetRowsSearchRowDto.employeeInfo?.lastName ?: ""}"),
                        ) + weeklyCells + listOf(
                            ExcelUtils.formatCell(totalHours.toString()),
                        )
                        entriesWithHeaders.add(dataRow)
                    }
                    entriesWithHeaders.add(listOf(ExcelUtils.formatCell("")))
                }

                timeSheetRowsSearchRowExportToExcelDto.timeSheetRowsStatementRows = entriesWithHeaders
                timeSheetRowsSearchRowExportToExcelDto
            }
            .flux()
    }
}
