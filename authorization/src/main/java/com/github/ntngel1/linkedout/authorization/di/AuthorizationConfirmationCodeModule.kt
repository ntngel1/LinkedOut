package com.github.ntngel1.linkedout.authorization.di

import com.github.ntngel1.linkedout.authorization.domain.usecase.check_authorization_confirmation_code.CheckAuthorizationConfirmationCodeUseCase
import com.github.ntngel1.linkedout.authorization.domain.usecase.check_authorization_confirmation_code.CheckAuthorizationConfirmationCodeUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthorizationConfirmationCodeModule {

    @Binds
    abstract fun bindCheckAuthorizationConfirmationCodeUseCase(
        checkAuthorizationConfirmationCodeUseCaseImp: CheckAuthorizationConfirmationCodeUseCaseImp
    ): CheckAuthorizationConfirmationCodeUseCase
}