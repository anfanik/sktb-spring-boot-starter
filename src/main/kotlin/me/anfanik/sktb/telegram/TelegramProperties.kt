package me.anfanik.sktb.telegram

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("telegram")
data class TelegramConfig(
    /**
     * Telegram API URL
     */
    val apiUrl: String = "https://api.telegram.org/bot",
    /**
     * Telegram Bot token
     */
    val token: String,
    /**
     * Telegram Bot webhook configuration
     */
    val webhook: WebhookConfig = WebhookConfig(),
)

data class WebhookConfig(
    /**
     * Enable webhook
     */
    val enabled: Boolean = false,
    /**
     * Webhook secret
     */
    val secret: String? = null,
)