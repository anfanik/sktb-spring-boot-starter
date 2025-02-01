package me.anfanik.sktb.utility.format.formatter

object HtmlTelegramFormatter : TelegramFormatter {

    override fun bold(text: String) = "<b>$text</b>"

    override fun italic(text: String) = "<i>$text</i>"

    override fun underline(text: String) = "<u>$text</u>"

    override fun strikethrough(text: String) = "<s>$text</s>"

    override fun spoiler(text: String) = "<tg-spoiler>$text</tg-spoiler>"

    override fun url(text: String, url: String) = "<a href=\"$url\">$text</a>"

    override fun emoji(fallbackEmoji: String, emojiId: Long) = "<tg-emoji emoji-id=\"$emojiId\">$fallbackEmoji</tg-emoji>"

    override fun code(text: String, language: String?) = "<code>$text</code>"

    override fun escape(text: String) =
        text.replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("&", "&amp;")

}