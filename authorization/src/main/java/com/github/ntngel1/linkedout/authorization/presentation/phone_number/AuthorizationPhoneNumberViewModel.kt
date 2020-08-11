package com.github.ntngel1.linkedout.authorization.presentation.phone_number

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.authorization.domain.usecase.send_authorization_confirmation_code.SendAuthorizationConfirmationCodeUseCase
import com.github.ntngel1.linkedout.lib_utils.base.MviViewModel
import kotlinx.coroutines.launch

class AuthorizationPhoneNumberViewModel @ViewModelInject constructor(
    private val sendAuthorizationConfirmationCode: SendAuthorizationConfirmationCodeUseCase
) : MviViewModel<Nothing, AuthorizationPhoneNumberEvent>() {

    fun onContinueClicked(phoneNumber: String) = viewModelScope.launch {
        sendAuthorizationConfirmationCode(phoneNumber)
        sendEvent(AuthorizationPhoneNumberEvent.ShowAuthorizationConfirmationCodeScreen)
    }
}