package com.github.ntngel1.linkedout.logger

import timber.log.Timber

class TimberLogger : Logger {

    override fun e(message: String) {
        Timber.e(message)
    }

    override fun e(throwable: Throwable) {
        Timber.e(throwable)
    }

    override fun d(message: String) {
        Timber.d(message)
    }

    override fun d(obj: Any) {
        Timber.d(obj.toString())
    }
}