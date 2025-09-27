package ru.cappoeira.analytics

expect object Analytics {

    fun init(apiKey: String)

    fun logEvent(name: String, params: Map<String, Any>)
}