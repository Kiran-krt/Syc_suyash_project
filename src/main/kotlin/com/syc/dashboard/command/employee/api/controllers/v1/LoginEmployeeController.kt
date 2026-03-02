package com.syc.dashboard.command.employee.api.controllers.v1

import com.syc.dashboard.command.employee.api.commands.EmployeeLogInCommand
import com.syc.dashboard.command.employee.dto.EmployeeLoginDto
import com.syc.dashboard.framework.common.security.dto.AuthTokenDto
import com.syc.dashboard.framework.common.security.dto.TokenData
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.common.security.jwt.JwtTokenProvider
import com.syc.dashboard.framework.common.security.service.CustomAuthenticationManager
import com.syc.dashboard.framework.common.security.service.UserPrincipalReactiveService
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.query.employee.exceptions.EmployeeNotFoundException
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
@Tags(Tag(name = "V1 Employee"))
@CrossOrigin
class LoginEmployeeController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
    private val authenticationManager: CustomAuthenticationManager,
    private val jwdTokenProvider: JwtTokenProvider,
    private val userPrincipalReactiveService: UserPrincipalReactiveService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : RootController() {

    @PostMapping("/api/v1/employee/login")
    fun loginEmployee(
        @RequestBody employeeLoginDto: EmployeeLoginDto,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Mono<out BaseDto>> {
        employeeLoginDto.tenantId = tenantId

        return ResponseEntity.ok(
            userPrincipalReactiveService.findUserDetailsByEmailAndRole(
                tenantId = tenantId,
                email = employeeLoginDto.email,
                role = UserRole.EMPLOYEE,
            )
                .filter { bCryptPasswordEncoder.matches(employeeLoginDto.password, it.password) }
                .map {
                    commandDispatcher.send(
                        buildCommand(
                            EmployeeLogInCommand(
                                email = employeeLoginDto.email,
                                password = employeeLoginDto.password,
                                loggedIn = true,
                            ),
                        ),
                    )
                    it
                }
                .flatMap {
                    authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(
                            it,
                            it.password,
                            setOf(SimpleGrantedAuthority(it.getRole().name)),
                        ),
                    )
                }
                .map {
                    SecurityContextHolder.getContext().authentication = it
                    AuthTokenDto(
                        message = "Successful Login!",
                        tokenData = TokenData(
                            access_token = jwdTokenProvider.generateToken(
                                it,
                                employeeLoginDto.role,
                            ),
                        ),
                    )
                }
                .switchIfEmpty { throw EmployeeNotFoundException("Invalid email or password!") },
        )
    }
}
