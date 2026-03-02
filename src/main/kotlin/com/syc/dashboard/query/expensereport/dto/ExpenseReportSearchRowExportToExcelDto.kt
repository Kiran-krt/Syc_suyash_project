package com.syc.dashboard.query.expensereport.dto

import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.core.dto.BaseDto

class ExpenseReportSearchRowExportToExcelDto(
    var headers: List<ExcelCell> = mutableListOf(),
    var expenseRows: List<List<ExcelCell>> = mutableListOf(),
    var expenseTotalRow: List<ExcelCell> = mutableListOf(),
    var expenseReportInfo: List<List<ExcelCell>> = mutableListOf(),
) : BaseDto()
