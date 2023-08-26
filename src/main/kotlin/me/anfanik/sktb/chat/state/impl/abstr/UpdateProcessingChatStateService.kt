package me.anfanik.sktb.chat.state.impl.abstr

import com.pengrad.telegrambot.model.CallbackQuery
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import me.anfanik.sktb.chat.state.ChatStateService
import me.anfanik.sktb.telegram.TelegramService
import me.anfanik.sktb.update.listener.UpdateListener
import me.anfanik.sktb.utility.extension.answerCallbackQuery
import me.anfanik.sktb.utility.logger

abstract class UpdateProcessingChatStateService : ChatStateService {

    abstract val telegram: TelegramService

    @UpdateListener
    fun processUpdate(update: Update) {
        val updateId = update.updateId()
        logger().info("Processing '$updateId' Update")

        update.message()?.let {
            processMessage(updateId, it)
        }

        update.callbackQuery()?.let {
            processCallbackQuery(updateId, it)
        }
    }

    private fun processMessage(updateId: Int, message: Message) {
        logger().debug("Processing '${message.messageId()}' message from '$updateId' update")
        val chat = message.chat()

        val state = getState(chat.id())
        state.processMessage(message)
    }

    private fun processCallbackQuery(updateId: Int, callback: CallbackQuery) {
        val originalMessage = callback.maybeInaccessibleMessage()
        logger().debug("Processing '${callback.id()}' callback for '${originalMessage.messageId()}' inline message from '$updateId' update")

        val state = getState(originalMessage.chat().id())

        try {
            state.processCallbackQuery(callback)
        } catch (throwable: Throwable) {
            logger().error("Unexpected error while processing callback query", throwable)

            telegram.answerCallbackQuery(callback.id()) {
                showAlert(true)

                val errorMessage = throwable.message?.let {
                    if (it.length > 10) {
                        "${it.substring(0, 10)}..."
                    } else {
                        it
                    }
                }

                text("""
                    Произошла ошибка:
                    ${throwable::class.simpleName} – $errorMessage
                """.trimIndent())
            }
            throw throwable
        }
    }

}