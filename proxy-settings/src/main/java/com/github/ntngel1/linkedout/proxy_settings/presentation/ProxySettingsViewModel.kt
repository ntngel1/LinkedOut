package com.github.ntngel1.linkedout.proxy_settings.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.lib_extensions.BaseViewModel
import com.github.ntngel1.linkedout.lib_extensions.skipWhitespaces
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings.GetProxySettingsUseCase
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType
import kotlinx.coroutines.launch

class ProxySettingsViewModel @ViewModelInject constructor(
    private val getProxySettings: GetProxySettingsUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<ProxySettingsState>(ProxySettingsState())
    val state: LiveData<ProxySettingsState> = _state

    init {
        loadProxySettings()
    }

    private fun loadProxySettings() = viewModelScope.launch {
        val proxySettings = getProxySettings()
        changeState {
            ProxySettingsState(
                savedProxyHostname = proxySettings.proxyHostname,
                savedProxyPort = proxySettings.proxyPort,
                savedProxyType = proxySettings.proxyType,
                isHostnameAndPortInputsVisible = shouldShowHostnameAndPortInputs(proxySettings.proxyType),
                isSecretInputVisible = shouldShowSecretInput(proxySettings.proxyType),
                isPingProxyButtonVisible = shouldShowPingProxyButton(proxySettings.proxyType),
                isUsernameAndPasswordInputsVisible = shouldShowUsernameAndPasswordInputs(
                    proxySettings.proxyType
                )
            )
        }
    }

    fun onProxyTypeChanged(proxyType: ProxyType) {
        changeState { state ->
            state.copy(
                newProxyType = proxyType,
                isHostnameAndPortInputsVisible = shouldShowHostnameAndPortInputs(proxyType),
                isUsernameAndPasswordInputsVisible = shouldShowUsernameAndPasswordInputs(proxyType),
                isSecretInputVisible = shouldShowSecretInput(proxyType),
                isPingProxyButtonVisible = shouldShowPingProxyButton(proxyType),
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

    fun onPingProxyClicked() {

    }

    fun onSaveClicked() {
        TODO("Not yet implemented")
    }

    private fun ProxySettingsState.shouldShowSaveButton(
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

    private fun shouldShowHostnameAndPortInputs(proxyType: ProxyType) =
        proxyType != ProxyType.NO_PROXY

    private fun shouldShowUsernameAndPasswordInputs(proxyType: ProxyType) =
        proxyType == ProxyType.HTTP || proxyType == ProxyType.SOCKS5

    private fun shouldShowSecretInput(proxyType: ProxyType) =
        proxyType == ProxyType.MT_PROTO

    private fun shouldShowPingProxyButton(proxyType: ProxyType) =
        proxyType != ProxyType.NO_PROXY

    private fun changeState(updater: (state: ProxySettingsState) -> ProxySettingsState) {
        _state.value = _state.value?.let(updater)
    }

    companion object {
        private const val MIN_PORT = 1
        private const val MAX_PORT = 65535
    }
}