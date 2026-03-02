package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import java.util.*

class AddStructureDrawingDataCommand(
    id: String = "",
    var structureDrawingDataId: String = "",
    var structureInformationId: String = "",
    var existingOrProposedIndex: String = "",
    var mdshaStandardNumber: String = "",
    var typeOfStructure: String = "",
    var structureClass: String = "",
    var station: String = "",
    var offset: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
) : TenantBaseCommand(id = id)
