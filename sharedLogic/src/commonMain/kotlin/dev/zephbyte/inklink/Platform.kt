package dev.zephbyte.inklink

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform