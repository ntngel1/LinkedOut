package com.github.ntngel1.linkedout.proxy_settings.presentation.proxies

import com.github.ntngel1.linkedout.lib_utils.base.BaseFragment
import com.github.ntngel1.linkedout.proxy_settings.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProxiesFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_proxies
}