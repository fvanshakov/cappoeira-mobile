package ru.cappoeira.app.videoPlayer.view

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController

@Composable
actual fun PlatformMediaPlayerView(
    modifier: Modifier,
    playbackStateController: PlaybackStateController,
    isCustom: Boolean
) {
    val controller by remember {
        mutableStateOf(playbackStateController.getController())
    }
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = controller
                useController = !isCustom
            }
        },
        modifier = Modifier.wrapContentSize()
    )
}