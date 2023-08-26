package me.anfanik.sktb.chat.state.impl

import me.anfanik.sktb.chat.state.ChatState
import me.anfanik.sktb.chat.state.ChatStateService
import me.anfanik.sktb.chat.state.InitialChatStateProvider
import me.anfanik.sktb.chat.state.impl.abstr.ChatStateServiceBase
import me.anfanik.sktb.telegram.TelegramService
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

class InMemoryChatStateService(
    override val beanFactory: ConfigurableListableBeanFactory,
    override val initialStateProvider: InitialChatStateProvider,
    override val telegram: TelegramService
) : ChatStateService, ChatStateServiceBase() {

    private val states = mutableMapOf<Long, ChatState>()

    override fun loadState(telegramId: Long): ChatState? =
        states[telegramId]

    override fun saveState(telegramId: Long, state: ChatState) {
        states[telegramId] = state
    }

}