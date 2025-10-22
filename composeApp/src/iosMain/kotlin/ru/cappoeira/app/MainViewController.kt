package ru.cappoeira.app

import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIApplicationDidBecomeActiveNotification
import platform.UIKit.UIApplicationWillResignActiveNotification
import ru.cappoeira.analytics.Analytics
import ru.cappoeira.analytics.AnalyticsApi
import ru.cappoeira.lifecycle.AppLifecycle

fun MainViewController() = ComposeUIViewController {
    val center = NSNotificationCenter.defaultCenter
    center.addObserverForName(
        name = UIApplicationWillResignActiveNotification,
        `object` = null,
        queue = NSOperationQueue.mainQueue
    ) { _ ->
        AppLifecycle.notifyBackground()
    }

    center.addObserverForName(
        name = UIApplicationDidBecomeActiveNotification,
        `object` = null,
        queue = NSOperationQueue.mainQueue
    ) { _ ->
        AppLifecycle.notifyForeground()
    }
    Analytics.init(AnalyticsApi.getApiKey())
    App()
}