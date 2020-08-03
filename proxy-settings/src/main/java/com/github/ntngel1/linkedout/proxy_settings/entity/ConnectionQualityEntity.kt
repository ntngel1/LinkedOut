package com.github.ntngel1.linkedout.proxy_settings.entity

data class ConnectionQualityEntity(
    val latencyMs: Long? = null, // null if stability == BAD
    val stability: Stability
) {

    enum class Stability {
        BAD,
        NORMAL,
        GOOD
    }
}