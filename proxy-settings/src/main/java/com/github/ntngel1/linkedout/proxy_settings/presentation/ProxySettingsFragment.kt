package com.github.ntngel1.linkedout.proxy_settings.presentation

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback2
import com.github.ntngel1.linkedout.lib_delegate_adapter.item_decorations.SpacingItemDecoration
import com.github.ntngel1.linkedout.lib_delegate_adapter.render
import com.github.ntngel1.linkedout.lib_extensions.BaseFragment
import com.github.ntngel1.linkedout.lib_extensions.dp
import com.github.ntngel1.linkedout.lib_extensions.string
import com.github.ntngel1.linkedout.lib_ui.items.ButtonItem
import com.github.ntngel1.linkedout.lib_ui.items.SingleSelectDropdownItem
import com.github.ntngel1.linkedout.lib_ui.items.text_input.TextInputItem
import com.github.ntngel1.linkedout.proxy_settings.R
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_proxy_settings.*
import java.lang.IllegalStateException

@AndroidEntryPoint
class ProxySettingsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_proxy_settings

    private val viewModel: ProxySettingsViewModel by viewModels()
    private val spacingItemDecoration = SpacingItemDecoration()

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
        recyclerview_proxy_settings.addItemDecoration(spacingItemDecoration)
    }

    private fun render(
        state: ProxySettingsState
    ) = recyclerview_proxy_settings.render(spacingItemDecoration = spacingItemDecoration) {
        state.newProxyType?.let { proxyType ->
            SingleSelectDropdownItem(
                id = "proxy_type_dropdown",
                hint = string(R.string.proxy_settings_proxy_type),
                entries = listOf(
                    string(R.string.proxy_settings_proxy_type_no_proxy),
                    string(R.string.proxy_settings_proxy_type_http),
                    string(R.string.proxy_settings_proxy_type_socks),
                    string(R.string.proxy_settings_proxy_type_mt_proto)
                ),
                selectedEntryIndex = proxyType.ordinal,
                onEntrySelected = callback2(static = true) { _, proxyTypeIndex ->
                    val proxyType = when (proxyTypeIndex) {
                        0 -> ProxyType.NO_PROXY
                        1 -> ProxyType.HTTP
                        2 -> ProxyType.SOCKS5
                        3 -> ProxyType.MT_PROTO
                        else -> throw IllegalStateException("No such ProxyType value for index $proxyTypeIndex")
                    }

                    viewModel.onProxyTypeChanged(proxyType)
                }
            ).render()

            spacing(8.dp)
        }

        if (state.isHostnameAndPortInputsVisible) {
            TextInputItem(
                id = "proxy_hostname_text_input",
                hint = string(R.string.proxy_settings_hostname),
                text = state.newProxyHostname.orEmpty(),
                cursorPosition = state.hostnameCursorPosition,
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS,
                onTextChanged = callback2(
                    static = true,
                    listener = viewModel::onProxyHostnameChanged
                )
            ).render()

            spacing(8.dp)

            TextInputItem(
                id = "proxy_port_text_input",
                hint = string(R.string.proxy_settings_port),
                inputType = InputType.TYPE_CLASS_NUMBER,
                text = state.newProxyPort?.toString().orEmpty(),
                cursorPosition = state.portCursorPosition,
                onTextChanged = callback2(static = true, listener = viewModel::onProxyPortChanged)
            ).render()

            spacing(8.dp)
        }

        if (state.isUsernameAndPasswordInputsVisible) {
            TextInputItem(
                id = "proxy_username_text_input",
                hint = string(R.string.proxy_settings_username),
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS,
                text = state.newProxyUsername.orEmpty(),
                cursorPosition = state.usernameCursorPosition,
                onTextChanged = callback2(
                    static = true,
                    listener = viewModel::onProxyUsernameChanged
                )
            ).render()

            spacing(8.dp)

            TextInputItem(
                id = "proxy_password_text_input",
                hint = string(R.string.proxy_settings_password),
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
                text = state.newProxyPassword.orEmpty(),
                cursorPosition = state.passwordCursorPosition,
                onTextChanged = callback2(
                    static = true,
                    listener = viewModel::onProxyPasswordChanged
                )
            ).render()

            spacing(8.dp)
        }

        if (state.isSecretInputVisible) {
            TextInputItem(
                id = "proxy_secret_text_input",
                hint = string(R.string.proxy_settings_secret),
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
                text = state.newProxySecret.orEmpty(),
                cursorPosition = state.secretCursorPosition,
                onTextChanged = callback2(
                    static = true,
                    listener = viewModel::onProxySecretChanged
                )
            ).render()

            spacing(8.dp)
        }

        if (state.isPingProxyButtonVisible) {
            ButtonItem(
                id = "ping_proxy_button",
                text = string(R.string.proxy_settings_ping_proxy),
                style = ButtonItem.Style.OUTLINED,
                onClicked = callback(static = true, listener = viewModel::onPingProxyClicked)
            ).render()

            spacing(8.dp)
        }

        if (state.isSaveButtonVisible) {
            ButtonItem(
                id = "save_button",
                text = "Save",
                onClicked = callback(static = true, listener = viewModel::onSaveClicked)
            ).render()
        }
    }
}