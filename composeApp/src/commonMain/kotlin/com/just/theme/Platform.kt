package com.just.theme

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform