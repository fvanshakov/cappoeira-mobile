package ru.cappoeira

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform