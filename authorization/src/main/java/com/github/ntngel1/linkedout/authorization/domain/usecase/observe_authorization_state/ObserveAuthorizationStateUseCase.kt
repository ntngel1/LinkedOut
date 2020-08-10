package com.github.ntngel1.linkedout.authorization.domain.usecase.observe_authorization_state

import com.github.ntngel1.linkedout.authorization.entity.AuthorizationState
import kotlinx.coroutines.flow.Flow

interface ObserveAuthorizationStateUseCase {
    operator fun invoke(): Flow<AuthorizationState>
}