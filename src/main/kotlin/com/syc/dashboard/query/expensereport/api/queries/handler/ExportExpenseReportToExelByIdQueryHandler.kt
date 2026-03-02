package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.common.utils.ExcelUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.expensereport.api.queries.ExportExpenseReportToExcelByIdQuery
import com.syc.dashboard.query.expensereport.dto.ExpenseReportDto
import com.syc.dashboard.query.expensereport.dto.ExpenseReportExportToExcelDto
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.settings.dto.ExpenseTypeDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.math.BigDecimal
import java.math.RoundingMode

class ExportExpenseReportToExelByIdQueryHandler(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as ExportExpenseReportToExcelByIdQuery
        return expenseReportReactiveRepository
            .findByTenantIdAndIdWithAllDetails(
                tenantId = query.tenantId,
                id = query.expenseReportId,
            )
            .flatMap { expenseReport ->
                if (expenseReport.supervisorInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(
                        expenseReport.tenantId,
                        expenseReport.supervisorId,
                    )
                        .map {
                            expenseReport.supervisorInfo = it
                            expenseReport
                        }
                        .switchIfEmpty { Mono.just(expenseReport) }
                } else {
                    Mono.just(expenseReport)
                }
            }
            .map { expenseReport ->
                val expenseReportDto = EntityDtoConversion.toDto(expenseReport, ExpenseReportDto::class)

                if (expenseReport.employeeInfo != null) {
                    expenseReportDto.employeeInfo =
                        EntityDtoConversion.toDto(expenseReport.employeeInfo!!, EmployeeDto::class)
                }

                if (expenseReport.supervisorInfo != null) {
                    expenseReportDto.supervisorInfo =
                        EntityDtoConversion.toDto(expenseReport.supervisorInfo!!, EmployeeDto::class)
                }
                if (expenseReport.expenseReportRowsForEmployee != null) {
                    expenseReportDto.expenseReportRowsForEmployee = expenseReport.expenseReportRowsForEmployee?.map {
                        val expenseReportRowDto = EntityDtoConversion.toDto(it, ExpenseReportRowDto::class)

                        if (it.expenseTypeInfo != null) {
                            expenseReportRowDto.expenseTypeInfo =
                                EntityDtoConversion.toDto(it.expenseTypeInfo!!, ExpenseTypeDto::class)
                        }
                        if (it.jobCodeInfo != null) {
                            expenseReportRowDto.jobCodeInfo =
                                EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                        }
                        if (it.costCodeInfo != null) {
                            expenseReportRowDto.costCodeInfo =
                                EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                        }
                        expenseReportRowDto
                    }
                }

                if (expenseReport.expenseReportRowsForSuyash != null) {
                    expenseReportDto.expenseReportRowsForSuyash = expenseReport.expenseReportRowsForSuyash?.map {
                        val expenseReportRowDto = EntityDtoConversion.toDto(it, ExpenseReportRowDto::class)

                        if (it.expenseTypeInfo != null) {
                            expenseReportRowDto.expenseTypeInfo =
                                EntityDtoConversion.toDto(it.expenseTypeInfo!!, ExpenseTypeDto::class)
                        }
                        if (it.jobCodeInfo != null) {
                            expenseReportRowDto.jobCodeInfo =
                                EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                        }
                        if (it.costCodeInfo != null) {
                            expenseReportRowDto.costCodeInfo =
                                EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                        }
                        expenseReportRowDto
                    }
                }
                expenseReportDto
            }
            .map { expenseReportDto ->

                val expenseReportExportToExcelDto = ExpenseReportExportToExcelDto()

                val employeeSectionHeaders: List<ExcelCell> = listOf(
                    ExcelUtils.formatCell(
                        cellValue = "Expense Report For Employee",
                        dataType = ExcelCell.DataTypeEnum.STRING,
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                )

                val suyashSectionHeaders: List<ExcelCell> = listOf(
                    ExcelUtils.formatCell(
                        cellValue = "Expense Report For Suyash",
                        dataType = ExcelCell.DataTypeEnum.STRING,
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                )

                val headers: List<ExcelCell> = listOf(
                    ExcelUtils.formatCell(
                        cellValue = "Sr No",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Date",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Job Code",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Cost Code",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Description",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Expense Type",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Mileage Rate",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Mileage (Miles)",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Mileage Amount",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Amount",
                        fontBold = true,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Reimbursable Amount($)",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                )

                val employeeHeaders: List<ExcelCell> = headers
                val suyashHeaders: List<ExcelCell> = headers
                expenseReportExportToExcelDto.expenseReportInfo = listOf(
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Status",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.status.toString()),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Period From",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.periodFrom),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Period To",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.periodTo),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Description",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.description),
                    ),
                    listOf(ExcelUtils.formatCell(cellValue = "")),
                )

                val employeeRows: MutableList<List<ExcelCell>> = mutableListOf()
                var employeeTotalRow: List<Double> = MutableList(0) { 0.0 }

                for ((index, value) in expenseReportDto.expenseReportRowsForEmployee!!.withIndex()) {
                    val totalMileage = expenseReportDto.expenseReportRowsForEmployee?.sumOf { it.expenseMileageRate }
                        ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                        ?: 0.0
                    val totalMiles = expenseReportDto.expenseReportRowsForEmployee?.sumOf { it.expenseMileage } ?: 0.0
                    val mileageAmount =
                        expenseReportDto.expenseReportRowsForEmployee?.sumOf { it.expenseMileageRate * it.expenseMileage }
                            ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                            ?: 0.0
                    val totalAmount = expenseReportDto.expenseReportRowsForEmployee?.sumOf { it.expenseAmount }
                        ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                        ?: 0.0
                    val totalReimbursableAmount =
                        expenseReportDto.expenseReportRowsForEmployee?.sumOf { (it.expenseMileage * it.expenseMileageRate + it.expenseAmount) }
                            ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                            ?: 0.0
                    employeeTotalRow = listOf(totalMileage, totalMiles, mileageAmount, totalAmount, totalReimbursableAmount)

                    employeeRows.add(
                        mutableListOf(
                            ExcelUtils.formatCell(
                                cellValue = "${index + 1}",
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseDate,
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.jobCodeInfo?.code ?: "",
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.costCodeInfo?.code ?: "",
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseDescription,
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseTypeInfo?.expenseType ?: "",
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseMileageRate,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseMileage,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = BigDecimal(value.expenseMileageRate * value.expenseMileage).setScale(
                                    2,
                                    BigDecimal.ROUND_HALF_UP,
                                ),
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseAmount,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = BigDecimal(value.expenseMileage * value.expenseMileageRate + value.expenseAmount).setScale(
                                    2,
                                    BigDecimal.ROUND_HALF_UP,
                                ),
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                        ),
                    )
                }

                expenseReportExportToExcelDto.employeeTotalRow =
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Total",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                    ) +
                        employeeTotalRow.map {
                            ExcelUtils.formatCell(
                                cellValue = it,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                                fontBold = true,
                                fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            )
                        }

                expenseReportExportToExcelDto.employeeSectionHeaders = employeeSectionHeaders
                expenseReportExportToExcelDto.employeeHeaders = employeeHeaders
                expenseReportExportToExcelDto.employeeRows = employeeRows

                val suyashRows: MutableList<List<ExcelCell>> = mutableListOf()
                var suyashTotalRow: List<Double> = MutableList(0) { 0.0 }

                val totalMileage = expenseReportDto.expenseReportRowsForSuyash?.sumOf { it.expenseMileageRate }
                    ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                    ?: 0.0
                val totalMiles = expenseReportDto.expenseReportRowsForSuyash?.sumOf { it.expenseMileage } ?: 0.0
                val mileageAmount =
                    expenseReportDto.expenseReportRowsForSuyash?.sumOf { it.expenseMileageRate * it.expenseMileage }
                        ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                        ?: 0.0
                val totalAmount = expenseReportDto.expenseReportRowsForSuyash?.sumOf { it.expenseAmount }
                    ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                    ?: 0.0
                val totalReimbursableAmount =
                    expenseReportDto.expenseReportRowsForSuyash?.sumOf { (it.expenseMileage * it.expenseMileageRate + it.expenseAmount) }
                        ?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                        ?: 0.0
                suyashTotalRow = listOf(totalMileage, totalMiles, mileageAmount, totalAmount, totalReimbursableAmount)

                for ((index, value) in expenseReportDto.expenseReportRowsForSuyash!!.withIndex()) {
                    suyashRows.add(
                        mutableListOf(
                            ExcelUtils.formatCell(
                                cellValue = "${index + 1}",
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseDate,
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.jobCodeInfo?.code ?: "",
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.costCodeInfo?.code ?: "",
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseDescription,
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseTypeInfo?.expenseType ?: "",
                                dataType = ExcelCell.DataTypeEnum.STRING,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseMileageRate,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseMileage,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = BigDecimal(value.expenseMileage * value.expenseMileageRate).setScale(
                                    2,
                                    BigDecimal.ROUND_HALF_UP,
                                ),
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = value.expenseAmount,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = BigDecimal(value.expenseMileage * value.expenseMileageRate + value.expenseAmount).setScale(
                                    2,
                                    BigDecimal.ROUND_HALF_UP,
                                ),
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                        ),
                    )
                }

                expenseReportExportToExcelDto.suyashTotalRow =
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Total",
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        ),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                    ) +
                        suyashTotalRow.map {
                            ExcelUtils.formatCell(
                                cellValue = it,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                                fontBold = true,
                                fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            )
                        }

                val employeeInfo = expenseReportDto.employeeInfo!!
                expenseReportExportToExcelDto.employeeDetails = listOf(
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Employee Id",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = employeeInfo.employeeNumber),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Name",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = "${employeeInfo.firstName} ${employeeInfo.lastName}"),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Employee Signature",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.employeeSignature),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Supervisor Signature",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.supervisorSignature),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Reimbursement Signature",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(cellValue = expenseReportDto.adminSignature),
                    ),
                    listOf(
                        ExcelUtils.formatCell(
                            cellValue = "Supervisor",
                            fontBold = true,
                        ),
                        ExcelUtils.formatCell(
                            cellValue =
                            if (expenseReportDto.supervisorInfo != null) {
                                "${expenseReportDto.supervisorInfo!!.firstName} ${expenseReportDto.supervisorInfo!!.lastName}"
                            } else {
                                ""
                            },
                        ),
                    ),
                )

                expenseReportExportToExcelDto.suyashSectionHeaders = suyashSectionHeaders
                expenseReportExportToExcelDto.suyashHeaders = suyashHeaders
                expenseReportExportToExcelDto.suyashRows = suyashRows

                expenseReportExportToExcelDto
            }
            .flux()
    }
}
