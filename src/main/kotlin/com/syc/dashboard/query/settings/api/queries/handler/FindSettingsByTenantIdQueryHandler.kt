package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindSettingsByTenantIdQuery
import com.syc.dashboard.query.settings.dto.SettingsDto
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum
import com.syc.dashboard.query.settings.repository.reactive.SettingsReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindSettingsByTenantIdQueryHandler(
    private val settingsReactiveRepository: SettingsReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindSettingsByTenantIdQuery
        return settingsReactiveRepository
            .findFirstByTenantIdAndStatus(
                tenantId = query.tenantId,
                status = SettingsStatusEnum.ACTIVE,
            )
            .map {
                val settingsDto = EntityDtoConversion.toDto(it, SettingsDto::class)

                settingsDto.systemSettings =
                    EntityDtoConversion.toDto(it.systemSettings, SettingsDto.SystemSettings::class)

                settingsDto.mileageRateInfo =
                    EntityDtoConversion.toDto(it.mileageRateInfo, SettingsDto.MileageRateInfo::class)

                settingsDto.yearlyQuarterInfo =
                    if (it.yearlyQuarterInfo != null) {
                        EntityDtoConversion.toDto(it.yearlyQuarterInfo as BaseEntity, SettingsDto.YearlyQuarterInfo::class)
                    } else {
                        SettingsDto.YearlyQuarterInfo()
                    }
                settingsDto
            }
            .toFlux()
    }
}
