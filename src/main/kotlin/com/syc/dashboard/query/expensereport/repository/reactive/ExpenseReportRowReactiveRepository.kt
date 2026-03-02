package com.syc.dashboard.query.expensereport.repository.reactive

import com.syc.dashboard.query.expensereport.dto.ExpenseReportSearchRowDto
import com.syc.dashboard.query.expensereport.entity.ExpenseReportRow
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseByEnum
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ExpenseReportRowReactiveRepository : ReactiveMongoRepository<ExpenseReportRow, String> {
    @Aggregation(
        "{\$project: {" +
            "expenseDate_toDate: {" +
            "\$cond: {" +
            "if: {" +
            "\$and: [" +
            "{\$ne: [\"\$expenseDate\", null]}, " +
            "{\$ne: [\"\$expenseDate\", '']}" +
            "]" +
            "}, " +
            "then: {" +
            "\$dateFromString: {" +
            "dateString: \"\$expenseDate\"" +
            "}" +
            "}, " +
            "else: null" +
            "}" +
            "}," +
            "id: 1, tenantId: 1, expenseReportId: 1, expenseTypeId: 1, expenseTypeInfo: 1, " +
            "expenseAmount: 1, expenseDescription: 1, jobCodeId: 1, jobCodeInfo: 1, " +
            "costCodeId: 1, costCodeInfo: 1, expenseMileage: 1, expenseMileageRate: 1," +
            "expenseDate: 1, expenseBy: 1, expenseReportRowStatus: 1, receiptNumber: 1, " +
            "receiptDocumentId: 1, receiptDocumentInfo: 1," +
            "}}",
        "{\$match: " +
            "{tenantId: :#{#tenantId}, expenseReportId: :#{#expenseReportId}, expenseBy: :#{#expenseBy}}, }",
        "{\$lookup: " +
            "{ from: \"q_settings_expense_type\", " +
            "localField: \"expenseTypeId\", " +
            "foreignField: \"_id\", " +
            "as: \"expenseTypeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$expenseTypeInfo, preserveNullAndEmptyArrays: true }}",
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
            "{ from: \"q_document\", " +
            "localField: \"receiptDocumentId\", " +
            "foreignField: \"_id\", " +
            "as: \"receiptDocumentInfo\"" +
            "} } ",
        "{\$unwind: { path: \$receiptDocumentInfo, preserveNullAndEmptyArrays: true }}",
        "{\$sort: {" +
            "expenseDate_toDate: 1" +
            "}}",
    )
    fun findByTenantIdAndExpenseReportIdAndExpenseBy(
        tenantId: String,
        expenseReportId: String,
        expenseBy: ExpenseByEnum,
    ): Flux<ExpenseReportRow>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, jobCodeId: :#{#jobCodeId}}}",
        "{\$lookup: " +
            "{ from: \"q_expense_report\", " +
            "localField: \"expenseReportId\", " +
            "foreignField: \"_id\", " +
            "as: \"expenseReportInfo\"" +
            "} } ",
        "{\$unwind: { path: \$expenseReportInfo, preserveNullAndEmptyArrays: true }}",
        "{\$match: {\"expenseReportInfo.periodFrom\": :#{#periodFrom} }  }",
        "{\$lookup: " +
            "{ from: \"q_jobcode\", " +
            "localField: \"jobCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"jobCodeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$jobCodeInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndJobCodeIdAndPeriodFrom(
        tenantId: String,
        jobCodeId: String,
        periodFrom: String,
    ): Flux<ExpenseReportRow>

    @Aggregation(
        "{\$project: {\"q_expense_report_row\": \$\$ROOT, \"_id\": 0}} ",
        "{\$lookup: { " +
            "localField: \"q_expense_report_row.expenseReportId\", " +
            "from: \"q_expense_report\", " +
            "foreignField: \"_id\", " +
            "as: \"q_expense_report\" " +
            "}} ",
        "{\$unwind: {path: \"\$q_expense_report\", preserveNullAndEmptyArrays: true}}",
        "{\$match: " +
            "{\$expr: {\$and: [{\$and: [{\$and: [" +
            "{\$eq: [\"\$q_expense_report_row.tenantId\", \"\$q_expense_report.tenantId\"]}, " +
            "{\$eq: [\"\$q_expense_report.periodFrom\", :#{#periodFrom} ]}]}, " +
            "{\$eq: [\"\$q_expense_report_row.jobCodeId\", :#{#jobCodeId} ]}]}, " +
            "{\$eq: [\"\$q_expense_report.tenantId\", :#{#tenantId} ]}]}}}",
        "{\$replaceRoot: { " +
            "newRoot: {\$mergeObjects: [\"\$q_expense_report\", \"\$q_expense_report_row\", \"\$\$ROOT\"]}}}",
        "{\$project: {\"q_expense_report\": 0, \"q_expense_report_row\": 0}}",
        "{\"\$lookup\": {" +
            "\"localField\": \"jobCodeId\"," +
            "\"from\": \"q_jobcode\"," +
            "\"foreignField\": \"_id\"," +
            "\"as\": \"jobCodeInfo\"" +
            "}}",
        "{\"\$unwind\": {\"path\": \"\$jobCodeInfo\",\"preserveNullAndEmptyArrays\": true}}",
        "{\"\$lookup\": {" +
            "\"localField\": \"costCodeId\"," +
            "\"from\": \"q_costcode\"," +
            "\"foreignField\": \"_id\"," +
            "\"as\": \"costCodeInfo\"" +
            "}}",
        "{\"\$unwind\": {\"path\": \"\$costCodeInfo\",\"preserveNullAndEmptyArrays\": true}}",
        "{\"\$lookup\": {" +
            "\"localField\": \"employeeId\"," +
            "\"from\": \"q_employee\"," +
            "\"foreignField\": \"_id\"," +
            "\"as\": \"employeeInfo\"" +
            "}}",
        "{\"\$unwind\": {\"path\": \"\$employeeInfo\",\"preserveNullAndEmptyArrays\": true}}",
        "{\"\$lookup\": {" +
            "\"localField\": \"expenseTypeId\"," +
            "\"from\": \"q_settings_expense_type\"," +
            "\"foreignField\": \"_id\"," +
            "\"as\": \"expenseTypeInfo\"" +
            "}}",
        "{\"\$unwind\": {\"path\": \"\$expenseTypeInfo\",\"preserveNullAndEmptyArrays\": true}}",
        "{\$lookup: " +
            "{ from: \"q_document\", " +
            "localField: \"receiptDocumentId\", " +
            "foreignField: \"_id\", " +
            "as: \"receiptDocumentInfo\"" +
            "} } ",
        "{\$unwind: { path: \$receiptDocumentInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findAllByTenantIdAndJobCodeIdAndPeriodFromAndExpenseReportStatusIn(
        tenantId: String,
        jobCodeId: String,
        periodFrom: String,
    ): Flux<ExpenseReportSearchRowDto>

    @Aggregation(
        "{\$lookup: {" +
            "from: 'q_expense_report'," +
            "localField: 'expenseReportId'," +
            "foreignField: '_id'," +
            "as: 'expenseReportInfo'" +
            "}}",
        "{\$unwind: '\$expenseReportInfo'}",
        "{\$lookup: {" +
            "from: 'q_jobcode'," +
            "localField: 'jobCodeId'," +
            "foreignField: '_id'," +
            "as: 'jobCodeInfo'" +
            "}}",
        "{\$unwind: '\$jobCodeInfo'}",
        "{\$match: {" +
            "'expenseReportInfo.periodFrom': :#{#periodFrom}," +
            "tenantId: :#{#tenantId}" +
            "}}",
        "{\$group: {" +
            "_id: '\$jobCodeInfo._id'," +
            "code: { \$first: '\$jobCodeInfo.code' }," +
            "createdBy: { \$first: '\$jobCodeInfo.createdBy' }," +
            "createdOn: { \$first: '\$jobCodeInfo.createdOn' }," +
            "costCodeList: { \$first: '\$jobCodeInfo.costCodeList' }," +
            "status: { \$first: '\$jobCodeInfo.status' }," +
            "description: { \$first: '\$jobCodeInfo.description' }," +
            "tenantId: { \$first: '\$jobCodeInfo.tenantId' }," +
            "remoteAddress: { \$first: '\$jobCodeInfo.remoteAddress' }," +
            "remoteHostName: { \$first: '\$jobCodeInfo.remoteHostName' }," +
            "triggeredBy: { \$first: '\$jobCodeInfo.triggeredBy' }," +
            "_class: { \$first: '\$jobCodeInfo._class' }" +
            "}}",
    )
    fun findAllByTenantIdAndPeriodFrom(
        tenantId: String,
        periodFrom: String,
    ): Flux<JobCodeDto>
}
