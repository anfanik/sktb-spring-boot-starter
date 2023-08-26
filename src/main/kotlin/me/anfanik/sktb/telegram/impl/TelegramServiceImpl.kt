package me.anfanik.sktb.telegram.impl

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.response.BaseResponse
import me.anfanik.sktb.telegram.TelegramApiException
import me.anfanik.sktb.telegram.TelegramService

class TelegramServiceImpl(
    private val telegramBot: TelegramBot
) : TelegramService {

    override fun <REQ : BaseRequest<REQ, RES>, RES : BaseResponse> execute(request: REQ): RES {
        val response = telegramBot.execute(request)

        if (response.isOk) {
            return response
        } else {
            throw TelegramApiException(response)
        }
    }

}