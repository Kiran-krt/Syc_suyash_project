package com.syc.dashboard.migration.controller.v1

import com.syc.dashboard.framework.common.dto.SimpleMessageDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.migration.service.CostCodeMigrationService
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tags(Tag(name = "V1 Migration"))
@CrossOrigin
class CostCodeMigrationController(
    private val costCodeMigrationService: CostCodeMigrationService,
) {
    @PostMapping("/api/v1/migration/system/costcode")
    fun migrationField(): ResponseEntity<BaseDto> {
        costCodeMigrationService.updateCostCode()
        return ResponseEntity(
            SimpleMessageDto(message = "Cost Code Migration completed successfully.!"),
            HttpStatus.OK,
        )
    }
}
