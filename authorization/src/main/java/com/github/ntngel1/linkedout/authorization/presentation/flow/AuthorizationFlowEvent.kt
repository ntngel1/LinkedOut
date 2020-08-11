package com.github.ntngel1.linkedout.authorization.presentation.flow

sealed class AuthorizationFlowEvent {

    object ShowPhoneNumberScreen : AuthorizationFlowEvent()

    data class ShowConfirmationCodeScreen(
        val phoneNumber: String,
        val confirmationCodeLength: Int
    ) : AuthorizationFlowEvent()
}