package com.github.ntngel1.linkedout.authorization.domain.usecase.send_authorization_confirmation_code

interface SendAuthorizationConfirmationCodeUseCase {
    suspend operator fun invoke(phoneNumber: String)
}