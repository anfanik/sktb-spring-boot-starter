package me.anfanik.sktb.chat.state.impl.abstr

import me.anfanik.sktb.chat.state.ChatState
import me.anfanik.sktb.chat.state.InitialChatStateProvider
import me.anfanik.sktb.utility.logger
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

abstract class ChatStateServiceBase : UpdateProcessingChatStateService() {

    abstract val beanFactory: ConfigurableListableBeanFactory
    abstract val initialStateProvider: InitialChatStateProvider

    override fun getState(telegramId: Long): ChatState {
        return loadState(telegramId) ?: let {
            val state = createInitialState(telegramId)
            setState(telegramId, state)
            state
        }
    }

    abstract fun loadState(telegramId: Long): ChatState?

    override fun setState(telegramId: Long, state: ChatState) {
        val previousState = loadState(telegramId)
        logger().info("Changing state for '$telegramId' from ${getStateName(previousState)} to ${getStateName(state)}")

        previousState?.stop()

        if (state is AutowiredChatState && !state.isAutowired) {
            beanFactory.autowireBean(state)
            state.isAutowired = true
        }

        saveState(telegramId, state)
        state.start()
    }

    abstract fun saveState(telegramId: Long, state: ChatState)

    private fun createInitialState(telegramId: Long): ChatState {
        return initialStateProvider.createInitialState(telegramId)
    }

    protected fun getStateName(state: ChatState?) = if (state == null) "null" else state::class.simpleName

}