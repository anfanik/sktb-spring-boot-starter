package me.anfanik.sktb.chat.state

import com.pengrad.telegrambot.model.CallbackQuery
import com.pengrad.telegrambot.model.Message

interface ChatState {

    val telegramId: Long

    fun start() {}

    fun stop() {}

    fun processMessage(message: Message) {}

    fun processCallbackQuery(callback: CallbackQuery) {}

}