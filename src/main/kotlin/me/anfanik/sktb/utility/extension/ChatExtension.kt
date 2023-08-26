package me.anfanik.sktb.utility.extension

import com.pengrad.telegrambot.model.Chat

val Chat.displayName: String
    get() = title()
        ?: listOfNotNull(firstName(), lastName()).joinToString(" ")