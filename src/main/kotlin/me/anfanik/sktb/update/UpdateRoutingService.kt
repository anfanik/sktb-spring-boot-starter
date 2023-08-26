package me.anfanik.sktb.update

import com.pengrad.telegrambot.model.Update
import kotlin.reflect.KFunction

interface UpdateRoutingService {

    fun processUpdate(update: Update)

    fun registerUpdateListener(bean: Any, function: KFunction<*>)

    fun unregisterUpdateListeners(bean: Any)

}