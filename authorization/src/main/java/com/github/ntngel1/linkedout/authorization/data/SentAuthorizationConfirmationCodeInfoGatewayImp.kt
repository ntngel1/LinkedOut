package com.github.ntngel1.linkedout.authorization.data

import com.github.ntngel1.linkedout.authorization.data.room.dao.SentAuthorizationConfirmationCodeInfoDao
import com.github.ntngel1.linkedout.authorization.domain.gateway.SentAuthorizationConfirmationCodeInfoGateway
import com.github.ntngel1.linkedout.authorization.entity.SentAuthorizationConfirmationCodeInfoEntity
import javax.inject.Inject

class SentAuthorizationConfirmationCodeInfoGatewayImp @Inject constructor(
    private val sentAuthorizationConfirmationCodeInfoDao: SentAuthorizationConfirmationCodeInfoDao
) : SentAuthorizationConfirmationCodeInfoGateway {

    override suspend fun getMostRecent(): SentAuthorizationConfirmationCodeInfoEntity {
        TODO("Not yet implemented")
    }

    override suspend fun save(info: SentAuthorizationConfirmationCodeInfoEntity) {
        TODO("Not yet implemented")
    }
}