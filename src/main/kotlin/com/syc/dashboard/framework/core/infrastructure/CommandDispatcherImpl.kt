package com.syc.dashboard.framework.core.infrastructure

import com.syc.dashboard.framework.core.commands.BaseCommand
import com.syc.dashboard.framework.core.commands.CommandHandlerMethod
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommandDispatcherImpl : CommandDispatcher {

    private val routes: MutableMap<Class<out BaseCommand>, MutableList<CommandHandlerMethod>> =
        HashMap()

    override fun <T : BaseCommand> registerHandler(type: Class<T>, handler: (T: BaseCommand) -> Unit) {
        val handlers = routes.computeIfAbsent(type) { LinkedList() }
        handlers.add(handler)
    }

    override fun <T : BaseCommand> send(command: T) {
        val handlers = routes[command.javaClass]
        if (handlers == null || handlers.isEmpty()) {
            throw RuntimeException("No command handler was registered!")
        }
        if (handlers.size > 1) {
            throw RuntimeException("Cannot send command to more than one handler!")
        }
        handlers[0].handle(command)
    }
}
