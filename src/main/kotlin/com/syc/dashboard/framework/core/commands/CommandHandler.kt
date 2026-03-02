package com.syc.dashboard.framework.core.commands

interface CommandHandler {

    fun <T : BaseCommand> handle(command: T)
}
