package com.github.ntngel1.linkedout.authorization.di

import com.github.ntngel1.linkedout.authorization.domain.usecase.observe_authorization_state.ObserveAuthorizationStateUseCase
import com.github.ntngel1.linkedout.authorization.domain.usecase.observe_authorization_state.ObserveAuthorizationStateUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class AuthorizationFlowModule {

    @Binds
    abstract fun bindObserveAuthorizationStateUseCase(
        observeAuthorizationStateUseCaseImp: ObserveAuthorizationStateUseCaseImp
    ): ObserveAuthorizationStateUseCase
}