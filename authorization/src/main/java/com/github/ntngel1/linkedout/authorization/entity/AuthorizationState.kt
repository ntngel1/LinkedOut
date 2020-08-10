package com.github.ntngel1.linkedout.authorization.entity

sealed class AuthorizationState {

    object WaitPhoneNumber : AuthorizationState()

    data class WaitConfirmationCode(
        val phoneNumber: String,
        val confirmationCodeLength: Int
    ) : AuthorizationState()

    data class WaitPassword(
        val passwordHint: String
    ) : AuthorizationState()
}