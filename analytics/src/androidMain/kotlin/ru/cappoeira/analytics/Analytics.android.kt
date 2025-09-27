package ru.cappoeira.analytics

import com.yandex.metrica.YandexMetrica

actual object Analytics {

    actual fun init(apiKey: String) = Unit

    actual fun logEvent(name: String, params: Map<String, Any>) {
        YandexMetrica.reportEvent(name, params)
    }
}