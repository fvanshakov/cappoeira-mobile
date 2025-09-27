package ru.cappoeira.analytics

import cocoapods.YandexMobileMetrica.YMMYandexMetrica
import cocoapods.YandexMobileMetrica.YMMYandexMetricaConfiguration
import kotlinx.cinterop.ExperimentalForeignApi

actual object Analytics {

    @OptIn(ExperimentalForeignApi::class)
    actual fun init(apiKey: String) {
        val config = YMMYandexMetricaConfiguration(apiKey)
        YMMYandexMetrica.activateWithConfiguration(config)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun logEvent(name: String, params: Map<String, Any>) {
        val castParams = params as Map<Any?, *>
        YMMYandexMetrica.reportEvent(name, parameters = castParams, null)
    }
}