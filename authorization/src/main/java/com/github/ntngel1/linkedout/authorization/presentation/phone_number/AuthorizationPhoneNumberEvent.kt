package com.github.ntngel1.linkedout.authorization.presentation.phone_number

sealed class AuthorizationPhoneNumberEvent {
    object ShowAuthorizationConfirmationCodeScreen : AuthorizationPhoneNumberEvent()
}