package me.anfanik.sktb.chat.state

import me.anfanik.sktb.chat.state.impl.DummyChatState
import me.anfanik.sktb.chat.state.impl.InMemoryChatStateService
import me.anfanik.sktb.telegram.TelegramService
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class ChatStateConfiguration {

    @Bean
    @ConditionalOnMissingBean(InitialChatStateProvider::class)
    fun initialChatStateProvider() = InitialChatStateProvider { telegramId ->
        DummyChatState(telegramId)
    }

    @Bean
    fun chatStateService(
        beanFactory: ConfigurableListableBeanFactory,
        initialStateProvider: InitialChatStateProvider,
        telegramService: TelegramService
    ): ChatStateService = InMemoryChatStateService(
        beanFactory = beanFactory,
        initialStateProvider = initialStateProvider,
        telegram = telegramService
    )

}