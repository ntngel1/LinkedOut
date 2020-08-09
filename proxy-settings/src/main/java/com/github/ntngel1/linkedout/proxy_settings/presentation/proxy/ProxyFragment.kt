package com.github.ntngel1.linkedout.proxy_settings.presentation.proxy

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback2
import com.github.ntngel1.linkedout.lib_delegate_adapter.item_decorations.SpacingItemDecoration
import com.github.ntngel1.linkedout.lib_delegate_adapter.renderState
import com.github.ntngel1.linkedout.lib_utils.BaseFragment
import com.github.ntngel1.linkedout.lib_utils.dp
import com.github.ntngel1.linkedout.lib_utils.attributeResourceId
import com.github.ntngel1.linkedout.lib_utils.string
import com.github.ntngel1.linkedout.lib_ui.items.*
import com.github.ntngel1.linkedout.lib_ui.items.text_input.TextInputItem
import com.github.ntngel1.linkedout.proxy_settings.R
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyPingEntity
import com.github.ntngel1.linkedout.proxy_settings.presentation.proxy.enums.ProxyType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_proxy.*
import java.lang.IllegalStateException

@AndroidEntryPoint
class ProxyFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_proxy

    private val viewModel: ProxyViewModel by viewModels()
    private val spacingItemDecoration = SpacingItemDecoration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val proxyId = requireArguments().getInt(PROXY_ID_KEY)
            viewModel.setup(
                proxyId = if (proxyId == 0) null else proxyId
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.state.observe(::renderState)
    }

    private fun setupRecyclerView() {
        recyclerview_proxy_settings.setHasFixedSize(true)
        recyclerview_proxy_settings.addItemDecoration(spacingItemDecoration)
    }

    private fun renderState(
        state: ProxyState
    ) = recyclerview_proxy_settings.renderState(spacingItemDecoration = spacingItemDecoration) {
        state.newProxyType?.let { proxyType ->
            SingleSelectDropdownItem(
                id = "proxy_type_dropdown",
                hint = string(R.string.proxy_settings_proxy_type),
                entries = listOf(
                    string(R.string.proxy_settings_proxy_type_http),
                    string(R.string.proxy_settings_proxy_type_socks),
                    string(R.string.proxy_settings_proxy_type_mt_proto)
                ),
                selectedEntryIndex = proxyType.ordinal,
                onEntrySelected = callback2(static = true) { _, proxyTypeIndex ->
                    val proxyType = when (proxyTypeIndex) {
                        0 -> ProxyType.HTTP
                        1 -> ProxyType.SOCKS5
                        2 -> ProxyType.MT_PROTO
                        else -> throw IllegalStateException("No such ProxyType value for index $proxyTypeIndex")
                    }

                    viewModel.onProxyTypeChanged(proxyType)
                }
            ).render()

            spacing(8.dp)
        }

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

        if (state.isPingingProxy) {
            LoadingItem(
                id = "pinging_proxy_loading"
            ).render()

            spacing(8.dp)
        }

        if (state.isProxyPingVisible) {
            TextItem(
                id = "proxy_ping_text",
                text = makeProxyPingText(state),
                textAppearanceResId = attributeResourceId(R.attr.textAppearanceBody1)
            ).render()

            spacing(8.dp)
        }

        SwitchItem(
            id = "enable_switch",
            isChecked = true,
            text = string(R.string.proxy_settings_enable),
            onCheckedChange = callback1(static = true) {

            }
        ).render()

        spacing(8.dp)

        if (state.isPingProxyButtonVisible) {
            ButtonItem(
                id = "ping_proxy_button",
                text = string(R.string.proxy_settings_ping_proxy),
                style = ButtonItem.Style.OUTLINED,
                onClicked = callback(static = true) {
                    viewModel.onPingProxyClicked()
                }
            ).render()

            spacing(8.dp)
        }

        if (state.isSaveButtonVisible) {
            val text = if (state.proxyId == null) {
                string(R.string.proxy_settings_add)
            } else {
                string(R.string.proxy_settings_save)
            }

            ButtonItem(
                id = "save_button",
                text = text,
                onClicked = callback(static = true, listener = viewModel::onSaveClicked)
            ).render()
        }
    }

    private fun makeProxyPingText(state: ProxyState): CharSequence = buildSpannedString {
        if (state.proxyPingLatencyMs != null && state.proxyPingLatencyMs > 0) {
            string(R.string.proxy_settings_proxy_ping_latency, state.proxyPingLatencyMs)
                .let(::append)
        }

        if (state.proxyPingStability != null) {
            val stabilityStringId = when (state.proxyPingStability) {
                ProxyPingEntity.Stability.GOOD -> R.string.proxy_settings_proxy_ping_stability_good
                ProxyPingEntity.Stability.NORMAL -> R.string.proxy_settings_proxy_ping_stability_normal
                ProxyPingEntity.Stability.BAD -> R.string.proxy_settings_proxy_ping_stability_bad
            }

            string(R.string.proxy_settings_proxy_ping_stability, string(stabilityStringId))
                .let(::append)
        }
    }

    companion object {
        private const val PROXY_ID_KEY = "proxy_id"

        fun newInstance(proxyId: Int? = null) = ProxyFragment().apply {
            arguments = bundleOf(PROXY_ID_KEY to proxyId)
        }
    }
}