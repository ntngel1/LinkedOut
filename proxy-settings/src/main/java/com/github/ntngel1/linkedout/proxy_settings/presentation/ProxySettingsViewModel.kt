package com.github.ntngel1.linkedout.proxy_settings.presentation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.lib_extensions.BaseViewModel
import com.github.ntngel1.linkedout.proxy_settings.domain.repository.ProxySettingsRepository
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings.GetProxySettingsUseCase
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class ProxySettingsViewModel @ViewModelInject constructor(
    private val okHttpClient: OkHttpClient,
    private val getProxySettings: GetProxySettingsUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<ProxySettingsState>(ProxySettingsState())
    val state: LiveData<ProxySettingsState> = _state

    init {
        loadProxySettings()
    }

    private fun loadProxySettings() = viewModelScope.launch {
        val proxySettings = getProxySettings()
        _state.value = ProxySettingsState(savedProxySettings = proxySettings, isProxyInputsVisible = true)
    }

    fun onProxyTypeChanged(proxyTypeString: String) {
        val proxyType = ProxyType.valueOf(proxyTypeString)
        val newProxySettings = _state.value!!.newProxySettings!!.copy(proxyType = proxyType)
        _state.value = _state.value!!.copy(
            isSaveButtonVisible = newProxySettings != _state.value!!.savedProxySettings,
            newProxySettings = newProxySettings
        )
    }

    fun onSaveClicked() {
        TODO("Not yet implemented")
    }

    fun onProxyHostnameChanged(hostname: CharSequence) {
        val newProxySettings = _state.value!!.newProxySettings!!.copy(
            proxyHostname = hostname.toString()
        )

        _state.value = _state.value!!.copy(
            isSaveButtonVisible = newProxySettings != _state.value!!.savedProxySettings,
            newProxySettings = newProxySettings
        )
    }
}