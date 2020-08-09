package com.github.ntngel1.linkedout.lib_utils

import android.widget.EditText

fun EditText.text(): String = text?.toString().orEmpty()