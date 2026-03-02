package com.syc.dashboard.query.settings.repository.jpa

import com.syc.dashboard.query.settings.entity.Settings
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface SettingsRepository : MongoRepository<Settings, String> {

    fun findByTenantId(tenantId: String): Optional<Settings>
}
