package com.syc.dashboard.query.employee.repository.jpa

import com.syc.dashboard.query.employee.entity.Employee
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : MongoRepository<Employee, String> {
    fun findByTenantIdAndId(tenantId: String, id: String): Employee?

    fun findAllByTenantIdAndStatus(
        tenantId: String,
        status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    ): List<Employee>
}
