package com.github.ntngel1.linkedout.authorization.domain.gateway

import com.github.ntngel1.linkedout.authorization.entity.SentAuthorizationConfirmationCodeInfoEntity

interface SentAuthorizationConfirmationCodeInfoGateway {
    suspend fun getMostRecent(): SentAuthorizationConfirmationCodeInfoEntity
    suspend fun save(info: SentAuthorizationConfirmationCodeInfoEntity)
    suspend fun removeMostRecent()
}