package com.github.ntngel1.linkedout.authorization.domain.gateway

interface AuthorizationConfirmationCodeGateway {
    suspend fun send(phoneNumber: String)
}