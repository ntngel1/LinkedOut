package com.github.ntngel1.linkedout.authorization.domain.usecase.check_authorization_confirmation_code

import com.github.ntngel1.linkedout.authorization.domain.gateway.AuthorizationConfirmationCodeGateway
import javax.inject.Inject

class CheckAuthorizationConfirmationCodeUseCaseImp @Inject constructor(
    private val authorizationConfirmationCodeGateway: AuthorizationConfirmationCodeGateway
) : CheckAuthorizationConfirmationCodeUseCase {

    override suspend fun invoke(confirmationCode: String) {
        authorizationConfirmationCodeGateway.checkConfirmationCode(confirmationCode)
    }
}