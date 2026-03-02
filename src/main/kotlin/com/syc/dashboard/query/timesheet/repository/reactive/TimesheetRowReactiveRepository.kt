package com.syc.dashboard.query.timesheet.repository.reactive

import com.syc.dashboard.query.timesheet.dto.TimesheetRowWatchDto
import com.syc.dashboard.query.timesheet.entity.TimesheetRow
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface TimesheetRowReactiveRepository : ReactiveMongoRepository<TimesheetRow, String> {

    fun findByTenantIdAndTimesheetId(
        tenantId: String,
        timesheetId: String,
    ): Flux<TimesheetRow>

    @Aggregation(
        "{ \$match: { " +
            "\"tenantId\": :#{#tenantId}, " +
            "\"timesheetId\": :#{#timesheetId} " +
            "} }",
        "{ \$lookup: { " +
            "from: \"q_jobcode\", " +
            "localField: \"jobCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"jobCodeInfo\" " +
            "} }",
        "{ \$unwind: { " +
            "path: \"\$jobCodeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: { " +
            "from: \"q_costcode\", " +
            "localField: \"costCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"costCodeInfo\" " +
            "} }",
        "{ \$unwind: { " +
            "path: \"\$costCodeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: { " +
            "from: \"q_settings_payroll_item\", " +
            "localField: \"weeklyDetails.payrollItemId\", " +
            "foreignField: \"_id\", " +
            "as: \"payrollItems\" " +
            "} }",
        "{ \$addFields: { " +
            "\"weeklyDetails\": { \$map: { " +
            "input: \"\$weeklyDetails\", " +
            "as: \"wd\", " +
            "in: { \$mergeObjects: [ " +
            "\"$\$wd\", " +
            "{ \"payrollItemInfo\": { " +
            "\$arrayElemAt: [ { " +
            "\$filter: { " +
            "input: \"\$payrollItems\", " +
            "as: \"pi\", " +
            "cond: { \$eq: [\"$\$pi._id\", \"$\$wd.payrollItemId\"] } " +
            "} }, 0 ] " +
            "} } " +
            "] } " +
            "} } " +
            "} }",
        "{ \$project: { " +
            "\"tenantId\": 1, " +
            "\"timesheetId\": 1, " +
            "\"jobCodeId\": 1, " +
            "\"jobCodeInfo\": 1, " +
            "\"costCodeId\": 1, " +
            "\"costCodeInfo\": 1, " +
            "\"description\": 1, " +
            "\"isNew\": 1, " +
            "\"status\": 1, " +
            "\"employeeInfo\": 1, " +
            "\"weeklyDetails\": 1 " +
            "} }",
    )
    fun findByTenantIdAndTimesheetIdWithJobCodeAndCostCodeDetails(
        tenantId: String,
        timesheetId: String,
    ): Flux<TimesheetRow>

    @Aggregation(
        "{ \$match: { " +
            "\"tenantId\": :#{#tenantId}, " +
            "\"jobCodeId\": :#{#jobcode} " +
            "} }",
        "{ \$project: { " +
            "\"q_timesheet_row\": \"$\$ROOT\", " +
            "\"weeklyDetails\": { " +
            "\"\$filter\": { " +
            "\"input\": \"\$weeklyDetails\", " +
            "\"as\": \"dayDetail\", " +
            "\"cond\": { " +
            "\"\$let\": { " +
            "\"vars\": { " +
            "\"dayDate\": \"$\$dayDetail.day\", " +
            "\"startDate\": { \"\$ifNull\": [\":#{#startDate}\", \"\"] }, " +
            "\"endDate\": { \"\$ifNull\": [\":#{#endDate}\", \"\"] } " +
            "}, " +
            "\"in\": { " +
            "\"\$or\": [ " +
            "{ \"\$and\": [ { \"\$ne\": [\"$\$startDate\", \"\"] }, { \"\$eq\": [\"$\$endDate\", \"\"] }, { \"\$eq\": [\"$\$dayDate\", \"$\$startDate\"] } ] }, " +
            "{ \"\$and\": [ { \"\$eq\": [\"$\$startDate\", \"\"] }, { \"\$ne\": [\"$\$endDate\", \"\"] }, { \"\$eq\": [\"$\$dayDate\", \"$\$endDate\"] } ] }, " +
            "{ \"\$and\": [ " +
            "{ \"\$ne\": [\"$\$startDate\", \"\"] }, " +
            "{ \"\$ne\": [\"$\$endDate\", \"\"] }, " +
            "{ \"\$gte\": [ " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$dayDate\", \"format\": \"%m/%d/%Y\" } }, " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$startDate\", \"format\": \"%m/%d/%Y\" } } " +
            "] }, " +
            "{ \"\$lte\": [ " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$dayDate\", \"format\": \"%m/%d/%Y\" } }, " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$endDate\", \"format\": \"%m/%d/%Y\" } } " +
            "] } " +
            "] } " +
            "] " +
            "} " +
            "} " +
            "} " +
            "} " +
            "} " +
            "} } ",
        "{ \$match: { " +
            "\"weeklyDetails\": { \"\$ne\": [] } " +
            "} }",
        "{ \$lookup: { " +
            "from: \"q_timesheet\", " +
            "localField: \"q_timesheet_row.timesheetId\", " +
            "foreignField: \"_id\", " +
            "as: \"q_timesheet\" " +
            "} }",
        "{ \$unwind: { " +
            "path: \"\$q_timesheet\", " +
            "preserveNullAndEmptyArrays: false " +
            "} }",
        "{ \$replaceRoot: { newRoot: { \$mergeObjects: [\"\$q_timesheet\", \"\$q_timesheet_row\", \"\$\$ROOT\"]} }}",
        "{ \$lookup: { " +
            "from: \"q_jobcode\", " +
            "localField: \"jobCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"jobCodeInfo\" " +
            "} }",
        "{ \$unwind: { " +
            "path: \"\$jobCodeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: { " +
            "from: \"q_costcode\", " +
            "localField: \"costCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"costCodeInfo\" " +
            "} }",
        "{ \$unwind: { " +
            "path: \"\$costCodeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: { " +
            "from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\" " +
            "} }",
        "{ \$unwind: { " +
            "path: \"\$employeeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$project: { " +
            "\"q_timesheet\": 0, " +
            "\"q_timesheet_row\": 0 " +
            "} }",
    )
    fun findByTenantIdAndJobCode(
        tenantId: String,
        jobcode: String,
        startDate: String,
        endDate: String,
    ): Flux<TimesheetRowWatchDto>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, timesheetId: :#{#timesheetId}}, }",
        "{\$lookup: " +
            "{ from: \"q_jobcode\", " +
            "localField: \"jobCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"jobCodeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$jobCodeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_costcode\", " +
            "localField: \"costCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"costCodeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$costCodeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_project\", " +
            "localField: \"jobCodeInfo.projectId\", " +
            "foreignField: \"_id\", " +
            "as: \"projectInfo\"" +
            "} } ",
        "{\$unwind: { path: \$projectInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_timesheet\", " +
            "localField: \"timesheetId\", " +
            "foreignField: \"_id\", " +
            "as: \"timesheetInfo\"" +
            "} } ",
        "{\$unwind: { path: \$timesheetInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"timesheetInfo.userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{ \$sort: { 'jobCodeInfo.code': 1 } }",
    )
    fun findByTenantIdAndTimesheetIdWithJobCodeAndCostCodeDetailsExportToExcel(
        tenantId: String,
        timesheetId: String,
    ): Flux<TimesheetRow>

    @Aggregation(
        "{ \$match: { " +
            "\"tenantId\": :#{#tenantId}, " +
            "\"jobCodeId\": :#{#jobcode} " +
            "} }",
        "{ \$project: { " +
            "\"q_timesheet_row\": \"$\$ROOT\", " +
            "\"weeklyDetails\": { " +
            "\"\$filter\": { " +
            "\"input\": \"\$weeklyDetails\", " +
            "\"as\": \"dayDetail\", " +
            "\"cond\": { " +
            "\"\$let\": { " +
            "\"vars\": { " +
            "\"dayDate\": \"$\$dayDetail.day\", " +
            "\"startDate\": { \"\$ifNull\": [\":#{#startDate}\", \"\"] }, " +
            "\"endDate\": { \"\$ifNull\": [\":#{#endDate}\", \"\"] } " +
            "}, " +
            "\"in\": { " +
            "\"\$or\": [ " +
            "{ \"\$and\": [ { \"\$ne\": [\"$\$startDate\", \"\"] }, { \"\$eq\": [\"$\$endDate\", \"\"] }, { \"\$eq\": [\"$\$dayDate\", \"$\$startDate\"] } ] }, " +
            "{ \"\$and\": [ { \"\$eq\": [\"$\$startDate\", \"\"] }, { \"\$ne\": [\"$\$endDate\", \"\"] }, { \"\$eq\": [\"$\$dayDate\", \"$\$endDate\"] } ] }, " +
            "{ \"\$and\": [ " +
            "{ \"\$ne\": [\"$\$startDate\", \"\"] }, " +
            "{ \"\$ne\": [\"$\$endDate\", \"\"] }, " +
            "{ \"\$gte\": [ " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$dayDate\", \"format\": \"%m/%d/%Y\" } }, " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$startDate\", \"format\": \"%m/%d/%Y\" } } " +
            "] }, " +
            "{ \"\$lte\": [ " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$dayDate\", \"format\": \"%m/%d/%Y\" } }, " +
            "{ \"\$dateFromString\": { \"dateString\": \"$\$endDate\", \"format\": \"%m/%d/%Y\" } } " +
            "] } " +
            "] } " +
            "] " +
            "} " +
            "} " +
            "} " +
            "} " +
            "} " +
            "} } ",
        "{ \$match: { " +
            "\"weeklyDetails\": { \"\$ne\": [] } " +
            "} }",
        "{ \$lookup: " +
            "{ from: \"q_timesheet\", " +
            "localField: \"q_timesheet_row.timesheetId\", " +
            "foreignField: \"_id\", " +
            "as: \"q_timesheet\"" +
            "} } ",
        "{ \$unwind: { " +
            "path: \"\$q_timesheet\", " +
            "preserveNullAndEmptyArrays: false " +
            "} }",
        "{ \$replaceRoot: { newRoot: { \$mergeObjects: [\"\$q_timesheet\", \"\$q_timesheet_row\", \"\$\$ROOT\"]} }}",
        "{ \$lookup: " +
            "{ from: \"q_jobcode\", " +
            "localField: \"jobCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"jobCodeInfo\"" +
            "} } ",
        "{ \$unwind: { " +
            "path: \"\$jobCodeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: " +
            "{ from: \"q_costcode\", " +
            "localField: \"costCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"costCodeInfo\"" +
            "} } ",
        "{ \$unwind: { " +
            "path: \"\$costCodeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{ \$unwind: { " +
            "path: \"\$employeeInfo\", " +
            "preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$project: {\"q_timesheet\": 0, \"q_timesheet_row\": 0}}",
    )
    fun findByTenantIdAndJobCodeAndWeekEndingPeriodAndExportToExcel(
        tenantId: String,
        jobcode: String,
        startDate: String,
        endDate: String,
    ): Flux<TimesheetRowWatchDto>
}
