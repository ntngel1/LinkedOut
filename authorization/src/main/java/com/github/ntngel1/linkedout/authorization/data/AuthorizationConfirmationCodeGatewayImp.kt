package com.github.ntngel1.linkedout.authorization.data

import com.github.ntngel1.linkedout.authorization.domain.gateway.AuthorizationConfirmationCodeGateway
import com.github.ntngel1.linkedout.lib_telegram.TelegramClient
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

class AuthorizationConfirmationCodeGatewayImp @Inject constructor(
    private val telegramClient: TelegramClient
) : AuthorizationConfirmationCodeGateway {

    override suspend fun sendConfirmationCode(phoneNumber: String) {
        val query = TdApi.SetAuthenticationPhoneNumber(
            phoneNumber,
            // TODO AllowFlashCall and eval isCurrentPhoneNumber
            TdApi.PhoneNumberAuthenticationSettings(
                false,
                false,
                false
            )
        )

        telegramClient.execute(query)
    }

    override suspend fun checkConfirmationCode(confirmationCode: String) {
        val query = TdApi.CheckAuthenticationCode(confirmationCode)
        telegramClient.execute(query)
    }
}