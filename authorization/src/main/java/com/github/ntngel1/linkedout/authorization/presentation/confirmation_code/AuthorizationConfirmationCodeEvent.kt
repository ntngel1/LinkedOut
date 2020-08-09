package com.github.ntngel1.linkedout.authorization.presentation.confirmation_code

sealed class AuthorizationConfirmationCodeEvent {
    object ShowAuthorizationRegistrationScreen : AuthorizationConfirmationCodeEvent()
    object ShowAuthorizationPasswordScreen : AuthorizationConfirmationCodeEvent()
    object OnAuthorized : AuthorizationConfirmationCodeEvent()
}