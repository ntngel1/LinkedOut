package com.github.ntngel1.linkedout.lib_ui.items

import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_ui.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.extensions.LayoutContainer

data class SwitchItem(
    override val id: String,
    val isChecked: Boolean,
    val text: String,
    val onCheckedChange: Callback1<Boolean>
) : Item<SwitchItem>() {

    override val layoutId: Int
        get() = R.layout.item_switch

    override fun bind(layout: LayoutContainer) {
        val switch = layout.containerView as SwitchMaterial

        bindIsChecked(switch)
        bindText(switch)
        bindOnCheckedChange(switch)
    }

    override fun bind(previousItem: SwitchItem, layout: LayoutContainer) {
        val switch = layout.containerView as SwitchMaterial

        if (isChecked != previousItem.isChecked) {
            bindIsChecked(switch)
        }

        if (text != previousItem.text) {
            bindText(switch)
        }

        if (onCheckedChange != previousItem.onCheckedChange) {
            bindOnCheckedChange(switch)
        }
    }

    override fun recycle(layout: LayoutContainer) {
        val switch = layout.containerView as SwitchMaterial

        switch.setOnCheckedChangeListener(null)
    }

    private fun bindText(switch: SwitchMaterial) {
        switch.text = text
    }

    private fun bindIsChecked(switch: SwitchMaterial) {
        switch.isChecked = isChecked
    }

    private fun bindOnCheckedChange(switch: SwitchMaterial) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange.invoke(isChecked)
        }
    }
}