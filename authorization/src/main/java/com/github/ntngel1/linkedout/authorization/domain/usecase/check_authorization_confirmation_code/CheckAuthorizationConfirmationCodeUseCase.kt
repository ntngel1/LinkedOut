package com.github.ntngel1.linkedout.authorization.domain.usecase.check_authorization_confirmation_code

interface CheckAuthorizationConfirmationCodeUseCase {
    suspend operator fun invoke(confirmationCode: String)
}