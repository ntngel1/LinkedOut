package com.github.ntngel1.linkedout.authorization.domain.usecase.get_recent_sent_authorization_confirmation_code_info

import com.github.ntngel1.linkedout.authorization.domain.gateway.SentAuthorizationConfirmationCodeInfoGateway
import com.github.ntngel1.linkedout.authorization.entity.SentAuthorizationConfirmationCodeInfoEntity
import javax.inject.Inject

class GetRecentSentAuthorizationConfirmationCodeInfoUseCaseImp @Inject constructor(
    private val sentAuthorizationConfirmationCodeInfoGateway: SentAuthorizationConfirmationCodeInfoGateway
) : GetRecentSentAuthorizationConfirmationCodeInfoUseCase {

    override suspend fun invoke(): SentAuthorizationConfirmationCodeInfoEntity {
        TODO("Not yet implemented")
    }
}