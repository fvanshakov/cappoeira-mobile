package ru.cappoeira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import ru.cappoeira.analytics.Analytics
import ru.cappoeira.analytics.AnalyticsApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

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