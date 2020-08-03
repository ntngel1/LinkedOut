package com.github.ntngel1.linkedout.proxy_settings.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.render
import com.github.ntngel1.linkedout.lib_extensions.BaseFragment
import com.github.ntngel1.linkedout.lib_ui.items.ButtonItem
import com.github.ntngel1.linkedout.lib_ui.items.SingleSelectDropdownItem
import com.github.ntngel1.linkedout.lib_ui.items.TextInputItem
import com.github.ntngel1.linkedout.proxy_settings.R
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_proxy_settings.*

@AndroidEntryPoint
class ProxySettingsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_proxy_settings

    private val viewModel: ProxySettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.state.observe(::render)
    }

    private fun setupRecyclerView() {
        recyclerview_proxy_settings.setHasFixedSize(true)
    }

    private fun render(state: ProxySettingsState) = recyclerview_proxy_settings.render {
        state.savedProxySettings?.let { proxySettings ->
            SingleSelectDropdownItem(
                id = "proxy_type_dropdown",
                title = "Proxy Type",
                entries = ProxyType.values().map { it.name },
                selectedEntryIndex = proxySettings.proxyType.ordinal,
                onEntrySelected = callback1(static = true) { proxyTypeString ->
                    viewModel.onProxyTypeChanged(proxyTypeString)
                }
            ).render()
        }

        if (state.isProxyInputsVisible && state.newProxySettings != null) {
            TextInputItem(
                id = "proxy_hostname_text_input",
                hint = "Hostname",
                text = state.newProxySettings.proxyHostname.orEmpty(),
                onTextChanged = callback1(static = true) { hostname ->
                    viewModel.onProxyHostnameChanged(hostname)
                }
            ).render()
        }

        if (state.isSaveButtonVisible) {
            ButtonItem(
                id = "save_button",
                text = "Save",
                onClicked = callback(static = true) {
                    viewModel.onSaveClicked()
                }
            ).render()
        }
    }
}