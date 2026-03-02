package com.syc.dashboard.query.timesheet.repository.jpa

import com.syc.dashboard.query.timesheet.entity.TimesheetRow
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface TimesheetRowRepository : MongoRepository<TimesheetRow, String> {

    fun findByTenantIdAndIdAndTimesheetId(
        tenantId: String,
        id: String,
        timesheetId: String,
    ): Optional<TimesheetRow>

    fun findByTenantIdAndTimesheetIdAndId(
        tenantId: String,
        timesheetId: String,
        id: String,
    ): Optional<TimesheetRow>
}
