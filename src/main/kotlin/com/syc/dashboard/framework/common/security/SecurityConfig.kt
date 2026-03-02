package com.syc.dashboard.framework.common.security

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.jwt.JwtAuthenticationEntryPoint
import com.syc.dashboard.framework.common.security.jwt.JwtAuthenticationFilter
import com.syc.dashboard.framework.common.security.service.CustomAuthenticationManager
import com.syc.dashboard.framework.common.security.service.CustomSecurityContextRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
class SecurityConfig @Autowired constructor(
    private val unauthorizedHandler: JwtAuthenticationEntryPoint,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customSecurityContextRepository: CustomSecurityContextRepository,
    private val customAuthenticationManager: CustomAuthenticationManager,
) {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authenticationManager(customAuthenticationManager)
            .securityContextRepository(customSecurityContextRepository)
            .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .exceptionHandling { it.authenticationEntryPoint(unauthorizedHandler) }
            .authorizeExchange {
                it // common permit all
                    .pathMatchers("/api/v1/logs/**").permitAll()
                    .pathMatchers("/swagger-ui.html/**").permitAll()
                    .pathMatchers("/swagger-ui/**").permitAll()
                    .pathMatchers("/api-docs/**").permitAll()
            }
            .authorizeExchange {
                it // admin
                    .pathMatchers("/api/v1/admin/register").permitAll()
                    .pathMatchers("/api/v1/admin/login").permitAll()
                    .pathMatchers("/api/v1/admin/invalid/logout").permitAll()
                    .pathMatchers("/api/v1/admin/*/logout").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/update/password").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/update/fullname").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/lookup").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/lookup/byemail/**").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/profile/update").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/lookup/allusers/search/all").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/update/email").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/update/status").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/*/update/mobiledeviceinfo").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/admin/forgot/password").permitAll()
            }
            .authorizeExchange {
                it // employee
                    .pathMatchers("/api/v1/employee/register").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/employee/login").permitAll()
                    .pathMatchers("/api/v1/employee/invalid/logout").permitAll()
                    .pathMatchers("/api/v1/employee/*/logout").hasAnyAuthority(*UserRole.ALL_EMPLOYEE())
                    .pathMatchers("/api/v1/employee/lookup/byemail/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/reset/password/byemail/**").permitAll()
                    .pathMatchers("/api/v1/employee/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/employee/*/update/password").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/*/update/role").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/employee/timesheet/*/pageable/lookup/bystatus/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/lookup/formanager/**").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/employee/lookup/formanager/*/bystatus/**").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/employee/search/all/manager/admin").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/*/update/profile").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/lookup/search/all").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name, UserRole.SUPERVISOR.name, UserRole.EMPLOYEE.name)
                    .pathMatchers("/api/v1/employee/*/lookup/withmanager").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/count/search/all").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/employee/*/update/email").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/employee/*/update/status").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/employee/*/update/mobiledeviceinfo").hasAnyAuthority(*UserRole.ALL_EMPLOYEE())
                    .pathMatchers("/api/v1/employee/forgot/password").permitAll()
                    .pathMatchers("/api/v1/employee/lookup/all/supervisor").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/lookup/all/active/supervisor").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/supervisor/*/lookup/all/employee").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/employee/*/vacations/lookup").hasAnyAuthority(*UserRole.ALL_ADMIN())
            }
            .authorizeExchange {
                it
                    // timesheet
                    .pathMatchers("/api/v1/timesheet/register").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/timesheetrow/*/update/day/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/bystatus/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/admin/update/status/and/comments").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/employee/update/status/and/comments").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/manager/update/status/and/comments").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/submittedforapproval/foradmin").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/submittedforapproval/forsupervisor").hasAuthority(UserRole.SUPERVISOR.name)
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/submittedforapproval/formanager/manager/**").hasAuthority(UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/tosubmit/foremployee/employee/**").hasAuthority(UserRole.EMPLOYEE.name)
                    .pathMatchers("/api/v1/timesheet/lookup/search/foremployee/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/search/foremployee/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/lookup/search/formanager/**").hasAnyAuthority(UserRole.MANAGER.name, UserRole.ADMIN.name)
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/rejectedbymanager/foremployee/**").hasAnyAuthority(*UserRole.ALL_EMPLOYEE())
                    .pathMatchers("/api/v1/timesheet/*/update/status").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/register/withstartdate").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/manager/**").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/*/timesheetrow/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/timesheetrow/*/delete").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/*/update/withtimesheetrows").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/approved/foremployee/**").hasAnyAuthority(UserRole.EMPLOYEE.name, UserRole.SUPERVISOR.name)
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/approved/formanager/**").hasAnyAuthority(UserRole.MANAGER.name, UserRole.ADMIN.name)
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/approved/foradmin").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/pageable/lookup/approved/forsupervisor").hasAuthority(UserRole.SUPERVISOR.name)
                    .pathMatchers("/api/v1/timesheet/count/foremployee/*/bystatus").hasAnyAuthority(*UserRole.ALL_EMPLOYEE())
                    .pathMatchers("/api/v1/timesheet/count/formanager/*/bystatus").hasAnyAuthority(UserRole.MANAGER.name, UserRole.ADMIN.name)
                    .pathMatchers("/api/v1/timesheet/count/foradmin/bystatus").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/*/export/toexcel").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/*/update/weekstartingdate").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/*/update/weekendingdate").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/count/forsupervisor/*/bystatus").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/*/export/toquickbook").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/timesheet/timesheetrow/search/all").hasAnyAuthority(UserRole.EMPLOYEE.name, UserRole.SUPERVISOR.name, UserRole.MANAGER.name)
            }
            .authorizeExchange {
                it
                    // jobcode
                    .pathMatchers("/api/v1/jobcode/register").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/jobcode/*/lookup/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/lookup/bycode/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/update/status").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/jobcode/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/add/costcode").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/jobcode/*/costcode/pageable/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/costcode/pageable/lookup/all/active").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/costcode/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/costcode/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/costcode/lookup/all/active").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/costcode/*/update").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/jobcode/*/update/description/and/status").hasAnyAuthority(UserRole.ADMIN.name, UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/jobcode/costcode/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/lookup/all/active").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/*/costcode/lookup/bycode/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/costcode/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/lookup/all/bywatcher/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/jobcode/project/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    // settings
                    .pathMatchers("/api/v1/settings/register").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/update/dateformat").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/update/timezone").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/update/timesheetdelayinhours").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/settings/*/delete").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/update/status").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/mileagerate/*/update").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/add/expensetype").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/expensetype/*/update/allfields").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/expensetype/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/settings/mileagerateinfo/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/settings/*/add/payrollitem").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/payrollitem/lookup/all").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/payrollitem/*/update/allfields").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/payrollitem/*/lookup").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/yearlyquarterinfo/lookup").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/yearlyquarter/update").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/register/vehicleinfo").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/*/vehicleinfo/*/update/allfields").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/vehicleinfo/*/lookup").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name, UserRole.EMPLOYEE.name)
                    .pathMatchers("/api/v1/settings/vehicleinfo/pageable/lookup/all").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/settings/vehicleinfo/lookup/all/active").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name, UserRole.EMPLOYEE.name)
            }
            .authorizeExchange {
                it
                    // notification inapp
                    .pathMatchers("/api/v1/notification/inapp/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/notification/inapp/count/bystatus").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/notification/inapp/*/update/status").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    // notification email
                    .pathMatchers("/api/v1/notification/email/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/notification/email/count/bystatus").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    // notification mobile
                    .pathMatchers("/api/v1/notification/mobile/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/notification/mobile/count/bystatus").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    // expenses report
                    .pathMatchers("/api/v1/expensereport/add").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/add/expenserow/foremployee").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/expensereportrow/*/delete").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/delete").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/update/status").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/review/byadmin").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/submit/byemployee").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/review/bysupervisor").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/count/foradmin/bystatus").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/expensereport/count/foremployee/*/bystatus").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/count/forsupervisor/*/bystatus").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/add/expenserow/forsuyash").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/expenserows/lookup/forsuyash").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/expenserows/lookup/foremployee").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/update/withexpenserows/foremployee").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/update/withexpenserows/forsuyash").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/export/toexcel").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/*/export/topdf").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/lookup/search/forjobcode/**").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/lookup/byjobcode/byperiodfrom").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/jobcode/periodfrom/export/toexcel").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/byperiodfrom/jobcode/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/timesheet/timesheetrow/search/all/export/toexcel").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/expensereport/lookup/all").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    // document
                    .pathMatchers("/api/v1/document/upload").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/document/*/view").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/document/*/thumbnail").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/document/*/update/status").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/document/*/lookup").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    // project
                    .pathMatchers("/api/v1/project/register").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/*/update/status").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/*/lookup").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/lookup/all").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/pageable/lookup/all").hasAnyAuthority(*UserRole.ALL_ADMIN(), *UserRole.ALL_EMPLOYEE())
                    .pathMatchers("/api/v1/project/*/update/allfields").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/lookup/all/active").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/*/add/jobcode").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
                    .pathMatchers("/api/v1/project/*/jobcode/*/update").hasAnyAuthority(*UserRole.ALL_ADMIN(), UserRole.MANAGER.name)
            }
            .authorizeExchange {
                it
                    // project report
                    .pathMatchers("/api/v1/projectreport/register").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/pageable/lookup/search/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/update/status").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/update/field").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/upload/outfallphoto").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/outfallphoto/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/outfallphoto/*/update/status").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/outfallphoto/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/add/appendix").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/appendix/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/appendix/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/projectreport/*/appendix/*/update/status").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it // system config
                    .pathMatchers("/api/v1/systemconfig/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/systemconfig/update/logo").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/systemconfig/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/systemconfig/ui/lookup").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it // tvhg Input
                    .pathMatchers("/api/v1/tvhginput/register").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/update/status").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/pageable/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/projectinformation/add").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/structureinformation").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/structureinformation/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/structuredrawingdata/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/update/projectinformation/field").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/projectinformation/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/hydrologicinformation").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/hydrologicinformation/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/update/hydrologicinformation/field").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/pipeinformation").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/structuredrawingdata").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/pipeinformation/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/structuredrawingdata/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/inletcontrolparameter").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/outletdrawinginformation").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/pipedrawinginformation").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/flowpathdrawinginformation/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/pipedrawinginformation/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/inletcontrolparameter/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/update/outletdrawinginformation/field").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/pipedrawinginformation/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/add/flowpathdrawinginformation").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhginput/*/outletdrawinginformation/distanceelevation/*/update/allFields").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it // tvhg config
                    .pathMatchers("/api/v1/tvhgconfig/register").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/units").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/units/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/designstorm").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/structuretype").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/designstorm/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/inletcontroldata").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/outletstructuretype").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/pipematerial").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/pipetype").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/designstorm/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/inletcontroldata/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/pipematerial/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/pipeType/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/structuretype/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/units/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/outletstructuretype/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/add/mdstandardnumber").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/mdstandardnumber/*/update/allfields").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/tvhgconfig/mdstandardnumber/lookup/bystructureclass").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it // vehicle log
                    .pathMatchers("/api/v1/vehiclelog/register").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/vehiclelog/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/vehiclelog/pageable/lookup/all").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/vehiclelog/*/update/allfields").hasAnyAuthority(*UserRole.ALL_ADMIN())
                    .pathMatchers("/api/v1/vehicle/*/lookup/vehiclelog").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/vehiclelog/*/lookup").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/vehiclelog/createdBy/*/vehicle/*/lookup/foremployee").hasAnyAuthority(*UserRole.ALL())
                    .pathMatchers("/api/v1/vehiclelog/*/update/status").hasAnyAuthority(*UserRole.ALL())
            }
            .authorizeExchange {
                it
                    .anyExchange()
                    .authenticated()
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(urlBasedCorsConfigurationSource()) }
            .csrf { it.disable() }
            .build()
    }

    private fun urlBasedCorsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.applyPermitDefaultValues()
        corsConfiguration.allowedHeaders = listOf("*")
        corsConfiguration.allowedMethods = listOf("*")
        corsConfiguration.allowedOrigins = listOf("*")
        val ccs = UrlBasedCorsConfigurationSource()
        ccs.registerCorsConfiguration("/**", corsConfiguration)
        return ccs
    }
}
