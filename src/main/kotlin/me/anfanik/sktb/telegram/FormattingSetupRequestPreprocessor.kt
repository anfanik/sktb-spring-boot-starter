package me.anfanik.sktb.telegram

import com.pengrad.telegrambot.RequestPreprocessor
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.response.BaseResponse
import me.anfanik.sktb.utility.debug
import me.anfanik.sktb.utility.format.setupFormatting
import me.anfanik.sktb.utility.logger

class FormattingSetupRequestPreprocessor : RequestPreprocessor {

    override fun <REQ : BaseRequest<REQ, RES>, RES : BaseResponse> process(request: BaseRequest<REQ, RES>) {
        logger().debug { "Setting up formatting for request ${request::class.simpleName} (${request.method}) request" }
        request.setupFormatting()
    }

}