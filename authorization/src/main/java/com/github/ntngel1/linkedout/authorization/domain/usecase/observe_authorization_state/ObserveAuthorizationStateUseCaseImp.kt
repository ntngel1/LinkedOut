package com.github.ntngel1.linkedout.authorization.domain.usecase.observe_authorization_state

import com.github.ntngel1.linkedout.authorization.domain.gateway.AuthorizationStateGateway
import com.github.ntngel1.linkedout.authorization.entity.AuthorizationState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthorizationStateUseCaseImp @Inject constructor(
    private val authorizationStateGateway: AuthorizationStateGateway
) : ObserveAuthorizationStateUseCase {

    override fun invoke(): Flow<AuthorizationState> =
        authorizationStateGateway.observeAuthorizationStateChanges()
}