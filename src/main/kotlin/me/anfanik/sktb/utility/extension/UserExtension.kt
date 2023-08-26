package me.anfanik.sktb.utility.extension

import com.pengrad.telegrambot.model.User

val User.displayName: String
    get() = listOfNotNull(firstName(), lastName()).joinToString(" ")