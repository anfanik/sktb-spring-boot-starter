package me.anfanik.sktb.update.impl

import com.pengrad.telegrambot.model.Update
import me.anfanik.sktb.update.UpdateRoutingService
import me.anfanik.sktb.utility.logger
import kotlin.reflect.KFunction

class UpdateRoutingServiceImpl : UpdateRoutingService {

    private val updateListeners = mutableMapOf<Any, MutableList<KFunction<*>>>()

    override fun processUpdate(update: Update) {
        val updateId = update.updateId()
        logger().info("Processing '$updateId' Update")

        updateListeners.forEach { (bean, functions) ->
            functions.forEach { function ->
                function.call(bean, update)
            }
        }
    }

    override fun registerUpdateListener(bean: Any, function: KFunction<*>) {
        updateListeners.computeIfAbsent(bean) { mutableListOf() }.add(function)
    }

    override fun unregisterUpdateListeners(bean: Any) {
        updateListeners.remove(bean)
    }

}