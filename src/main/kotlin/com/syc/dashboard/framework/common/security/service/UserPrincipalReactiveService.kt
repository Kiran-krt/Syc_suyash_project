package com.syc.dashboard.framework.common.security.service

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.user.UserPrincipal
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserPrincipalReactiveService @Autowired constructor(
    private val adminReactiveRepository: AdminReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) {
    fun findUserDetailsByEmailAndRole(
        tenantId: String,
        email: String,
        role: UserRole,
    ): Mono<out UserPrincipal> {
        return when (role) {
            UserRole.ADMIN -> adminReactiveRepository.findFirstByTenantIdAndEmail(
                tenantId = tenantId,
                email = email,
            )

            UserRole.EMPLOYEE, UserRole.MANAGER, UserRole.SUPERVISOR -> employeeReactiveRepository.findFirstByTenantIdAndEmail(
                tenantId = tenantId,
                email = email,
            )

            else -> throw Exception("Fix this...")
        }
    }

    fun findUserDetailsById(
        id: String,
        role: UserRole,
    ): Mono<out UserPrincipal> {
        return when (role) {
            UserRole.ADMIN -> adminReactiveRepository.findById(id)

            UserRole.EMPLOYEE, UserRole.MANAGER, UserRole.SUPERVISOR -> employeeReactiveRepository.findById(id)

            else -> throw Exception("Fix this...")
        }
    }
}
