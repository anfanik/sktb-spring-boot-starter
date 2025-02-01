package me.anfanik.sktb.utility.format

import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.model.request.ParseMode.*
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.request.EditMessageCaption
import com.pengrad.telegrambot.request.EditMessageText
import com.pengrad.telegrambot.request.SendMessage
import me.anfanik.sktb.utility.format.formatter.*

var DEFAULT_FORMATTING_PARAMETERS: FormattingParameters? = null

private fun getDefaultFormattingParameters() =
    DEFAULT_FORMATTING_PARAMETERS
        ?: throw IllegalStateException("Default formatting parameters is not initialized")

fun String.bold(parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).bold(this)

fun String.italic(parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).italic(this)

fun String.underline(parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).underline(this)

fun String.strikethrough(parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).strikethrough(this)

fun String.spoiler(parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).spoiler(this)

fun String.url(url: String, parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).url(this, url)

fun String.emoji(emojiId: Long, parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).emoji(this, emojiId)

fun String.code(language: String? = null, parseMode: ParseMode = getDefaultFormattingParameters().mode) =
    getMarkdownFormatter(parseMode).code(this, language)

private fun getMarkdownFormatter(parseMode: ParseMode): TelegramFormatter =
    when (parseMode) {
        HTML -> HtmlTelegramFormatter
        Markdown -> MarkdownV1TelegramFormatter
        MarkdownV2 -> TODO()
    }

fun BaseRequest<*, *>.setupFormatting(settings: FormattingParameters = getDefaultFormattingParameters()) {
    when (this) {
        is SendMessage -> setupFormatting(settings)
        is EditMessageText -> setupFormatting(settings)
        is EditMessageCaption -> setupFormatting(settings)
    }
}

fun SendMessage.setupFormatting(settings: FormattingParameters = getDefaultFormattingParameters()) {
    parseMode(settings.mode)
    linkPreviewOptions(settings.toLinkPreviewOptions())
}

fun EditMessageText.setupFormatting(settings: FormattingParameters = getDefaultFormattingParameters()) {
    parseMode(settings.mode)
    linkPreviewOptions(settings.toLinkPreviewOptions())
}

fun EditMessageCaption.setupFormatting(settings: FormattingParameters = getDefaultFormattingParameters()) {
    parseMode(settings.mode)
}