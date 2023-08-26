package me.anfanik.sktb.utility.format

import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.model.request.ParseMode.*
import com.pengrad.telegrambot.request.EditMessageCaption
import com.pengrad.telegrambot.request.EditMessageText
import com.pengrad.telegrambot.request.SendMessage
import me.anfanik.sktb.utility.format.formatter.HtmlMarkdownFormatter
import me.anfanik.sktb.utility.format.formatter.MarkdownFormatter
import me.anfanik.sktb.utility.format.formatter.MarkdownV1MarkdownFormatter

var DEFAULT_FORMAT_SETTINGS = FormatSettings(
    mode = HTML,
    disableLinkPreview = true
)

fun String.bold(parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).bold(this)

fun String.italic(parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).italic(this)

fun String.underline(parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).underline(this)

fun String.strikethrough(parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).strikethrough(this)

fun String.spoiler(parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).spoiler(this)

fun String.url(url: String, parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).url(this, url)

fun String.emoji(emojiId: Long, parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).emoji(this, emojiId)

fun String.code(language: String? = null, parseMode: ParseMode = DEFAULT_FORMAT_SETTINGS.mode) =
    getMarkdownFormatter(parseMode).code(this, language)

private fun getMarkdownFormatter(parseMode: ParseMode): MarkdownFormatter =
    when (parseMode) {
        HTML -> HtmlMarkdownFormatter
        Markdown -> MarkdownV1MarkdownFormatter
        MarkdownV2 -> TODO()
    }

fun SendMessage.setupFormatting(settings: FormatSettings = DEFAULT_FORMAT_SETTINGS) {
    parseMode(settings.mode)
    linkPreviewOptions(settings.toLinkPreviewOptions())
}

fun EditMessageText.setupFormatting(settings: FormatSettings = DEFAULT_FORMAT_SETTINGS) {
    parseMode(settings.mode)
    linkPreviewOptions(settings.toLinkPreviewOptions())
}

fun EditMessageCaption.setupFormatting(settings: FormatSettings = DEFAULT_FORMAT_SETTINGS) {
    parseMode(settings.mode)
}