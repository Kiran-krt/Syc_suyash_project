package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.common.utils.ExcelUtils
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.expensereport.api.queries.ExpenseReportSearchRowExportToExcelQuery
import com.syc.dashboard.query.expensereport.dto.ExpenseReportSearchRowExportToExcelDto
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportRowReactiveRepository
import reactor.core.publisher.Flux
import java.math.BigDecimal

class ExpenseReportSearchRowExportToExcelQueryHandler(
    private val expenseReportRowReactiveRepository: ExpenseReportRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as ExpenseReportSearchRowExportToExcelQuery
        return expenseReportRowReactiveRepository
            .findAllByTenantIdAndJobCodeIdAndPeriodFromAndExpenseReportStatusIn(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
                periodFrom = query.periodFrom,
            )
            .collectList()
            .map { expenseReportSearchRowDtos ->
                val expenseReportSearchRowExportToExcelDto = ExpenseReportSearchRowExportToExcelDto()

                val headers: List<ExcelCell> = listOf(
                    ExcelUtils.formatCell(
                        cellValue = "Sr No",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Expense Date",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Job Code",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Cost Code",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Expense Description",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Expense By",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Employee Name",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Employee Number",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Miles",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Mileage Amount ($)",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Expense Type",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Expense Amount ($)",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                    ),
                    ExcelUtils.formatCell(
                        cellValue = "Reimbursable Amount ($)",
                        fontBold = true,
                        fillPattern = ExcelCell.FillTypeEnum.SOLID,
                        fontColor = "f48458",
                    ),
                )

                expenseReportSearchRowExportToExcelDto.headers = headers

                var totalMileageAmount = BigDecimal(0.0)
                var totalExpenseAmount = BigDecimal(0.0)
                var totalReimbursableAmount = BigDecimal(0.0)

                expenseReportSearchRowExportToExcelDto.expenseRows =
                    expenseReportSearchRowDtos.mapIndexed { index, expenseReportSearchRowDto ->
                        val mileageAmount =
                            BigDecimal(expenseReportSearchRowDto.expenseMileageRate * expenseReportSearchRowDto.expenseMileage).setScale(
                                2,
                                BigDecimal.ROUND_HALF_UP,
                            )
                        val expenseAmount = expenseReportSearchRowDto.expenseAmount
                        val reimbursableAmount =
                            BigDecimal(expenseReportSearchRowDto.expenseMileage * expenseReportSearchRowDto.expenseMileageRate + expenseReportSearchRowDto.expenseAmount).setScale(
                                2,
                                BigDecimal.ROUND_HALF_UP,
                            )

                        totalMileageAmount = totalMileageAmount.plus(mileageAmount)
                        totalExpenseAmount = totalExpenseAmount.plus(BigDecimal(expenseAmount))
                        totalReimbursableAmount = totalReimbursableAmount.plus(reimbursableAmount)

                        listOf(
                            ExcelUtils.formatCell(cellValue = "${index + 1}"),
                            ExcelUtils.formatCell(cellValue = expenseReportSearchRowDto.expenseDate),
                            ExcelUtils.formatCell(cellValue = expenseReportSearchRowDto.jobCodeInfo?.code ?: ""),
                            ExcelUtils.formatCell(cellValue = expenseReportSearchRowDto.costCodeInfo?.code ?: ""),
                            ExcelUtils.formatCell(cellValue = expenseReportSearchRowDto.expenseDescription),
                            ExcelUtils.formatCell(cellValue = expenseReportSearchRowDto.expenseBy),
                            ExcelUtils.formatCell(
                                cellValue = (
                                    expenseReportSearchRowDto.employeeInfo?.firstName
                                        ?: ""
                                    ) + (expenseReportSearchRowDto.employeeInfo?.lastName ?: ""),
                            ),
                            ExcelUtils.formatCell(
                                cellValue = expenseReportSearchRowDto.employeeInfo?.employeeNumber ?: "",
                            ),
                            ExcelUtils.formatCell(
                                cellValue = expenseReportSearchRowDto.expenseMileage,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                            ),
                            ExcelUtils.formatCell(
                                cellValue = mileageAmount,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                                fontColor = "f48458",
                            ),
                            ExcelUtils.formatCell(
                                cellValue = expenseReportSearchRowDto.expenseTypeInfo?.expenseType ?: "",
                            ),
                            ExcelUtils.formatCell(
                                cellValue = expenseAmount,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                                fontColor = "f48458",
                            ),
                            ExcelUtils.formatCell(
                                cellValue = reimbursableAmount,
                                dataType = ExcelCell.DataTypeEnum.NUMBER,
                                fontBold = true,
                                fontColor = "f48458",
                            ),
                        )
                    }

                expenseReportSearchRowExportToExcelDto.expenseTotalRow =
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
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(
                            cellValue = totalMileageAmount,
                            dataType = ExcelCell.DataTypeEnum.NUMBER,
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            fontColor = "f48458",
                        ),
                        ExcelUtils.formatCell(cellValue = "", fillPattern = ExcelCell.FillTypeEnum.SOLID),
                        ExcelUtils.formatCell(
                            cellValue = totalExpenseAmount,
                            dataType = ExcelCell.DataTypeEnum.NUMBER,
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            fontColor = "f48458",
                        ),
                        ExcelUtils.formatCell(
                            cellValue = totalReimbursableAmount,
                            dataType = ExcelCell.DataTypeEnum.NUMBER,
                            fontBold = true,
                            fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            fontColor = "f48458",
                        ),
                    )

                if (expenseReportSearchRowDtos.size > 0) {
                    val firstExpenseReportSearchRow = expenseReportSearchRowDtos.first()

                    expenseReportSearchRowExportToExcelDto.expenseReportInfo = listOf(
                        listOf(
                            ExcelUtils.formatCell(
                                cellValue = "Expense Report Details",
                                fontBold = true,
                                fillPattern = ExcelCell.FillTypeEnum.SOLID,
                            ),
                        ),
                        listOf(
                            ExcelUtils.formatCell(
                                cellValue = "Period From",
                                fontBold = true,
                            ),
                            ExcelUtils.formatCell(cellValue = firstExpenseReportSearchRow.periodFrom),
                        ),
                        listOf(
                            ExcelUtils.formatCell(
                                cellValue = "Period To",
                                fontBold = true,
                            ),
                            ExcelUtils.formatCell(cellValue = firstExpenseReportSearchRow.periodTo),
                        ),
                        listOf(
                            ExcelUtils.formatCell(
                                cellValue = "Job Code",
                                fontBold = true,
                            ),
                            ExcelUtils.formatCell(cellValue = firstExpenseReportSearchRow.jobCodeInfo?.code ?: ""),
                        ),
                    )
                }

                expenseReportSearchRowExportToExcelDto
            }
            .flux()
    }
}
