package ru.cappoeira.app.videoPlayer.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformMediaPlayerView(
    modifier: Modifier,
    playbackViewModel: PlaybackViewModel,
    hidePlayback: Boolean
) {
    val avPlayerViewController = remember { AVPlayerViewController() }
    avPlayerViewController.player = remember { playbackViewModel.getPlatformController().avPlayer }
    avPlayerViewController.showsPlaybackControls = false
    avPlayerViewController.view.setFrame(CGRectMake(0.0, 0.0, 0.10, 0.10))

    val interactionSource = remember { MutableInteractionSource() }

    UIKitView(
        factory = {
            val playerContainer = UIView()
            playerContainer.addSubview(avPlayerViewController.view)
            playerContainer
        },
        update = {
            avPlayerViewController.player?.play()
        },
        onRelease = { avPlayerViewController.player?.pause() },
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                playbackViewModel.playPause()
            }
    )
}