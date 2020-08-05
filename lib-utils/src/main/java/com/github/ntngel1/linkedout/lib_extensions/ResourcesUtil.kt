package com.github.ntngel1.linkedout.lib_extensions

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.string(@StringRes id: Int): String = resources.getString(id)
fun Fragment.string(@StringRes id: Int): String = requireContext().string(id)