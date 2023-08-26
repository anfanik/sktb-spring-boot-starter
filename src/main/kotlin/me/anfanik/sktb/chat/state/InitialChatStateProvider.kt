package me.anfanik.sktb.chat.state

fun interface InitialChatStateProvider {

    fun createInitialState(telegramId: Long): ChatState

}