package com.github.ntngel1.linkedout.proxy_settings.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.lib_extensions.BaseViewModel
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
                isProxyInputsVisible = shouldShowProxyInputs(proxySettings.proxyType)
            )
        }
    }

    fun onProxyTypeChanged(proxyType: ProxyType) {
        changeState { state ->
            state.copy(
                newProxyType = proxyType,
                isProxyInputsVisible = shouldShowProxyInputs(proxyType),
                isSaveButtonVisible = state.shouldShowSaveButton(newProxyType = proxyType)
            )
        }
    }

    fun onSaveClicked() {
        TODO("Not yet implemented")
    }

    fun onProxyHostnameChanged(hostname: CharSequence, cursorPosition: Int) {
        val hostname = hostname.toString().replace(" ", "")
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

    private fun ProxySettingsState.shouldShowSaveButton(
        newProxyType: ProxyType? = this.newProxyType,
        newProxyHostname: String? = this.newProxyHostname,
        newProxyPort: Int? = this.newProxyPort
    ) = if (newProxyType == ProxyType.NO_PROXY) {
        newProxyType != savedProxyType
    } else {
        newProxyType != savedProxyType ||
                newProxyHostname != savedProxyHostname ||
                newProxyPort != savedProxyPort
    }

    private fun shouldShowProxyInputs(proxyType: ProxyType) =
        proxyType != ProxyType.NO_PROXY

    private fun changeState(updater: (state: ProxySettingsState) -> ProxySettingsState) {
        _state.value = _state.value?.let(updater)
    }

    companion object {
        private const val MIN_PORT = 1
        private const val MAX_PORT = 65535
    }
}