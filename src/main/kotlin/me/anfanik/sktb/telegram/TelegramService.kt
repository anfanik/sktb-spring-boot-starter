package me.anfanik.sktb.telegram

import com.pengrad.telegrambot.TelegramAware
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.response.BaseResponse
import kotlin.jvm.Throws

interface TelegramService : TelegramAware {

    @Throws(TelegramApiException::class)
    override fun <REQ : BaseRequest<REQ, RES>, RES : BaseResponse> execute(request: BaseRequest<REQ, RES>): RES
    
}