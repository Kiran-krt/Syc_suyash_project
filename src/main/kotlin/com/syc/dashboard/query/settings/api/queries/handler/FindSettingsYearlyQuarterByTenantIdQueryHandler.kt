package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindSettingsYearlyQuarterByTenantIdQuery
import com.syc.dashboard.query.settings.dto.YearlyQuarterDto
import com.syc.dashboard.query.settings.repository.reactive.SettingsReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindSettingsYearlyQuarterByTenantIdQueryHandler(
    private val settingsReactiveRepository: SettingsReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindSettingsYearlyQuarterByTenantIdQuery
        return settingsReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
            .map {
                val yearlyQuarterDto = if (it.yearlyQuarterInfo != null) {
                    EntityDtoConversion.toDto(it.yearlyQuarterInfo as BaseEntity, YearlyQuarterDto::class)
                } else {
                    YearlyQuarterDto()
                }
                yearlyQuarterDto.settingsId = it.id
                yearlyQuarterDto
            }
            .toFlux()
    }
}
