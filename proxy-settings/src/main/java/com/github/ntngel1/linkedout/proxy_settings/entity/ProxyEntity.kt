package com.github.ntngel1.linkedout.proxy_settings.entity

sealed class ProxyEntity {

    abstract val id: Int
    abstract val hostname: String
    abstract val port: Int

    data class Http(
        override val id: Int,
        override val hostname: String,
        override val port: Int,
        val username: String,
        val password: String
    ) : ProxyEntity()

    data class Socks5(
        override val id: Int,
        override val hostname: String,
        override val port: Int,
        val username: String,
        val password: String
    ) : ProxyEntity()

    data class MtProto(
        override val id: Int,
        override val hostname: String,
        override val port: Int,
        val secret: String
    ) : ProxyEntity()
}