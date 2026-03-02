package com.syc.dashboard.query.timesheet.dto

import com.syc.dashboard.framework.common.utils.ExcelCell
import com.syc.dashboard.framework.core.dto.BaseDto

class TimeSheetRowsSearchRowExportToExcelDto(
    var headers: List<ExcelCell> = mutableListOf(),
    var timeSheetRowsStatementRows: List<List<ExcelCell>> = mutableListOf(),
) : BaseDto()
