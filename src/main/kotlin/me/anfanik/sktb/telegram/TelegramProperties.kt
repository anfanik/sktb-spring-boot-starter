package me.anfanik.sktb.telegram

import com.pengrad.telegrambot.model.request.ParseMode
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("telegram")
data class TelegramProperties(
    /**
     * Telegram API URL
     */
    val apiUrl: String = "https://api.telegram.org/bot",
    /**
     * Telegram File API URL
     */
    val fileApiUrl: String = "https://api.telegram.org/file/bot",
    /**
     * Telegram Bot token
     */
    val token: String,
    /**
     * Telegram Bot webhook configuration
     */
    val webhook: WebhookProperties = WebhookProperties(),
    /**
     * Formatting configuration
     */
    val formatting: FormattingProperties = FormattingProperties()
)

data class WebhookProperties(
    /**
     * Enable webhook
     */
    val enabled: Boolean = false,
    /**
     * Webhook secret
     */
    val secret: String? = null,
)

data class FormattingProperties(
    /**
     * Setup built-in requests formatting
     */
    val setupRequestsFormatting: Boolean = true,
    /**
     * Formatting mode
     */
    val mode: ParseMode = ParseMode.HTML,
    /**
     * Disable link preview
     */
    val disableLinkPreview: Boolean = true
)