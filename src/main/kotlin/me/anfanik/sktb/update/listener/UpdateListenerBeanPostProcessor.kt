package me.anfanik.sktb.update.listener

import com.pengrad.telegrambot.model.Update
import me.anfanik.sktb.update.UpdateRoutingService
import me.anfanik.sktb.utility.logger
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions

@Component
class UpdateListenerBeanPostProcessor(
    private val updateRoutingService: UpdateRoutingService
) : DestructionAwareBeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val functions = bean::class.functions

        functions.filter { isUpdateListener(it) }
            .forEach {
                updateRoutingService.registerUpdateListener(bean, it)
            }

        return super.postProcessAfterInitialization(bean, beanName)
    }

    fun isUpdateListener(function: KFunction<*>): Boolean {
        if (function.annotations.none { it is UpdateListener }) {
            logger().debug("Function {} is not an UpdateListener", function)
            return false
        }

        if (function.parameters.size != 2 || function.parameters[1].type.classifier != Update::class) {
            logger().warn("Function ${function.name} is an UpdateListener but function must have exactly 1 parameter: Update")
            return false
        }

        return true
    }

    override fun postProcessBeforeDestruction(bean: Any, beanName: String) {

    }

}