package com.github.ntngel1.linkedout.proxy_settings.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.github.ntngel1.linkedout.lib_extensions.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class ProxySettingsViewModel @ViewModelInject constructor(
    private val okHttpClient: OkHttpClient
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            okHttpClient.newCall(
                Request.Builder().get().url("https://google.com").build()
            ).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}