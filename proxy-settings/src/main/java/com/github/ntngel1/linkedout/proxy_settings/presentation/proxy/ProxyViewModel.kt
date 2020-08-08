package com.github.ntngel1.linkedout.proxy_settings.presentation.proxy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.lib_extensions.BaseViewModel
import com.github.ntngel1.linkedout.lib_extensions.skipWhitespaces
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy.GetProxyUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.ping_proxy.PingProxyUseCase
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity
import com.github.ntngel1.linkedout.proxy_settings.presentation.proxy.enums.ProxyType
import kotlinx.coroutines.launch
import timber.log.Timber

class ProxyViewModel @ViewModelInject constructor(
    private val getProxy: GetProxyUseCase,
    private val pingProxy: PingProxyUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<ProxyState>(ProxyState())
    private val currentState: ProxyState
        get() = _state.value!!
    val state: LiveData<ProxyState> = _state

    fun setup(proxyId: Int) {
        changeState { state ->
            state.copy(proxyId = proxyId)
        }

        loadProxy()
    }

    private fun loadProxy() = viewModelScope.launch {
        val proxy = getProxy(currentState.proxyId)
        val proxyType = when (proxy) {
            is ProxyEntity.Http -> ProxyType.HTTP
            is ProxyEntity.Socks5 -> ProxyType.SOCKS5
            is ProxyEntity.MtProto -> ProxyType.MT_PROTO
        }

        changeState {
            ProxyState(
                savedProxyHostname = proxy.hostname,
                savedProxyPort = proxy.port,
                savedProxyType = proxyType,
                savedProxyUsername = when (proxy) {
                    is ProxyEntity.Http -> proxy.username
                    is ProxyEntity.Socks5 -> proxy.username
                    is ProxyEntity.MtProto -> null
                },
                savedProxyPassword = when (proxy) {
                    is ProxyEntity.Http -> proxy.password
                    is ProxyEntity.Socks5 -> proxy.password
                    is ProxyEntity.MtProto -> null
                },
                savedProxySecret = when (proxy) {
                    is ProxyEntity.MtProto -> proxy.secret
                    is ProxyEntity.Http -> null
                    is ProxyEntity.Socks5 -> null
                },
                isPingProxyButtonVisible = true,
                isSecretInputVisible = shouldShowSecretInput(proxyType),
                isUsernameAndPasswordInputsVisible = shouldShowUsernameAndPasswordInputs(proxyType)
            )
        }
    }

    fun onProxyTypeChanged(proxyType: ProxyType) {
        changeState { state ->
            state.copy(
                newProxyType = proxyType,
                isUsernameAndPasswordInputsVisible = shouldShowUsernameAndPasswordInputs(proxyType),
                isSecretInputVisible = shouldShowSecretInput(proxyType),
                isPingProxyButtonVisible = true,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyType = proxyType)
            )
        }
    }

    fun onProxyHostnameChanged(hostname: CharSequence, cursorPosition: Int) {
        val hostname = hostname.toString().skipWhitespaces()
        changeState { state ->
            state.copy(
                newProxyHostname = hostname,
                hostnameCursorPosition = cursorPosition,
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

        changeState { state ->
            state.copy(
                newProxyPort = normalizedPort,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyPort = normalizedPort),
                portCursorPosition = cursorPosition
            )
        }
    }

    fun onProxyUsernameChanged(username: CharSequence, cursorPosition: Int) {
        val username = username.toString().skipWhitespaces()
        changeState { state ->
            state.copy(
                newProxyUsername = username,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyUsername = username),
                usernameCursorPosition = cursorPosition
            )
        }
    }

    fun onProxyPasswordChanged(password: CharSequence, cursorPosition: Int) {
        val password = password.toString().skipWhitespaces()
        changeState { state ->
            state.copy(
                newProxyPassword = password,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyPassword = password),
                passwordCursorPosition = cursorPosition
            )
        }
    }

    fun onProxySecretChanged(secret: CharSequence, cursorPosition: Int) {
        val secret = secret.toString().skipWhitespaces()
        changeState { state ->
            state.copy(
                newProxySecret = secret,
                isSaveButtonVisible = state.shouldShowSaveButton(newProxySecret = secret),
                secretCursorPosition = cursorPosition
            )
        }
    }

    fun onPingProxyClicked() = viewModelScope.launch {
        changeState { state ->
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

        changeState { state ->
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

    private fun changeState(updater: (state: ProxyState) -> ProxyState) {
        _state.value = _state.value?.let(updater)
    }

    companion object {
        private const val MIN_PORT = 1
        private const val MAX_PORT = 65535
    }
}