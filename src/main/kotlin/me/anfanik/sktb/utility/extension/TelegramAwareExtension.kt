@file:Suppress("unused", "NOTHING_TO_INLINE")

package me.anfanik.sktb.utility.extension

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup
import com.pengrad.telegrambot.model.request.InputMedia
import com.pengrad.telegrambot.model.request.LabeledPrice
import com.pengrad.telegrambot.request.*
import com.pengrad.telegrambot.response.*
import me.anfanik.sktb.telegram.TelegramAware
import me.anfanik.sktb.utility.format.setupFormatting

var SETUP_DEFAULT_FORMATTING = true

private fun setupDefaultFormatting(action: () -> Unit) {
    if (SETUP_DEFAULT_FORMATTING) {
        action()
    }
}

// GetMe

inline fun TelegramAware.getMe() : GetMeResponse =
    execute(GetMe())


// GetChat

inline fun TelegramAware.getChat(chatId: Long) : GetChatResponse =
    execute(GetChat(chatId))

inline fun TelegramAware.getChat(chatId: String) : GetChatResponse =
    execute(GetChat(chatId))


// SendMessage

fun SendMessage.prepareRequest() =
    apply {
        setupDefaultFormatting {
            setupFormatting()
        }
    }

inline fun TelegramAware.sendMessage(chatId: Long, text: String, modifier: SendMessage.() -> Unit = {}) : SendResponse =
    execute(SendMessage(chatId, text).prepareRequest(), modifier)

inline fun TelegramAware.sendMessage(chatId: String, text: String, modifier: SendMessage.() -> Unit = {}) : SendResponse =
    execute(SendMessage(chatId, text).prepareRequest(), modifier)


// DeleteMessage

inline fun TelegramAware.deleteMessage(chatId: Long, messageId: Int, modifier: DeleteMessage.() -> Unit = {}) : BaseResponse =
    execute(DeleteMessage(chatId, messageId), modifier)

inline fun TelegramAware.deleteMessage(chatId: String, messageId: Int, modifier: DeleteMessage.() -> Unit = {}) : BaseResponse =
    execute(DeleteMessage(chatId, messageId), modifier)


// SendMediaGroup

inline fun TelegramAware.sendMediaGroup(chatId: Long, media: List<InputMedia<*>>, modifier: SendMediaGroup.() -> Unit = {}) : MessagesResponse =
    execute(SendMediaGroup(chatId, *media.toTypedArray()), modifier)

inline fun TelegramAware.sendMediaGroup(chatId: String, media: List<InputMedia<*>>, modifier: SendMediaGroup.() -> Unit = {}) : MessagesResponse =
    execute(SendMediaGroup(chatId, *media.toTypedArray()), modifier)


// EditMessageText

fun EditMessageText.prepareRequest() =
    apply {
        setupDefaultFormatting {
            setupFormatting()
        }
    }

fun TelegramAware.editMessageText(chatId: Long, messageId: Int, text: String, modifier: EditMessageText.() -> Unit = {}): BaseResponse =
    execute(EditMessageText(chatId, messageId, text).prepareRequest(), modifier)

fun TelegramAware.editMessageText(chatId: String, messageId: Int, text: String, modifier: EditMessageText.() -> Unit = {}): BaseResponse =
    execute(EditMessageText(chatId, messageId, text).prepareRequest(), modifier)


// EditMessageMedia

inline fun TelegramAware.editMessageMedia(chatId: Long, messageId: Int, media: InputMedia<*>) : BaseResponse =
    execute(EditMessageMedia(chatId, messageId, media))

inline fun TelegramAware.editMessageMedia(chatId: String, messageId: Int, media: InputMedia<*>) : BaseResponse =
    execute(EditMessageMedia(chatId, messageId, media))


// EditMessageCaption

fun EditMessageCaption.prepareRequest() =
    apply {
        setupDefaultFormatting {
            setupFormatting()
        }
    }

inline fun TelegramAware.editMessageCaption(chatId: Long, messageId: Int, caption: String, modifier: EditMessageCaption.() -> Unit = {}) : BaseResponse =
    execute(EditMessageCaption(chatId, messageId).prepareRequest().apply { caption(caption) }, modifier)

inline fun TelegramAware.editMessageCaption(chatId: String, messageId: Int, caption: String, modifier: EditMessageCaption.() -> Unit = {}) : BaseResponse =
    execute(EditMessageCaption(chatId, messageId).prepareRequest().apply { caption(caption) }, modifier)


// EditMessageReplyMarkup

inline fun TelegramAware.editMessageReplyMarkup(chatId: Long, messageId: Int, modifier: EditMessageReplyMarkup.() -> Unit = {}) : BaseResponse =
    execute(EditMessageReplyMarkup(chatId, messageId), modifier)

inline fun TelegramAware.editMessageReplyMarkup(chatId: String, messageId: Int, modifier: EditMessageReplyMarkup.() -> Unit = {}) : BaseResponse =
    execute(EditMessageReplyMarkup(chatId, messageId), modifier)

inline fun TelegramAware.removeInlineKeyboard(chatId: Long, messageId: Int) : BaseResponse =
    editMessageReplyMarkup(chatId, messageId) { replyMarkup(InlineKeyboardMarkup()) }

inline fun TelegramAware.removeInlineKeyboard(chatId: String, messageId: Int) : BaseResponse =
    editMessageReplyMarkup(chatId, messageId) { replyMarkup(InlineKeyboardMarkup()) }


// ForwardMessage

inline fun TelegramAware.forwardMessage(chatId: Long, fromChatId: Long, messageId: Int, modifier: ForwardMessage.() -> Unit = {}) : SendResponse =
    execute(ForwardMessage(chatId, fromChatId, messageId), modifier)

inline fun TelegramAware.forwardMessage(chatId: String, fromChatId: Long, messageId: Int, modifier: ForwardMessage.() -> Unit = {}) : SendResponse =
    execute(ForwardMessage(chatId, fromChatId, messageId), modifier)


// AnswerCallbackQuery

inline fun TelegramAware.answerCallbackQuery(callbackQueryId: String, modifier: AnswerCallbackQuery.() -> Unit = {}) : BaseResponse =
    execute(AnswerCallbackQuery(callbackQueryId), modifier)


// AnswerPreCheckoutQuery

fun TelegramAware.answerPreCheckoutQuery(preCheckoutId: String): BaseResponse =
    execute(AnswerPreCheckoutQuery(preCheckoutId))

fun TelegramAware.answerPreCheckoutQuery(preCheckoutId: String, errorMessage: String): BaseResponse =
    execute(AnswerPreCheckoutQuery(preCheckoutId, errorMessage))


// SendInvoice

fun TelegramAware.sendInvoice(chatId: Long, title: String, description: String, payload: String, currency: String, items: List<LabeledPrice>, modifier: SendInvoice.() -> Unit = {}): SendResponse =
    execute(SendInvoice(chatId, title, description, payload, currency, *items.toTypedArray()), modifier)

fun TelegramAware.sendInvoice(chatId: Long, title: String, description: String, payload: String, currency: String, item: LabeledPrice, modifier: SendInvoice.() -> Unit = {}) =
    sendInvoice(
        chatId = chatId,
        title = title,
        description = description,
        payload = payload,
        currency = currency,
        items = listOf(item),
        modifier = modifier
    )


inline fun <T : BaseRequest<T, R>, R : BaseResponse> TelegramAware.execute(request: T, modifier: T.() -> Unit = {}): R =
    execute(request.apply(modifier))