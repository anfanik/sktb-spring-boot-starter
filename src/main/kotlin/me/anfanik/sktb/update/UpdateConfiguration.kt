package me.anfanik.sktb.update

import me.anfanik.sktb.telegram.TelegramProperties
import me.anfanik.sktb.update.impl.UpdateRoutingServiceImpl
import me.anfanik.sktb.update.listener.UpdateListenerBeanPostProcessor
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.RestController

@AutoConfiguration
class UpdateConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun updateListenerBeanPostProcessor(updateRoutingService: UpdateRoutingService) =
        UpdateListenerBeanPostProcessor(updateRoutingService)

    @Bean
    fun updateRoutingService(): UpdateRoutingService =
        UpdateRoutingServiceImpl()

    @Bean
    @ConditionalOnProperty("telegram.webhook.enabled", havingValue = "true")
    @ConditionalOnClass(RestController::class)
    fun updateController(
        telegramProperties: TelegramProperties,
        updateRoutingService: UpdateRoutingService
    ): UpdateController = UpdateController(telegramProperties, updateRoutingService)

}