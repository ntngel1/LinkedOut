package com.github.ntngel1.linkedout.authorization.presentation.confirmation_code

data class AuthorizationConfirmationCodeState(
    val phoneNumber: String? = null,
    val confirmationCodeLength: Int? = null
)