package com.github.ntngel1.linkedout.lib_ui.items

import android.view.View
import android.widget.ArrayAdapter
import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_ui.R
import kotlinx.android.synthetic.main.item_single_select_dropdown_setting.*

data class SingleSelectDropdownSettingItem(
    override val id: String,
    val title: String,
    val entries: List<String>,
    val onEntrySelected: Callback1<Int, Unit>
) : Item<SingleSelectDropdownSettingItem>() {

    override val layoutId: Int
        get() = R.layout.item_single_select_dropdown_setting

    override fun bind(view: View) {
        super.bind(view)
        text_input_layout_single_select_dropdown_setting.hint = title

        with(autocomplete_textview_single_select_dropdown_setting) {
            val adapter = ArrayAdapter(view.context, R.layout.item_dropdown_menu_entry, entries)
            setAdapter(adapter)
            setOnItemClickListener { _, _, position, _ ->
                onEntrySelected.listener.invoke(position)
            }
        }
    }
}