package com.github.ntngel1.linkedout.lib_extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Context.string(@StringRes id: Int, vararg args: Any): String =
    resources.getString(id, *args)

fun Fragment.string(@StringRes id: Int, vararg args: Any): String =
    requireContext().string(id, *args)

@AnyRes
fun FragmentActivity.attributeResourceId(@AttrRes id: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    return typedValue.resourceId
}

@AnyRes
fun Fragment.attributeResourceId(@AttrRes id: Int): Int =
    requireActivity().attributeResourceId(id)