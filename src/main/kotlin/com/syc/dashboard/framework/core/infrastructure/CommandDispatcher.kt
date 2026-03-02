package com.syc.dashboard.framework.core.infrastructure

import com.syc.dashboard.framework.core.commands.BaseCommand

interface CommandDispatcher {

    fun <T> registerHandler(type: Class<T>, handler: (T: BaseCommand) -> Unit) where T : BaseCommand
    fun <T> send(command: T) where T : BaseCommand
}
