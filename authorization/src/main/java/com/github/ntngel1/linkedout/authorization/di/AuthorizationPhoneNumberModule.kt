package com.github.ntngel1.linkedout.authorization.di

import com.github.ntngel1.linkedout.authorization.data.AuthorizationConfirmationCodeGatewayImp
import com.github.ntngel1.linkedout.authorization.domain.gateway.AuthorizationConfirmationCodeGateway
import com.github.ntngel1.linkedout.authorization.domain.usecase.send_authorization_confirmation_code.SendAuthorizationConfirmationCodeUseCase
import com.github.ntngel1.linkedout.authorization.domain.usecase.send_authorization_confirmation_code.SendAuthorizationConfirmationCodeUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthorizationPhoneNumberModule {

    @Binds
    abstract fun bindAuthorizationConfirmationCodeGateway(
        authorizationConfirmationCodeGatewayImp: AuthorizationConfirmationCodeGatewayImp
    ): AuthorizationConfirmationCodeGateway

    @Binds
    abstract fun bindSendAuthorizationConfirmationCodeUseCase(
        sendAuthorizationConfirmationCodeUseCaseImp: SendAuthorizationConfirmationCodeUseCaseImp
    ): SendAuthorizationConfirmationCodeUseCase
}