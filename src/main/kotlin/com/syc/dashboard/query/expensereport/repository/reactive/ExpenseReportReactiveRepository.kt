package com.syc.dashboard.query.expensereport.repository.reactive

import com.syc.dashboard.query.expensereport.dto.ExpenseReportDto
import com.syc.dashboard.query.expensereport.entity.ExpenseReport
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ExpenseReportReactiveRepository : ReactiveMongoRepository<ExpenseReport, String> {

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, id: :#{#id}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"employeeId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"supervisorId\", " +
            "foreignField: \"_id\", " +
            "as: \"supervisorInfo\"" +
            "} } ",
        "{\$unwind: { path: \$supervisorInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): Mono<ExpenseReport>

    @Aggregation(
        "{\$project: { " +
            "periodFrom_toDate: { " +
            "\$cond: [ " +
            "{ \$or: [ " +
            "{ \$eq: [\"\$periodFrom\", null] }, " +
            "{ \$eq: [\"\$periodFrom\", \"\"] } " +
            "] }, " +
            "null, " +
            "{ \$dateFromString: { dateString: \"\$periodFrom\" } } " +
            "] " +
            "}," +
            "id: 1, tenantId: 1, status: 1, employeeId: 1, employeeInfo: 1, supervisorId: 1, supervisorInfo: 1, periodFrom: 1, periodTo: 1, description: 1, commentsByAdmin: 1," +
            "commentsByEmployee: 1, commentsBySupervisor: 1, adminSignature: 1, employeeSignature: 1, supervisorSignature: 1, createdOn: 1, updatedOn: 1" +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId}, status: {\$in: :#{#status}}, description: {\$regex: :#{#description}, \$options: 'i'},employeeId: {\$regex: :#{#employeeId}, \$options: 'i'}, supervisorId: {\$regex: :#{#supervisorId}, \$options: 'i'}, periodFrom_toDate: { \$ne: null }}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"employeeId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"supervisorId\", " +
            "foreignField: \"_id\", " +
            "as: \"supervisorInfo\"" +
            "} } ",
        "{\$unwind: { path: \$supervisorInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndStatusAndEmployeeIdAndSupervisorIdAndDescription(
        tenantId: String,
        status: List<ExpenseReportStatusEnum> = listOf(ExpenseReportStatusEnum.IN_PROGRESS),
        employeeId: String = "",
        supervisorId: String = "",
        description: String = "",
        pageable: Pageable,
    ): Flux<ExpenseReport>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " ] } ",
        count = true,
    )
    fun expenseReportCountByTenantIdAndStatus(
        tenantId: String,
        status: List<ExpenseReportStatusEnum>,
    ): Mono<Long>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'employeeId': {\$eq: :#{#employeeId} }} " +
            " ] } ",
        count = true,
    )
    fun countByTenantIdAndEmployeeIdAndStatus(
        tenantId: String,
        employeeId: String,
        status: List<ExpenseReportStatusEnum>,
    ): Mono<Long>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'supervisorId': {\$eq: :#{#supervisorId} }} " +
            " ] } ",
        count = true,
    )
    fun expenseReportCountByTenantIdAndSupervisorIdAndStatus(
        tenantId: String,
        supervisorId: String,
        status: List<ExpenseReportStatusEnum>,
    ): Mono<Long>

    @Aggregation(
        "{\n" +
            "        \$match: {\n" +
            "            tenantId: :#{#tenantId} ,\n" +
            "            _id: :#{#id} ,\n" +
            "        }\n" +
            "    }\n",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_expense_report_row\",\n" +
            "                localField: \"_id\",\n" +
            "                foreignField: \"expenseReportId\",\n" +
            "                as: \"expenseReportRowInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$expenseReportRowInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_employee\",\n" +
            "                localField: \"employeeId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"employeeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$employeeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_employee\",\n" +
            "                localField: \"supervisorId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"supervisorInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$supervisorInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_settings_expense_type\",\n" +
            "                localField: \"expenseReportRowInfo.expenseTypeId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"expenseReportRowInfo.expenseTypeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$expenseReportRowInfo.expenseTypeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_costcode\",\n" +
            "                localField: \"expenseReportRowInfo.costCodeId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"expenseReportRowInfo.costCodeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$expenseReportRowInfo.costCodeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_jobcode\",\n" +
            "                localField: \"expenseReportRowInfo.jobCodeId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"expenseReportRowInfo.jobCodeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$expenseReportRowInfo.jobCodeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_document\",\n" +
            "                localField: \"expenseReportRowInfo.receiptDocumentId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"expenseReportRowInfo.receiptDocumentInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$expenseReportRowInfo.receiptDocumentInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        '\$group':\n" +
            "            {\n" +
            "                '_id': '\$_id',\n" +
            "                'tenantId': {'\$first': '\$tenantId'},\n" +
            "                'expenseReportRowsForEmployee': {\n" +
            "                '\$addToSet': {\n" +
            "                    '\$cond': {\n" +
            "                    'if': {\n" +
            "                    '\$and': [\n" +
            "                        {'\$eq': ['\$expenseReportRowInfo.expenseBy', 'EMPLOYEE']},\n" +
            "                        {'\$ne': ['\$expenseReportRowInfo',null]},\n" +
            "                   ]\n" +
            "               },\n" +
            "                       'then': '\$expenseReportRowInfo',\n" +
            "                        'else': null" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "            'expenseReportRowsForSuyash': {\n" +
            "                '\$addToSet': {\n" +
            "                    '\$cond': {\n" +
            "                    'if': {\n" +
            "                    '\$and': [\n" +
            "                        {'\$eq': ['\$expenseReportRowInfo.expenseBy', 'SUYASH']},\n" +
            "                        {'\$ne': ['\$expenseReportRowInfo',null]},\n" +
            "               ]\n" +
            "            },\n" +
            "                        'then': '\$expenseReportRowInfo',\n" +
            "                        'else': null" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "                'periodFrom': {'\$first': '\$periodFrom'},\n" +
            "                'periodTo': {'\$first': '\$periodTo'},\n" +
            "                'employeeId': {'\$first': '\$employeeId'},\n" +
            "                'employeeInfo': {'\$first': '\$employeeInfo'},\n" +
            "                'supervisorId': {'\$first': '\$supervisorId'},\n" +
            "                'supervisorInfo': {'\$first': '\$supervisorInfo'},\n" +
            "                'description': {'\$first': '\$description'},\n" +
            "                'status': {'\$first': '\$status'},\n" +
            "                'commentsByAdmin': {'\$first': '\$commentsByAdmin'},\n" +
            "                'commentsByEmployee': {'\$first': '\$commentsByEmployee'},\n" +
            "                'commentsBySupervisor': {'\$first': '\$commentsBySupervisor'},\n" +
            "                'adminSignature': {'\$first': '\$adminSignature'},\n" +
            "                'employeeSignature': {'\$first': '\$employeeSignature'},\n" +
            "                'supervisorSignature': {'\$first': '\$supervisorSignature'},\n" +
            "                'createdOn': {'\$first': '\$createdOn'},\n" +
            "                'updatedOn': {'\$first': '\$updatedOn'},\n" +
            "            }\n" +
            "    }",
        "    {\n" +
            "        '\$addFields': {\n" +
            "            'expenseReportRowsForEmployee': {\n" +
            "                '\$filter': {\n" +
            "                    'input': '\$expenseReportRowsForEmployee',\n" +
            "                    'as': 'rowsForEmployee',\n" +
            "                    'cond': {'\$ne': ['\$\$rowsForEmployee', null]}\n" +
            "                }\n" +
            "            },\n" +
            "            'expenseReportRowsForSuyash': {\n" +
            "                '\$filter': {\n" +
            "                    'input': '\$expenseReportRowsForSuyash',\n" +
            "                    'as': 'rowsForSuyash',\n" +
            "                    'cond': {'\$ne': ['\$\$rowsForSuyash', null]}\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }",

    )
    fun findByTenantIdAndIdWithAllDetails(
        tenantId: String,
        id: String,
    ): Mono<ExpenseReport>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " ] } ",
        count = true,
    )
    fun expenseReportCountByTenantIdAndApprovedByUserIdAndStatus(
        tenantId: String,
        status: List<ExpenseReportStatusEnum>,
    ): Mono<Long>

    fun findByTenantId(
        tenantId: String,
    ): Flux<ExpenseReportDto>
}
