package com.github.ntngel1.linkedout.authorization.di

import com.github.ntngel1.linkedout.authorization.data.SentAuthorizationConfirmationCodeInfoGatewayImp
import com.github.ntngel1.linkedout.authorization.domain.gateway.SentAuthorizationConfirmationCodeInfoGateway
import com.github.ntngel1.linkedout.authorization.domain.usecase.get_recent_sent_authorization_confirmation_code_info.GetRecentSentAuthorizationConfirmationCodeInfoUseCase
import com.github.ntngel1.linkedout.authorization.domain.usecase.get_recent_sent_authorization_confirmation_code_info.GetRecentSentAuthorizationConfirmationCodeInfoUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthorizationConfirmationCodeModule {

    @Binds
    abstract fun bindSentAuthorizationConfirmationCodeInfoGateway(
        sentAuthorizationConfirmationCodeInfoGatewayImp: SentAuthorizationConfirmationCodeInfoGatewayImp
    ): SentAuthorizationConfirmationCodeInfoGateway

    @Binds
    abstract fun bindGetRecentSentAuthorizationConfirmationCodeInfoUseCase(
        getRecentSentAuthorizationConfirmationCodeInfoUseCaseImp: GetRecentSentAuthorizationConfirmationCodeInfoUseCaseImp
    ): GetRecentSentAuthorizationConfirmationCodeInfoUseCase
}