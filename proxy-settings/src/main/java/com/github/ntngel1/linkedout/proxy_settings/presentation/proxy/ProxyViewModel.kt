package com.github.ntngel1.linkedout.proxy_settings.presentation.proxy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.lib_utils.base.MviViewModel
import com.github.ntngel1.linkedout.lib_utils.skipWhitespaces
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy.GetProxyUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.ping_proxy.PingProxyUseCase
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.passwordOrNull
import com.github.ntngel1.linkedout.proxy_settings.entity.secretOrNull
import com.github.ntngel1.linkedout.proxy_settings.entity.usernameOrNull
import com.github.ntngel1.linkedout.proxy_settings.presentation.proxy.enums.ProxyType
import kotlinx.coroutines.launch

class ProxyViewModel @ViewModelInject constructor(
    private val getProxy: GetProxyUseCase,
    private val pingProxy: PingProxyUseCase
) : MviViewModel<ProxyState, Nothing>() {

    fun setup(proxyId: Int?) {
        updateState(ProxyState(proxyId = proxyId))
        loadProxy()
    }

    private fun loadProxy() = viewModelScope.launch {
        val proxy = currentState.proxyId?.let { proxyId ->
            // getting existing proxy
            getProxy(proxyId)
        } ?: ProxyEntity.Http() // else creating new proxy

        val proxyType = when (proxy) {
            is ProxyEntity.Http -> ProxyType.HTTP
            is ProxyEntity.Socks5 -> ProxyType.SOCKS5
            is ProxyEntity.MtProto -> ProxyType.MT_PROTO
        }

        updateState {
            ProxyState(
                savedProxyHostname = proxy.hostname,
                savedProxyPort = proxy.port,
                savedProxyType = proxyType,
                savedProxyUsername = proxy.usernameOrNull(),
                savedProxyPassword = proxy.passwordOrNull(),
                savedProxySecret = proxy.secretOrNull(),
                isPingProxyButtonVisible = true,
                isSecretInputVisible = shouldShowSecretInput(proxyType),
                isUsernameAndPasswordInputsVisible = shouldShowUsernameAndPasswordInputs(proxyType)
            )
        }
    }

    fun onProxyTypeChanged(proxyType: ProxyType) {
        updateState { state ->
            state.copy(
                newProxyType = proxyType,
                isUsernameAndPasswordInputsVisible = shouldShowUsernameAndPasswordInputs(proxyType),
                isSecretInputVisible = shouldShowSecretInput(proxyType),
                isPingProxyButtonVisible = true,
                isProxyPingVisible = false,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyType = proxyType)
            )
        }
    }

    fun onProxyHostnameChanged(hostname: CharSequence, cursorPosition: Int) {
        val hostname = hostname.toString().skipWhitespaces()
        updateState { state ->
            state.copy(
                newProxyHostname = hostname,
                hostnameCursorPosition = cursorPosition,
                isProxyPingVisible = false,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyHostname = hostname)
            )
        }
    }

    fun onProxyPortChanged(port: CharSequence, cursorPosition: Int) {
        val portInt = port.toString().toIntOrNull()
            ?: 0

        val normalizedPort = when {
            portInt < MIN_PORT -> MIN_PORT
            portInt > MAX_PORT -> MAX_PORT
            else -> portInt
        }

        updateState { state ->
            state.copy(
                newProxyPort = normalizedPort,
                isProxyPingVisible = false,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyPort = normalizedPort),
                portCursorPosition = cursorPosition
            )
        }
    }

    fun onProxyUsernameChanged(username: CharSequence, cursorPosition: Int) {
        val username = username.toString().skipWhitespaces()
        updateState { state ->
            state.copy(
                newProxyUsername = username,
                isProxyPingVisible = false,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyUsername = username),
                usernameCursorPosition = cursorPosition
            )
        }
    }

    fun onProxyPasswordChanged(password: CharSequence, cursorPosition: Int) {
        val password = password.toString().skipWhitespaces()
        updateState { state ->
            state.copy(
                newProxyPassword = password,
                isProxyPingVisible = false,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyPassword = password),
                passwordCursorPosition = cursorPosition
            )
        }
    }

    fun onProxySecretChanged(secret: CharSequence, cursorPosition: Int) {
        val secret = secret.toString().skipWhitespaces()
        updateState { state ->
            state.copy(
                newProxySecret = secret,
                isProxyPingVisible = false,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxySecret = secret),
                secretCursorPosition = cursorPosition
            )
        }
    }

    // TODO: Validation
    fun onPingProxyClicked() = viewModelScope.launch {
        updateState { state ->
            state.copy(
                isPingProxyButtonVisible = false,
                isProxyPingVisible = false,
                isPingingProxy = true,
                proxyPingLatencyMs = null,
                proxyPingStability = null
            )
        }

        val state = currentState
        val proxy = when (state.newProxyType!!) {
            ProxyType.HTTP -> ProxyEntity.Http(
                hostname = state.newProxyHostname!!,
                port = state.newProxyPort!!,
                username = state.newProxyUsername.orEmpty(),
                password = state.newProxyPassword.orEmpty()
            )
            ProxyType.SOCKS5 -> ProxyEntity.Socks5(
                hostname = state.newProxyHostname!!,
                port = state.newProxyPort!!,
                username = state.newProxyUsername.orEmpty(),
                password = state.newProxyPassword.orEmpty()
            )
            ProxyType.MT_PROTO -> ProxyEntity.MtProto(
                hostname = state.newProxyHostname!!,
                port = state.newProxyPort!!,
                secret = state.newProxySecret!!
            )
        }

        val proxyPing = pingProxy(proxy)

        updateState { state ->
            state.copy(
                isPingProxyButtonVisible = true,
                isProxyPingVisible = true,
                isPingingProxy = false,
                proxyPingLatencyMs = proxyPing.latencyMs,
                proxyPingStability = proxyPing.stability
            )
        }
    }

    fun onSaveClicked() {

    }

    private fun ProxyState.shouldShowSaveButton(
        newProxyType: ProxyType? = this.newProxyType,
        newProxyHostname: String? = this.newProxyHostname,
        newProxyPort: Int? = this.newProxyPort,
        newProxyUsername: String? = this.newProxyUsername,
        newProxyPassword: String? = this.newProxyPassword,
        newProxySecret: String? = this.newProxySecret
    ): Boolean {
        if (newProxyType != savedProxyType) return true

        return when (newProxyType) {
            ProxyType.HTTP, ProxyType.SOCKS5 -> newProxyHostname != savedProxyHostname ||
                    newProxyPort != savedProxyPort ||
                    newProxyUsername != savedProxyUsername ||
                    newProxyPassword != savedProxyPassword
            ProxyType.MT_PROTO -> newProxyHostname != savedProxyHostname ||
                    newProxyPort != savedProxyPort ||
                    newProxySecret != savedProxySecret
            else -> false
        }
    }

    private fun shouldShowUsernameAndPasswordInputs(proxyType: ProxyType) =
        proxyType == ProxyType.HTTP || proxyType == ProxyType.SOCKS5

    private fun shouldShowSecretInput(proxyType: ProxyType) =
        proxyType == ProxyType.MT_PROTO

    companion object {
        private const val MIN_PORT = 1
        private const val MAX_PORT = 65535
    }
}