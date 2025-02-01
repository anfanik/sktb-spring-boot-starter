package me.anfanik.sktb.utility.format

import com.pengrad.telegrambot.model.LinkPreviewOptions
import com.pengrad.telegrambot.model.request.ParseMode

data class FormattingParameters(
    val mode: ParseMode,
    val disableLinkPreview: Boolean
) {

    fun toLinkPreviewOptions(): LinkPreviewOptions =
        LinkPreviewOptions()
            .isDisabled(disableLinkPreview)

}