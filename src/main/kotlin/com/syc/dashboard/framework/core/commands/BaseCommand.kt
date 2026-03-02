package com.syc.dashboard.framework.core.commands

import com.syc.dashboard.framework.core.messages.Message

abstract class BaseCommand(
    id: String,
) : Message(id = id)
