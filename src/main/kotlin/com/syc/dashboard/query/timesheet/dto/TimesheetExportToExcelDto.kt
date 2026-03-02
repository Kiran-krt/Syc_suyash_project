package com.syc.dashboard.query.timesheet.dto

import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.core.dto.BaseDto

class TimesheetExportToExcelDto(
    var headers: List<ExcelCell> = mutableListOf(),
    var rows: List<List<ExcelCell>> = mutableListOf(),
    var totalRow: List<ExcelCell> = mutableListOf(),
    var timesheetInfo: List<List<ExcelCell>> = mutableListOf(),
    var employeeDetails: List<List<ExcelCell>> = mutableListOf(),
) : BaseDto()
