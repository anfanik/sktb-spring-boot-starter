package me.anfanik.sktb.telegram

import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.response.BaseResponse

interface TelegramAware {

    fun <REQ : BaseRequest<REQ, RES>, RES : BaseResponse> execute(request: REQ): RES

}