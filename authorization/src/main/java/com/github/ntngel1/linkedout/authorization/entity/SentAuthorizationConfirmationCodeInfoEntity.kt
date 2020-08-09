package com.github.ntngel1.linkedout.authorization.entity

data class SentAuthorizationConfirmationCodeInfoEntity(
    val phoneNumber: String,
    val confirmationCodeLength: Int
)