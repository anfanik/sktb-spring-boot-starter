package me.anfanik.sktb.utility

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger =
    LoggerFactory.getLogger(if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java)

inline fun Logger.debug(provider: () -> String) {
    if (this.isDebugEnabled) {
        this.debug(provider())
    }
}