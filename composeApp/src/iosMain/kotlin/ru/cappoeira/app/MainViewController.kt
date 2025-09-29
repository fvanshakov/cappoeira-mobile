package ru.cappoeira.app

import androidx.compose.ui.window.ComposeUIViewController
import ru.cappoeira.analytics.Analytics
import ru.cappoeira.analytics.AnalyticsApi

fun MainViewController() = ComposeUIViewController {
    Analytics.init(AnalyticsApi.getApiKey())
    App()
}