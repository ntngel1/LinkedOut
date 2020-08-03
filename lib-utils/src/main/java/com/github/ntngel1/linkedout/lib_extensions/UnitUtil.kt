package com.github.ntngel1.linkedout.lib_extensions

import android.content.res.Resources

/**
 * Converts pixels integer value to dp
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()