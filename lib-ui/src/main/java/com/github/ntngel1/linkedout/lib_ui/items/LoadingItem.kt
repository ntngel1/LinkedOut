package com.github.ntngel1.linkedout.lib_ui.items

import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_ui.R

data class LoadingItem(
    override val id: String
) : Item<LoadingItem>() {

    override val layoutId: Int
        get() = R.layout.item_loading
}