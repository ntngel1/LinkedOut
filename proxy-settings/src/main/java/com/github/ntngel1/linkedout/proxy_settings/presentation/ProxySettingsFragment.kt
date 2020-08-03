package com.github.ntngel1.linkedout.proxy_settings.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.lib_delegate_adapter.callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.render
import com.github.ntngel1.linkedout.lib_extensions.BaseFragment
import com.github.ntngel1.linkedout.lib_ui.items.SingleSelectDropdownSettingItem
import com.github.ntngel1.linkedout.proxy_settings.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_proxy_settings.*

@AndroidEntryPoint
class ProxySettingsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_proxy_settings

    private val viewModel: ProxySettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_proxy_settings.setHasFixedSize(true)
        recyclerview_proxy_settings.render {
            SingleSelectDropdownSettingItem(
                id = "test",
                title = "NOTHING",
                entries = listOf("TEST", "TEST"),
                onEntrySelected = callback1 {

                }
            ).render()
        }
    }
}