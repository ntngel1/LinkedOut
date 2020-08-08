package com.github.ntngel1.linkedout.lib_extensions

import android.annotation.SuppressLint
import android.os.Build

object DeviceSpecsUtil {

    @SuppressLint("DefaultLocale")
    fun getDeviceModel(): String {
        val manufacturer = Build.MANUFACTURER.toLowerCase()
        val model = Build.MODEL.toLowerCase()

        return if (model.startsWith(manufacturer)) {
            model.capitalize()
        } else {
            "${manufacturer.capitalize()} $model"
        }
    }

    fun getSystemVersion(): String {
        return "Android ${Build.VERSION.SDK_INT}"
    }
}
