package com.syc.dashboard.query.employee.repository.reactive

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.query.employee.dto.EmployeeVacationHoursAndDaysDto
import com.syc.dashboard.query.employee.entity.Employee
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EmployeeReactiveRepository : ReactiveMongoRepository<Employee, String> {

    fun findFirstByTenantIdAndEmail(tenantId: String, email: String): Mono<Employee>

    fun findByTenantIdAndId(tenantId: String, id: String): Mono<Employee>

    fun findByTenantIdAndFirstNameContainsIgnoreCaseAndStatusInAndRoleIn(
        tenantId: String,
        firstName: String,
        status: List<EmployeeStatusEnum>,
        roles: List<UserRole>,
        pageable: Pageable,
    ): Flux<Employee>

    fun findByTenantIdAndManagerIdOrderByFirstName(
        tenantId: String,
        managerId: String,
    ): Flux<Employee>

    fun findByTenantIdAndStatus(
        tenantId: String,
        status: EmployeeStatusEnum,
        pageable: Pageable,
    ): Flux<Employee>

    fun findByTenantIdAndManagerIdAndStatus(
        tenantId: String,
        managerId: String,
        status: EmployeeStatusEnum,
    ): Flux<Employee>

    fun findByTenantIdAndFirstNameContainsIgnoreCaseAndRoleInOrderByFirstName(
        tenantId: String,
        firstName: String,
        roles: List<UserRole> = listOf(UserRole.MANAGER),
    ): Flux<Employee>

    fun findByTenantIdAndStatusInAndFirstNameContainsIgnoreCaseAndRoleInOrderByFirstName(
        tenantId: String,
        status: List<EmployeeStatusEnum> = listOf(EmployeeStatusEnum.ACTIVE),
        firstName: String,
        roles: List<UserRole> = listOf(UserRole.MANAGER),
    ): Flux<Employee>

    fun findByTenantIdAndStatusInOrderByFirstNameAsc(
        tenantId: String,
        status: List<EmployeeStatusEnum> = listOf(EmployeeStatusEnum.ACTIVE),
    ): Flux<Employee>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, id: :#{#id}} }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"managerId\", " +
            "foreignField: \"_id\", " +
            "as: \"managerInfo\"" +
            "} } ",
        "{\$unwind: { path: \$managerInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndIdWithMangerInfo(
        tenantId: String,
        id: String,
    ): Mono<Employee>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'role': {\$in: :#{#role} }}, " +
            " ] } ",
        count = true,
    )
    fun employeeCountByTenantIdAndRoleAndStatus(
        tenantId: String,
        role: List<UserRole>,
        status: List<EmployeeStatusEnum>,
    ): Mono<Long>

    fun findAllByTenantIdAndRoleInOrderByFirstName(
        tenantId: String,
        role: List<UserRole>,
    ): Flux<Employee>

    fun findAllByTenantIdAndRoleInAndStatusInOrderByFirstName(
        tenantId: String,
        role: List<UserRole>,
        status: List<EmployeeStatusEnum>,
    ): Flux<Employee>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'supervisorList': {\$in: :#{#supervisorList} }}, " +
            " ] } ",
    )
    fun findByTenantIdAndSupervisorListOrderByFirstName(
        tenantId: String,
        supervisorList: List<String>,
    ): Flux<Employee>

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, id: :#{#id}} }",
        "{\$lookup: " +
            "{ from: \"q_timesheet\", " +
            "localField: \"_id\", " +
            "foreignField: \"userId\", " +
            "as: \"timesheetInfo\"" +
            "} } ",
        "{\$unwind: { path: \"\$timesheetInfo\"," + "preserveNullAndEmptyArrays: true" + " }}",
        "{\$lookup: " +
            "{ from: \"q_timesheet_row\", " +
            "localField: \"timesheetInfo._id\", " +
            "foreignField: \"timesheetId\", " +
            "as: \"timesheetRowInfo\"" +
            "} } ",
        "{\$unwind: { path: \"\$timesheetRowInfo\"," + "preserveNullAndEmptyArrays: true" + " }}",
        "{\$lookup: " +
            "{ from: \"q_jobcode\", " +
            "localField: \"timesheetRowInfo.jobCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"jobCodeInfo\"" +
            "} } ",
        "{\$unwind: { path: \"\$jobCodeInfo\"," + "preserveNullAndEmptyArrays: true" + "}}",
        "{ \$lookup: { " +
            "from: \"q_costcode\", " +
            "localField: \"timesheetRowInfo.costCodeId\", " +
            "foreignField: \"_id\", " +
            "as: \"costCodeInfo\" " +
            "} }",
        "{ \$unwind: { " + "path: \"\$costCodeInfo\", " + "preserveNullAndEmptyArrays: true " + "} }",
        "{ \$match: { \$or: [ " +
            "{ 'jobCodeInfo.code': '700000', 'costCodeInfo.code': { \$in: ['FLHODY', 'HOLIDY', 'PERSTM', 'VACATN'] } }, " +
            "{ 'jobCodeInfo.code': '800000', 'costCodeInfo.code': { \$in: ['ADMIN', 'TRAING'] } }, " +
            "{ 'jobCodeInfo.code': '900001', 'costCodeInfo.code': { \$in: ['OGPROP', 'MRKTNG', 'MES-EOI1-24-1-03-7', 'KB-4903', 'BCS2024-02', 'BCS2023-14'] } } " +
            "] } }",
        "{ \$unwind: {" + "path: \"\$timesheetRowInfo.weeklyDetails\"," + "preserveNullAndEmptyArrays: true" + "} }",
        "{ \$addFields: { " +
            "'date': '\$timesheetRowInfo.weeklyDetails.day', " +
            "'year': { \$year: { \$dateFromString: { dateString: '\$timesheetRowInfo.weeklyDetails.day' } } }, " +
            "'vacationType': '\$costCodeInfo.code', " +
            "'description': '\$costCodeInfo.description', " +
            "'numberOfHours': '\$timesheetRowInfo.weeklyDetails.numberOfHours' " +
            "} }",
        "{ \$match: { numberOfHours: { \$gt: 0 } } }",
        "{ \$project: { " +
            "_id: 0, " +
            "date: '\$timesheetRowInfo.weeklyDetails.day', " +
            "numberOfHours: '\$timesheetRowInfo.weeklyDetails.numberOfHours', " +
            "costCodeDescription: '\$costCodeInfo.description', " +
            "vacationType: '\$costCodeInfo.code', " +
            "jobCode: '\$jobCodeInfo.code', " +
            "jobCodeDescription: '\$jobCodeInfo.description', " +
            "year: { \$year: { \$toDate: '\$timesheetRowInfo.weeklyDetails.day' } } " +
            "} }",
        "{\$facet: { " +
            "vacationDetails: [ { \$sort: { year: 1, date: 1 } } ], " +
            "totalVacationCount: [ " +
            "{ \$group: { _id: '\$year', totalHours: { \$sum: '\$numberOfHours' }, totalDays: { \$sum: { \$divide: ['\$numberOfHours', 8] } } } }, " +
            "{ \$project: { _id: 0, year: '\$_id', totalHours: 1, totalDays: 1 } }," +
            "{ \$sort: { _id: 1 } } " +
            "] " +
            "} }",
    )
    fun findEmployeeVacationsByTenantIdAndId(tenantId: String, id: String): Mono<EmployeeVacationHoursAndDaysDto>
}
