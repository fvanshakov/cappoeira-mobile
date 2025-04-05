package ru.cappoeira.app.videoPlayer.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController

@Composable
expect fun PlatformMediaPlayerView(
    modifier: Modifier,
    playbackStateController: PlaybackStateController,
    isCustom: Boolean = false
)