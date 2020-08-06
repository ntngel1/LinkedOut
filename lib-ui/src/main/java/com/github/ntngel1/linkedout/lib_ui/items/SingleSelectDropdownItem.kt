package com.github.ntngel1.linkedout.lib_ui.items

import android.widget.ArrayAdapter
import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback2
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_ui.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_single_select_dropdown.*

data class SingleSelectDropdownItem(
    override val id: String,
    val hint: String,
    val entries: List<String>,
    val selectedEntryIndex: Int,
    val onEntrySelected: Callback2<String, Int>
) : Item<SingleSelectDropdownItem>() {

    override val layoutId: Int
        get() = R.layout.item_single_select_dropdown

    override fun bind(layout: LayoutContainer) {
        bindHint(layout)
        bindEntries(layout)
        bindSelectedEntry(layout)
        bindListeners(layout)
    }

    override fun bind(previousItem: SingleSelectDropdownItem, layout: LayoutContainer) {
        if (hint != previousItem.hint) {
            bindHint(layout)
        }

        if (entries != previousItem.entries) {
            bindEntries(layout)
        }

        if (selectedEntryIndex != previousItem.selectedEntryIndex) {
            bindSelectedEntry(layout)
        }
    }

    override fun recycle(layout: LayoutContainer) {
        super.recycle(layout)
        layout.autocomplete_textview_single_select_dropdown.onItemClickListener = null
    }

    private fun bindHint(layout: LayoutContainer) {
        layout.text_input_layout_single_select_dropdown.hint = hint
    }

    private fun bindEntries(layout: LayoutContainer) {
        val adapter = ArrayAdapter(layout.context, R.layout.item_dropdown_menu_entry, entries)
        layout.autocomplete_textview_single_select_dropdown.setAdapter(adapter)
    }

    private fun bindSelectedEntry(layout: LayoutContainer) {
        layout.autocomplete_textview_single_select_dropdown.setText(
            entries[selectedEntryIndex],
            false
        )
    }

    private fun bindListeners(layout: LayoutContainer) {
        layout.autocomplete_textview_single_select_dropdown.setOnItemClickListener { _, _, position, _ ->
            onEntrySelected.invoke(entries[position], position)
        }
    }
}