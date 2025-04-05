package ru.cappoeira.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform