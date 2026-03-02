package com.syc.dashboard.framework.core.commands

fun interface CommandHandlerMethod {
    fun handle(command: BaseCommand)
}
