package com.syc.dashboard.query.settings.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindAllActiveVehicleInfoQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindExpenseTypeByTenantIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindPayrollItemByIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindPayrollItemByTenantIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindSettingsByTenantIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindSettingsMileageRateByTenantIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindSettingsYearlyQuarterByTenantIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.FindVehicleInfoByIdQueryHandler
import com.syc.dashboard.query.settings.api.queries.handler.PageableSearchAllVehicleInfoByFilterQueryHandler
import com.syc.dashboard.query.settings.repository.reactive.ExpenseTypeReactiveRepository
import com.syc.dashboard.query.settings.repository.reactive.PayrollItemReactiveRepository
import com.syc.dashboard.query.settings.repository.reactive.SettingsReactiveRepository
import com.syc.dashboard.query.settings.repository.reactive.VehicleInfoReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SettingsQueryHandler @Autowired constructor(
    private val settingsReactiveRepository: SettingsReactiveRepository,
    private val expenseTypeReactiveRepository: ExpenseTypeReactiveRepository,
    private val payrollItemReactiveRepository: PayrollItemReactiveRepository,
    private val vehicleInfoReactiveRepository: VehicleInfoReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindSettingsByTenantIdQuery): Flux<out BaseDto> {
        return FindSettingsByTenantIdQueryHandler(
            settingsReactiveRepository = settingsReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseTypeByTenantIdQuery): Flux<out BaseDto> {
        return FindExpenseTypeByTenantIdQueryHandler(
            expenseTypeReactiveRepository = expenseTypeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindSettingsMileageRateByTenantIdQuery): Flux<out BaseDto> {
        return FindSettingsMileageRateByTenantIdQueryHandler(
            settingsReactiveRepository = settingsReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindPayrollItemByTenantIdQuery): Flux<out BaseDto> {
        return FindPayrollItemByTenantIdQueryHandler(
            payrollItemReactiveRepository = payrollItemReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindPayrollItemByIdQuery): Flux<out BaseDto> {
        return FindPayrollItemByIdQueryHandler(
            payrollItemReactiveRepository = payrollItemReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindSettingsYearlyQuarterByTenantIdQuery): Flux<out BaseDto> {
        return FindSettingsYearlyQuarterByTenantIdQueryHandler(
            settingsReactiveRepository = settingsReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindVehicleInfoByIdQuery): Flux<out BaseDto> {
        return FindVehicleInfoByIdQueryHandler(
            vehicleInfoReactiveRepository = vehicleInfoReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: PageableSearchAllVehicleInfoByFilterQuery): Flux<out BaseDto> {
        return PageableSearchAllVehicleInfoByFilterQueryHandler(
            vehicleInfoReactiveRepository = vehicleInfoReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindAllActiveVehicleInfoQuery): Flux<out BaseDto> {
        return FindAllActiveVehicleInfoQueryHandler(
            vehicleInfoReactiveRepository = vehicleInfoReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindSettingsByTenantIdQuery -> handle(query)
            is FindExpenseTypeByTenantIdQuery -> handle(query)
            is FindSettingsMileageRateByTenantIdQuery -> handle(query)
            is FindPayrollItemByTenantIdQuery -> handle(query)
            is FindPayrollItemByIdQuery -> handle(query)
            is FindSettingsYearlyQuarterByTenantIdQuery -> handle(query)
            is FindVehicleInfoByIdQuery -> handle(query)
            is PageableSearchAllVehicleInfoByFilterQuery -> handle(query)
            is FindAllActiveVehicleInfoQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
