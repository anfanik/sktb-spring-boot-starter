package me.anfanik.sktb.telegram

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.User
import com.pengrad.telegrambot.utility.kotlin.extension.request.getMe
import jakarta.annotation.PostConstruct
import me.anfanik.sktb.telegram.impl.TelegramServiceImpl
import me.anfanik.sktb.update.UpdateRoutingService
import me.anfanik.sktb.utility.format.DEFAULT_FORMAT_SETTINGS
import me.anfanik.sktb.utility.format.FormatSettings
import me.anfanik.sktb.utility.logger
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@AutoConfiguration
@EnableConfigurationProperties(TelegramProperties::class)
class TelegramConfiguration(
    private val config: TelegramProperties
) {

    @PostConstruct
    fun setupFormatting() {
        logger().info("Setting up default formatting options")
        DEFAULT_FORMAT_SETTINGS = FormatSettings(
            mode = config.formatting.mode,
            disableLinkPreview = config.formatting.disableLinkPreview
        )
    }

    @Bean
    @ConditionalOnMissingBean(TelegramBot::class)
    fun telegramBot(updateRoutingServiceProvider: ObjectProvider<UpdateRoutingService>): TelegramBot {
        logger().info("Configuring Telegram client")

        val client = try {
            TelegramBot.Builder(config.token)
                .apiUrl(config.apiUrl)
                .apply {
                    if (config.formatting.enabled) {
                        logger().info("Setting up formatting for Telegram client")
                        requestPreprocessor(FormattingSetupRequestPreprocessor())
                    }
                }
                .build()
        } catch (throwable: Throwable) {
            logger().error("Unable to configure Telegram client: ${throwable::class.simpleName} - ${throwable.message}", throwable)
            throw throwable
        }

        if (!config.webhook.enabled) {
            logger().info("Configuring update listener")
            setupUpdateListener(client, updateRoutingServiceProvider)
        } else {
            logger().info("Webhook is enabled, skipping update listener setup")
        }

        return client
    }

    private fun setupUpdateListener(
        client: TelegramBot,
        updateRoutingServiceProvider: ObjectProvider<UpdateRoutingService>
    ) {
        client.setUpdatesListener { updates ->
            logger().info("Received ${updates.size} updates")

            val updateRoutingService = updateRoutingServiceProvider.getIfAvailable()
            if (updateRoutingService == null) {
                logger().info("Skipping ${updates.size} Updates because of no UpdateRoutingService found");
                return@setUpdatesListener UpdatesListener.CONFIRMED_UPDATES_ALL
            }

            updates.forEach { update ->
                update.runCatching { updateRoutingService.processUpdate(update) }
                    .onFailure { throwable ->
                        logger().error("Unhandled error while processing '${update.updateId()}' update", throwable)
                    }
            }
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }

    @Bean
    fun telegramService(telegramBot: TelegramBot): TelegramService =
        TelegramServiceImpl(telegramBot)

    @Bean
    @Qualifier("botUser")
    fun botUser(telegram: TelegramService): User {
        val user = try {
            val response = telegram.getMe()
            response.user()
        } catch (throwable: Throwable) {
            logger().error("Unable to fetch Telegram user", throwable)
            throw throwable
        }

        logger().info("Started Telegram Bot '@${user.username()}'")

        return user
    }

}