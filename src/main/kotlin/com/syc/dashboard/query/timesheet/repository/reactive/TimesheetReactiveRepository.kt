package com.syc.dashboard.query.timesheet.repository.reactive

import com.syc.dashboard.query.timesheet.dto.TimesheetRowExportToQuickBookDto
import com.syc.dashboard.query.timesheet.entity.Timesheet
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface TimesheetReactiveRepository : ReactiveMongoRepository<Timesheet, String> {

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, id: :#{#id}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndIdWithUserDetails(tenantId: String, id: String): Mono<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, createdOn: 1, updatedOn: 1" +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId}, status: {\$in: :#{#status}}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndStatus(
        tenantId: String,
        status: List<TimesheetStatusEnum> = listOf(TimesheetStatusEnum.IN_PROGRESS),
        pageable: Pageable,
    ): Flux<Timesheet>

    fun findByTenantIdAndStatusIn(
        tenantId: String,
        status: List<TimesheetStatusEnum> = listOf(TimesheetStatusEnum.IN_PROGRESS),
    ): Flux<Timesheet>

    @Aggregation(
        "{\$match: " +
            "{ tenantId: :#{#tenantId}, status: {\$in: :#{#status}}, " +
            "\$expr: " +
            "{\$lte: [ " +
            "{\$dateFromString: { dateString: '\$weekEndingDate', format: '%m/%d/%Y' } }, " +
            "{\$dateFromString: { dateString: :#{#currentDate}, format: '%m/%d/%Y' } } " +
            "]" +
            "}" +
            "}" +
            "}",
    )
    fun findByTenantIdAndStatusInAndBeforeCurrentDate(
        tenantId: String,
        status: List<TimesheetStatusEnum> = listOf(TimesheetStatusEnum.IN_PROGRESS),
        currentDate: String,
    ): Flux<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1" +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId},  status: :#{#status}, userId: :#{#userId}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndUserIdAndStatus(
        tenantId: String,
        userId: String,
        status: TimesheetStatusEnum,
        pageable: Pageable,
    ): Flux<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, updatedOn:  1, createdOn: 1 " +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId}, status: {\$in: :#{#status}}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findSubmittedForApprovalForAdmin(
        tenantId: String,
        status: List<TimesheetStatusEnum>,
        pageable: Pageable,
    ): Flux<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, createdOn: 1, updatedOn: 1" +
            "} " +
            "}",
        "{" +
            "\$lookup: {" +
            "from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$match: " +
            "{" +
            "tenantId: :#{#tenantId}, " +
            "status: {\$in: :#{#status}}, " +
            "\$or: [{'employeeInfo.managerId': :#{#managerId}}, {\"approvedByUserId\": :#{#managerId}}], " +
            "} } ",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndManagerIdAndStatus(
        tenantId: String,
        managerId: String,
        status: List<TimesheetStatusEnum>,
        pageable: Pageable,
    ): Flux<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1" +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId},  status: {\$in: :#{#status}}, userId: :#{#userId}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: \$employeeInfo}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndUserIdAndStatusIn(
        tenantId: String,
        userId: String,
        status: List<TimesheetStatusEnum>,
        pageable: Pageable,
    ): Flux<Timesheet>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId},  status: {\$in: :#{#status}}, userId: :#{#userId}, weekEndingDate: :#{#weekEndingDate}}}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndUserIdAndStatusInAndWeekEndingDate(
        tenantId: String,
        userId: String,
        status: List<TimesheetStatusEnum>,
        weekEndingDate: String,
    ): Mono<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, createdOn: 1, updatedOn: 1 " +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId},  status: {\$in: :#{#status}}, userId: :#{#userId}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndUserIdAndFilterData(
        tenantId: String,
        userId: String,
        status: List<TimesheetStatusEnum>,
        pageable: Pageable,
    ): Flux<Timesheet>

    @Aggregation(
        "{" +
            "\$lookup: {" +
            "from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "}" +
            "}",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$match: {tenantId: :#{#tenantId}, status: {\$in: :#{#status}}, 'employeeInfo.managerId': :#{#managerId},weekEndingDate: :#{#weekEndingDate}}}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndUserIdAndManagerIdAndStatusAndWeekEndingDate(
        tenantId: String,
        userId: String,
        managerId: String,
        status: List<TimesheetStatusEnum>,
        weekEndingDate: String,
    ): Mono<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, createdOn: 1, updatedOn: 1 " +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId},  status: {\$in: :#{#status}}, userId: :#{#userId}} }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndStatusAndUserId(
        tenantId: String,
        status: List<TimesheetStatusEnum>,
        userId: String,
        pageable: Pageable,
    ): Flux<Timesheet>

    fun findByTenantIdAndWeekEndingDateAndStatusIn(
        tenantId: String,
        weekEndingDate: String,
        status: List<TimesheetStatusEnum> = listOf(TimesheetStatusEnum.IN_PROGRESS),
    ): Flux<Timesheet>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'userId': {\$eq: :#{#userId} }} " +
            " ] } ",
        count = true,
    )
    fun countByTenantIdAndUserIdAndStatus(
        tenantId: String,
        userId: String,
        status: List<TimesheetStatusEnum>,
    ): Mono<Long>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'approvedByUserId': {\$eq: :#{#approvedByUserId} }} " +
            " ] } ",
        count = true,
    )
    fun timesheetCountByTenantIdAndApprovedByUserIdAndStatus(
        tenantId: String,
        approvedByUserId: String,
        status: List<TimesheetStatusEnum>,
    ): Mono<Long>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " ] } ",
        count = true,
    )
    fun timesheetCountByTenantIdAndStatus(
        tenantId: String,
        status: List<TimesheetStatusEnum>,
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
            "                from: \"q_timesheet_row\",\n" +
            "                localField: \"_id\",\n" +
            "                foreignField: \"timesheetId\",\n" +
            "                as: \"timesheetRowInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$timesheetRowInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_employee\",\n" +
            "                localField: \"userId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"employeeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$employeeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_employee\",\n" +
            "                localField: \"approvedByUserId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"approvedByInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$approvedByInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_costcode\",\n" +
            "                localField: \"timesheetRowInfo.costCodeId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"timesheetRowInfo.costCodeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$timesheetRowInfo.costCodeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        \$lookup:\n" +
            "            {\n" +
            "                from: \"q_jobcode\",\n" +
            "                localField: \"timesheetRowInfo.jobCodeId\",\n" +
            "                foreignField: \"_id\",\n" +
            "                as: \"timesheetRowInfo.jobCodeInfo\"\n" +
            "            }\n" +
            "    }",
        "    {\$unwind: { path: '\$timesheetRowInfo.jobCodeInfo', preserveNullAndEmptyArrays: true }}",
        "    {\n" +
            "        '\$group':\n" +
            "            {\n" +
            "                '_id': '\$_id',\n" +
            "                'tenantId': {'\$first': '\$tenantId'},\n" +
            "                'timesheetRows': {'\$addToSet': '\$timesheetRowInfo'},\n" +
            "                'status': {'\$first': '\$status'},\n" +
            "                'commentsByAdmin': {'\$first': '\$commentsByAdmin'},\n" +
            "                'commentsByEmployee': {'\$first': '\$commentsByEmployee'},\n" +
            "                'commentsByManager': {'\$first': '\$commentsByManager'},\n" +
            "                'submittedByName': {'\$first': '\$submittedByName'},\n" +
            "                'userId': {'\$first': '\$userId'},\n" +
            "                'employeeInfo': {'\$first': '\$employeeInfo'},\n" +
            "                'approvedByUserId': {'\$first': '\$approvedByUserId'},\n" +
            "                'approvedByInfo': {'\$first': '\$approvedByInfo'},\n" +
            "                'weekEndingDate': {'\$first': '\$weekEndingDate'},\n" +
            "                'weekStartingDate': {'\$first': '\$weekStartingDate'},\n" +
            "                'createdOn': {'\$first': '\$createdOn'},\n" +
            "                'updatedOn': {'\$first': '\$updatedOn'},\n" +
            "            }\n" +
            "    }",
        "    {\n" +
            "     '\$set': {\n" +
            "            'timesheetRows': {\n" +
            "                '\$sortArray': { 'input': '\$timesheetRows', 'sortBy': { 'jobCodeInfo.code': 1 } }\n" +
            "            }\n" +
            "        }\n" +
            "    }",
    )
    fun findByTenantIdAndIdWithAllDetails(
        tenantId: String,
        id: String,
    ): Mono<Timesheet>

    fun findByTenantIdAndId(tenantId: String, id: String): Mono<Timesheet>

    @Aggregation(
        "{ \$match: " +
            "{ tenantId: :#{#tenantId}, _id: :#{#timesheetId} " +
            "} }",
        "{ \$lookup: " +
            "{ from: 'q_employee'," +
            " localField: 'userId'," +
            " foreignField: '_id'," +
            " as: 'employeeInfo' " +
            "} }",
        "{ \$unwind: {" +
            " path: '\$employeeInfo'," +
            " preserveNullAndEmptyArrays: true" +
            " } }",
        "{ \$lookup: { " +
            "from: 'q_timesheet_row'," +
            " localField: '_id'," +
            " foreignField: 'timesheetId'," +
            " as: 'timesheetRowInfo'" +
            " } }",
        "{ \$lookup: {" +
            " from: 'q_jobcode'," +
            " localField: 'timeSheetRowInfo.jobCodeId'," +
            " foreignField: '_id'," +
            " as: 'jobCodeInfo'" +
            " } }",
        "{ \$unwind: {" +
            " path: '\$jobCodeInfo'," +
            " preserveNullAndEmptyArrays: true" +
            " } }",
        "{ \$lookup: {" +
            " from: 'q_costcode'," +
            " localField: 'timeSheetRowInfo.costCodeId'," +
            " foreignField: '_id', as: 'costCodeInfo'" +
            " } }",
        "{ \$unwind: {" +
            " path: '\$costCodeInfo'," +
            " preserveNullAndEmptyArrays: true " +
            "} }",
        "{ \$lookup: { " +
            "from: 'q_project'," +
            " localField: 'jobCodeInfo.projectId'," +
            " foreignField: '_id'," +
            " as: 'projectInfo' " +
            "} }",
        "{ \$unwind: { path: '\$projectInfo', preserveNullAndEmptyArrays: true } }",
    )
    fun findTimesheetRowByIdExportToQuickBookQuery(
        tenantId: String,
        timesheetId: String,
    ): Flux<TimesheetRowExportToQuickBookDto>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, createdOn: 1, updatedOn: 1" +
            "} " +
            "}",
        "{" +
            "\$lookup: {" +
            "from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{\$match: " +
            "{" +
            "tenantId: :#{#tenantId}, " +
            "status: {\$in: :#{#status}}, " +
            "\$or: [{'employeeInfo.managerId': :#{#managerId}}, {\"approvedByUserId\": :#{#managerId}}], " +
            "} } ",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
        "{ \$addFields: { " +
            "weekDate: { " +
            "\$cond: { " +
            "if: { \$eq: [ { \$type: '\$weekEndingDate' }, 'string' ] }, " +
            "then: { \$dateFromString: { dateString: '\$weekEndingDate' } }, " +
            "else: '\$weekEndingDate' " +
            "} " +
            "} " +
            "} }",
        "{ \$match: { " +
            "\$expr: { " +
            "\$cond: { " +
            "if: { \$and: [ { \$ne: [:#{#weekStartingDate}, null] }, { \$ne: [:#{#weekEndingDate}, null] } ] }, " +
            "then: { \$and: [ " +
            "{ \$gte: [ '\$weekDate', { \$dateFromString: { dateString: :#{#weekStartingDate} } } ] }, " +
            "{ \$lte: [ '\$weekDate', { \$dateFromString: { dateString: :#{#weekEndingDate} } } ] } " +
            "] }, " +
            "else: true " +
            "} " +
            "} " +
            "} }",
    )
    fun findAllByTenantIdAndManagerIdAndStatusAndWeekStartingDateAndWeekEndingDate(
        tenantId: String,
        managerId: String,
        status: List<TimesheetStatusEnum>,
        weekStartingDate: String,
        weekEndingDate: String,
        pageable: Pageable,
    ): Flux<Timesheet>

    @Aggregation(
        "{\$project: { " +
            "weekEnding_toDate: {" +
            "\$dateFromString: {" +
            "dateString: \"\$weekEndingDate\" " +
            "}" +
            "}," +
            "id: 1, tenantId: 1, status: 1, userId: 1, employeeInfo: 1, approvedByUserId: 1, approvedByInfo: 1 , weekEndingDate: 1, weekStartingDate: 1,commentsByAdmin: 1,commentsByEmployee: 1,commentsByManager: 1, submittedByName: 1, updatedOn:  1, createdOn: 1 " +
            "} " +
            "}",
        "{\$match: " +
            "{tenantId: :#{#tenantId}, status: {\$in: :#{#status}}}, }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
        "{ \$match: { " +
            "\$expr: { " +
            "\$or: [ " +
            "{ \$eq: [:#{#employeeId}, ''] }, " +
            "{ \$eq: [:#{#employeeId}, null] }, " +
            "{ \$eq: ['\$employeeInfo._id', :#{#employeeId}] } " +
            "] " +
            "} " +
            "} }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"approvedByUserId\", " +
            "foreignField: \"_id\", " +
            "as: \"approvedByInfo\"" +
            "} } ",
        "{\$unwind: { path: \$approvedByInfo, preserveNullAndEmptyArrays: true }}",
        "{ \$addFields: { " +
            "weekDate: { " +
            "\$cond: { " +
            "if: { \$eq: [ { \$type: '\$weekEndingDate' }, 'string' ] }, " +
            "then: { \$dateFromString: { dateString: '\$weekEndingDate' } }, " +
            "else: '\$weekEndingDate' " +
            "} " +
            "} " +
            "} }",
        "{ \$match: { " +
            "\$expr: { " +
            "\$cond: { " +
            "if: { \$and: [ { \$ne: [:#{#weekStartingDate}, null] }, { \$ne: [:#{#weekEndingDate}, null] } ] }, " +
            "then: { \$and: [ " +
            "{ \$gte: [ '\$weekDate', { \$dateFromString: { dateString: :#{#weekStartingDate} } } ] }, " +
            "{ \$lte: [ '\$weekDate', { \$dateFromString: { dateString: :#{#weekEndingDate} } } ] } " +
            "] }, " +
            "else: true " +
            "} " +
            "} " +
            "} }",
        "{ \$addFields: { " +
            "fullName: { \$concat: ['\$employeeInfo.firstName', ' ', '\$employeeInfo.lastName'] } } }",
        "{ \$match: { " +
            "fullName: { \$regex: :#{#fullName}, \$options: 'i'}, " +
            "} }",
    )
    fun findSubmittedForApprovalForAdminByFullNameAndWeekStartingDateAndWeekEndingDate(
        tenantId: String,
        status: List<TimesheetStatusEnum>,
        fullName: String,
        employeeId: String,
        weekStartingDate: String,
        weekEndingDate: String,
        pageable: Pageable,
    ): Flux<Timesheet>
}
