package com.syc.dashboard.query.expensereport.dto

import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.core.dto.BaseDto

class ExpenseReportExportToExcelDto(
    var headers: List<ExcelCell> = mutableListOf(),
    var suyashHeaders: List<ExcelCell> = mutableListOf(),
    var employeeHeaders: List<ExcelCell> = mutableListOf(),
    var employeeRows: List<List<ExcelCell>> = mutableListOf(),
    var suyashRows: List<List<ExcelCell>> = mutableListOf(),
    var employeeTotalRow: List<ExcelCell> = mutableListOf(),
    var suyashTotalRow: List<ExcelCell> = mutableListOf(),
    var expenseReportInfo: List<List<ExcelCell>> = mutableListOf(),
    var employeeDetails: List<List<ExcelCell>> = mutableListOf(),
    var employeeSectionHeaders: List<ExcelCell> = mutableListOf(),
    var suyashSectionHeaders: List<ExcelCell> = mutableListOf(),
) : BaseDto()
