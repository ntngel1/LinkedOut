package com.github.ntngel1.linkedout.authorization.domain.gateway

interface AuthorizationConfirmationCodeGateway {
    suspend fun sendConfirmationCode(phoneNumber: String)
    suspend fun checkConfirmationCode(confirmationCode: String)
}