package com.github.ntngel1.linkedout.logger

interface Logger {
    fun e(message: String)
    fun e(throwable: Throwable)

    fun d(message: String)
    fun d(obj: Any)
}