package me.anfanik.sktb.telegram

import com.pengrad.telegrambot.response.BaseResponse

class TelegramApiException(response: BaseResponse) :
    RuntimeException("Unhandled error while calling Telegram API: ${response.description()}")