package com.github.ntngel1.linkedout.authorization.presentation.confirmation_code

import androidx.hilt.lifecycle.ViewModelInject
import com.github.ntngel1.linkedout.authorization.domain.usecase.check_authorization_confirmation_code.CheckAuthorizationConfirmationCodeUseCase
import com.github.ntngel1.linkedout.lib_utils.MviViewModel

class AuthorizationConfirmationCodeViewModel @ViewModelInject constructor(
    private val checkAuthorizationConfirmationCode: CheckAuthorizationConfirmationCodeUseCase
) : MviViewModel<AuthorizationConfirmationCodeState, AuthorizationConfirmationCodeEvent>() {

    fun setup(phoneNumber: String, confirmationCodeLength: Int) {

    }
}