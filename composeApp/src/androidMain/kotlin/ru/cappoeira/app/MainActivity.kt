package ru.cappoeira.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import ru.cappoeira.analytics.AnalyticsApi
import ru.cappoeira.lifecycle.AppLifecycle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                AppLifecycle.notifyBackground()
            }

            override fun onStart(owner: LifecycleOwner) {
                AppLifecycle.notifyForeground()
            }
        })


        val config = YandexMetricaConfig.newConfigBuilder(AnalyticsApi.getApiKey()).build()
        YandexMetrica.activate(application, config)
        YandexMetrica.enableActivityAutoTracking(application)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}