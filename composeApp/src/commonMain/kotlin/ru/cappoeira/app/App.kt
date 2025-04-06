package ru.cappoeira.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import ru.cappoeira.app.videoPlayer.PlaybackView
import ru.cappoeira.app.videoPlayer.di.videoPlayerModule

@Composable
@Preview
fun App() {
    KoinApplication(
        application = { modules(videoPlayerModule) }
    ) {
        MaterialTheme {
            PlaybackView(
                isCustom = false,
                id = "TWV0YSBNZWxvbmhh"
            )
        }
    }
}