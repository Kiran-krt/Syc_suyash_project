package com.syc.dashboard.query.timesheet.api.queries.handler

import com.syc.dashboard.framework.common.utils.DateUtils
import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.common.utils.ExcelUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.timesheet.api.queries.ExportTimesheetToExelByIdQuery
import com.syc.dashboard.query.timesheet.dto.TimesheetDto
import com.syc.dashboard.query.timesheet.dto.TimesheetExportToExcelDto
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto
import com.syc.dashboard.query.timesheet.repository.reactive.TimesheetReactiveRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class ExportTimesheetToExelByIdQueryHandler(
    private val timesheetReactiveRepository: TimesheetReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as ExportTimesheetToExelByIdQuery
        return timesheetReactiveRepository
            .findByTenantIdAndIdWithAllDetails(
                tenantId = query.tenantId,
                id = query.timesheetId,
            )
            .flatMap { timesheet ->
                if (timesheet.approvedByInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(timesheet.tenantId, timesheet.approvedByUserId)
                        .map {
                            timesheet.approvedByInfo = it
                            timesheet
                        }
                        .switchIfEmpty { Mono.just(timesheet) }
                } else {
                    Mono.just(timesheet)
                }
            }
            .map { timesheet ->
                val timesheetDto = EntityDtoConversion.toDto(timesheet, TimesheetDto::class)

                if (timesheet.employeeInfo != null) {
                    timesheetDto.employeeInfo = EntityDtoConversion.toDto(timesheet.employeeInfo!!, EmployeeDto::class)
                }

                if (timesheet.approvedByInfo != null) {
                    timesheetDto.approvedByInfo =
                        EntityDtoConversion.toDto(timesheet.approvedByInfo!!, EmployeeDto::class)
                }

                timesheetDto.timesheetRows = timesheet.timesheetRows?.map {
                    val timesheetRowDto = EntityDtoConversion.toDto(it, TimesheetRowDto::class)
                    if (it.costCodeInfo != null) {
                        timesheetRowDto.costCodeInfo = EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                    }
                    if (it.jobCodeInfo != null) {
                        timesheetRowDto.jobCodeInfo = EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                    }
                    timesheetRowDto
                }

                timesheetDto
            }
            .map { timesheetDto ->
                val timesheetExportToExcelDto = TimesheetExportToExcelDto()
                val headers: List<ExcelCell> = listOf(
                    ExcelUtils.formatCell(cellValue = "Sr No"),
                    ExcelUtils.formatCell(cellValue = "Job Code"),
                    ExcelUtils.formatCell(cellValue = "Cost Code"),
                    ExcelUtils.formatCell(cellValue = "Cost Code Description"),
                ) + timesheetDto.timesheetRows!!.first().weeklyDetails.map {
                    ExcelUtils.formatCell(cellValue = "${it.day}\n${DateUtils.getDayOfWeek(it.day)}", wrapText = true)
                } + listOf(
                    ExcelUtils.formatCell(
                        cellValue = "Total",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                )

                timesheetExportToExcelDto.headers = headers

                timesheetExportToExcelDto.timesheetInfo = listOf(
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Status",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = timesheetDto.status.toString()),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Week Start Date",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = timesheetDto.weekStartingDate),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Week End Date",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = timesheetDto.weekEndingDate),
                    ),
                )

                val rows: MutableList<List<ExcelCell>> = mutableListOf()
                var totalRow: List<Double> = MutableList(8) { 0.0 }

                for ((index, value) in timesheetDto.timesheetRows!!.withIndex()) {
                    val dailyHoursAndTotal = value.weeklyDetails.map { it.numberOfHours } +
                        value.weeklyDetails.sumOf { it.numberOfHours }
                    totalRow = totalRow.zip(dailyHoursAndTotal) { totalHours, dailyHours -> totalHours + dailyHours }

                    rows.add(
                        mutableListOf(
                            ExcelUtils.formatCell(cellValue = "${index + 1}"),
                            ExcelUtils.formatCell(cellValue = value.jobCodeInfo?.code ?: ""),
                            ExcelUtils.formatCell(cellValue = value.costCodeInfo?.code ?: ""),
                            ExcelUtils.formatCell(cellValue = value.costCodeInfo?.description ?: ""),
                        ) + value.weeklyDetails
                            .map {
                                ExcelUtils.formatCell(
                                    cellValue = if (it.numberOfHours == 0.toDouble()) "" else it.numberOfHours,
                                    comment = if (it.comment.trim().isNotEmpty()) it.comment else null,
                                    dataType = ExcelCell.DataTypeEnum.NUMBER,
                                )
                            } +
                            ExcelUtils.formatCell(
                                cellValue = dailyHoursAndTotal.last(),
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                                fontBold = true,
                                fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            ),
                    )
                }

                timesheetExportToExcelDto.rows = rows
                timesheetExportToExcelDto.totalRow =
                    listOf(
                    ExcelUtils.formatCell(
                        cellValue = "Total",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                ) +
                    MutableList(3) {
                        ExcelUtils.formatCell(
                            cellValue = "",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        )
                    } +
                    totalRow.map {
                        ExcelUtils.formatCell(
                            cellValue = it,
                            dataType = ExcelCell.DataTypeEnum.NUMBER,
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        )
                    }

                val employeeInfo = timesheetDto.employeeInfo!!
                timesheetExportToExcelDto.employeeDetails = listOf(
                    listOf(ExcelUtils.formatCell(cellValue = "")),
                    listOf(ExcelUtils.formatCell(cellValue = "")),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Employee Details",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                        ExcelUtils.formatCell(
                            cellValue = "",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Employee #"),
                        ExcelUtils.formatCell(cellValue = employeeInfo.employeeNumber),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Name"),
                        ExcelUtils.formatCell(cellValue = "${employeeInfo.firstName} ${employeeInfo.lastName}"),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Email"),
                        ExcelUtils.formatCell(cellValue = employeeInfo.email),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Role"),
                        ExcelUtils.formatCell(cellValue = employeeInfo.role.toString()),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Status"),
                        ExcelUtils.formatCell(cellValue = employeeInfo.status.toString()),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Joining Date"),
                        ExcelUtils.formatCell(cellValue = DateUtils.getDateMMddyyyy(employeeInfo.joiningDate)),
                    ),
                    listOf(
                        ExcelUtils.formatCell(cellValue = "Approver"),
                        ExcelUtils.formatCell(
                            cellValue =
                            if (timesheetDto.approvedByInfo != null) {
                                "${timesheetDto.approvedByInfo!!.firstName} ${timesheetDto.approvedByInfo!!.lastName}"
                            } else {
                                ""
                            },
                        ),
                    ),
                )

                timesheetExportToExcelDto
            }
            .flux()
    }
}
