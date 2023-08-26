package me.anfanik.sktb.chat.state.impl.abstr

import me.anfanik.sktb.chat.state.ChatState
import me.anfanik.sktb.telegram.TelegramService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

@Configurable
abstract class AutowiredChatState(
    override val telegramId: Long
) : ChatState {

    internal var isAutowired: Boolean = false

    @Autowired
    lateinit var telegram: TelegramService

}