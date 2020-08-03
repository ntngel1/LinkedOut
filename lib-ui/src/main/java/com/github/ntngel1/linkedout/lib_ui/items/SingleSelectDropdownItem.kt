package com.github.ntngel1.linkedout.lib_ui.items

import android.widget.ArrayAdapter
import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.ViewStateStore
import com.github.ntngel1.linkedout.lib_ui.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_single_select_dropdown.*

data class SingleSelectDropdownItem(
    override val id: String,
    val title: String,
    val entries: List<String>,
    val selectedEntryIndex: Int,
    val onEntrySelected: Callback1<String>
) : Item<SingleSelectDropdownItem>() {

    override val layoutId: Int
        get() = R.layout.item_single_select_dropdown

    override fun bind(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        layout.text_input_layout_single_select_dropdown.hint = title
        with(layout.autocomplete_textview_single_select_dropdown) {
            val adapter = ArrayAdapter(layout.context, R.layout.item_dropdown_menu_entry, entries)
            setAdapter(adapter)
            setText(entries[selectedEntryIndex], false)
            setOnItemClickListener { _, _, position, _ ->
                onEntrySelected.invoke(entries[position])
            }
        }

        super.bind(layout, viewStateStore)
    }

    override fun recycle(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        super.recycle(layout, viewStateStore)
        layout.autocomplete_textview_single_select_dropdown.onItemClickListener = null
    }
}