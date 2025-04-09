package ru.cappoeira.app.analytics

expect object Analytics {

    fun initialize()

    fun sendEvent(
        eventName: String,
        params: Map<Any?, *>
    )
}