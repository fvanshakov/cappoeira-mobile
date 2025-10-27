package ru.cappoeira.app.videoPlayer.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

@Composable
actual fun PlatformMediaPlayerView(
    modifier: Modifier,
    playbackViewModel: PlaybackViewModel,
    hidePlayback: Boolean
) {
    val controller by remember {
        mutableStateOf(playbackViewModel.getPlatformController().getController())
    }

    val interactionSource = remember { MutableInteractionSource() }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = controller
                useController = false
            }
        },
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                playbackViewModel.playPause()
            }
    )
}