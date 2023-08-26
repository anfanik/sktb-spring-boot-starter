package me.anfanik.sktb.update

import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.utility.BotUtils
import me.anfanik.sktb.telegram.TelegramConfig
import me.anfanik.sktb.utility.logger
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

const val TELEGRAM_SECRET_HEADER = "X-Telegram-Bot-Api-Secret-Token"

@RestController
@RequestMapping("/telegram/updates")
class UpdateController(
    private val telegramConfig: TelegramConfig,
    private val updateRoutingService: UpdateRoutingService
) {

    @PostMapping
    fun processUpdate(
        @RequestBody updateRaw: String,
        @RequestHeader(TELEGRAM_SECRET_HEADER, required = false) secret: String?
    ): ResponseEntity<Unit> {
        if (secret != telegramConfig.webhook.secret) {
            logger().warn("Received update with invalid secret: $secret")
            return ResponseEntity.status(UNAUTHORIZED).build()
        }

        val update = try {
            parseUpdate(updateRaw)
        } catch (throwable: Throwable) {
            logger().error("Unhandled error while parsing update", throwable)
            return ResponseEntity.badRequest().build()
        }

        try {
            updateRoutingService.processUpdate(update)
        } catch (throwable: Throwable) {
            logger().error("Unhandled error while processing '${update.updateId()}' update", throwable)
        }

        return ResponseEntity.ok().build()
    }

    private fun parseUpdate(updateRaw: String): Update =
        BotUtils.GSON.fromJson(updateRaw, Update::class.java)

}