package com.github.ntngel1.linkedout.lib_telegram

class TelegramException(code: Int, message: String) : Exception("code = $code, message = $message")