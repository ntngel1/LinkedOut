package com.github.ntngel1.linkedout.authorization.data

import com.github.ntngel1.linkedout.authorization.domain.gateway.AuthorizationStateGateway
import com.github.ntngel1.linkedout.authorization.entity.AuthorizationState
import com.github.ntngel1.linkedout.lib_telegram.TelegramClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

class AuthorizationStateGatewayImp @Inject constructor(
    private val telegramClient: TelegramClient
) : AuthorizationStateGateway {

    override fun observeAuthorizationStateChanges(): Flow<AuthorizationState> {
        return telegramClient.authorizationStatesChannel.asFlow()
            .mapNotNull { state ->
                state.toDomainAuthorizationState()
            }
    }

    private fun TdApi.AuthorizationState.toDomainAuthorizationState(): AuthorizationState? =
        when (this) {
            is TdApi.AuthorizationStateWaitPhoneNumber -> AuthorizationState.WaitPhoneNumber
            is TdApi.AuthorizationStateWaitCode -> AuthorizationState.WaitConfirmationCode(
                phoneNumber = this.codeInfo.phoneNumber,
                confirmationCodeLength = when (val type = this.codeInfo.type) {
                    is TdApi.AuthenticationCodeTypeSms -> type.length
                    is TdApi.AuthenticationCodeTypeTelegramMessage -> type.length
                    is TdApi.AuthenticationCodeTypeCall -> type.length
                    is TdApi.AuthenticationCodeTypeFlashCall -> throw IllegalStateException("Flash call is not supported")
                    else -> throw IllegalStateException("Unhandled authentication code type = $type")
                }
            )
            is TdApi.AuthorizationStateWaitPassword -> AuthorizationState.WaitPassword(this.passwordHint)
            else -> null
        }
}