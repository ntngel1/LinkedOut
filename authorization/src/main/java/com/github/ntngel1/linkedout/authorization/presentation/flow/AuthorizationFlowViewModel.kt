package com.github.ntngel1.linkedout.authorization.presentation.flow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.authorization.domain.usecase.observe_authorization_state.ObserveAuthorizationStateUseCase
import com.github.ntngel1.linkedout.authorization.entity.AuthorizationState
import com.github.ntngel1.linkedout.lib_utils.base.MviViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthorizationFlowViewModel @ViewModelInject constructor(
    private val observeAuthorizationState: ObserveAuthorizationStateUseCase
) : MviViewModel<Nothing, AuthorizationFlowEvent>() {

    init {
        startObservingAuthorizationState()
    }

    private fun startObservingAuthorizationState() = viewModelScope.launch {
        observeAuthorizationState()
            .collect { authorizationState ->
                handleAuthorizationState(authorizationState)
            }
    }

    private fun handleAuthorizationState(authorizationState: AuthorizationState) {
        when (authorizationState) {
            is AuthorizationState.WaitPhoneNumber -> {
                sendEvent(AuthorizationFlowEvent.ShowPhoneNumberScreen)
            }
            is AuthorizationState.WaitConfirmationCode -> {
                sendEvent(
                    AuthorizationFlowEvent.ShowConfirmationCodeScreen(
                        phoneNumber = authorizationState.phoneNumber,
                        confirmationCodeLength = authorizationState.confirmationCodeLength
                    )
                )
            }
        }
    }
}