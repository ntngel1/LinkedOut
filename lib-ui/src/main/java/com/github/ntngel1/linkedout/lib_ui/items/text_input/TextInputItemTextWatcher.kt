package com.github.ntngel1.linkedout.lib_ui.items.text_input

import android.text.Editable
import android.text.TextWatcher

internal class TextInputItemTextWatcher(
    private val onTextChanged: (text: String) -> Unit
) : TextWatcher {

    var ignoreFirstTextChange: Boolean = false

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!ignoreFirstTextChange) {
            onTextChanged.invoke(s?.toString().orEmpty())
        } else {
            ignoreFirstTextChange = false
        }
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}