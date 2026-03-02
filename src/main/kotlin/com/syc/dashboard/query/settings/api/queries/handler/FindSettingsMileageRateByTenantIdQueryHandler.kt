package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindSettingsMileageRateByTenantIdQuery
import com.syc.dashboard.query.settings.dto.MileageRateDto
import com.syc.dashboard.query.settings.repository.reactive.SettingsReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindSettingsMileageRateByTenantIdQueryHandler(
    private val settingsReactiveRepository: SettingsReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindSettingsMileageRateByTenantIdQuery
        return settingsReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
            .map {
                val mileageRateDto = EntityDtoConversion.toDto(it.mileageRateInfo, MileageRateDto::class)
                mileageRateDto.settingsId = it.id
                mileageRateDto
            }
            .toFlux()
    }
}
