package me.anfanik.sktb.utility.format.formatter

import kotlin.reflect.KFunction

object MarkdownV1TelegramFormatter : TelegramFormatter {

    override fun bold(text: String) = "*$text*"

    override fun italic(text: String) = "_${text}_"

    override fun underline(text: String): String =
        throw createNotSupportedException(this::underline)

    override fun strikethrough(text: String): String =
        throw createNotSupportedException(this::strikethrough)

    override fun spoiler(text: String): String =
        throw createNotSupportedException(this::spoiler)

    override fun url(text: String, url: String) = "[$text]($url)"

    override fun emoji(fallbackEmoji: String, emojiId: Long): String =
        throw createNotSupportedException(this::emoji)

    override fun code(text: String, language: String?) = "```${language?.let { "$it\n" } ?: ""}$text```"

    override fun escape(text: String) =
        text.replace("_", "\\_")
            .replace("*", "\\*")
            .replace("`", "\\`")
            .replace("[", "\\[")

    private fun createNotSupportedException(function: KFunction<Any>) =
        UnsupportedOperationException("operation ${function.name} is not supported for Markdown V1")

}