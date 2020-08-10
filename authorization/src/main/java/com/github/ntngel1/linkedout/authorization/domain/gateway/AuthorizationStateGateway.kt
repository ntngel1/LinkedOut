package com.github.ntngel1.linkedout.authorization.domain.gateway

import com.github.ntngel1.linkedout.authorization.entity.AuthorizationState
import kotlinx.coroutines.flow.Flow

interface AuthorizationStateGateway {
    fun observeAuthorizationStateChanges(): Flow<AuthorizationState>
}