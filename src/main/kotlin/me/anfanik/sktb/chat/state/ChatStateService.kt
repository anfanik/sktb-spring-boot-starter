package me.anfanik.sktb.chat.state

interface ChatStateService {

    fun getState(telegramId: Long): ChatState

    fun setState(telegramId: Long, state: ChatState)

}