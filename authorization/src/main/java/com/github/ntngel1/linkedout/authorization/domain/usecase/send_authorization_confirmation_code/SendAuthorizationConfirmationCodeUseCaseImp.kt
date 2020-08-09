package com.github.ntngel1.linkedout.authorization.domain.usecase.send_authorization_confirmation_code

import com.github.ntngel1.linkedout.authorization.domain.gateway.AuthorizationConfirmationCodeGateway
import javax.inject.Inject

// TODO: Error handling, phone number pattern, etc.
class SendAuthorizationConfirmationCodeUseCaseImp @Inject constructor(
    private val authorizationConfirmationCodeGateway: AuthorizationConfirmationCodeGateway
) : SendAuthorizationConfirmationCodeUseCase {

    override suspend fun invoke(phoneNumber: String) {
        authorizationConfirmationCodeGateway.sendConfirmationCode(phoneNumber)
    }
}