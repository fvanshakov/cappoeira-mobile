package ru.cappoeira.app.videoPlayer.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformMediaPlayerView(
    modifier: Modifier,
    playbackStateController: PlaybackStateController,
    hidePlayback: Boolean
) {
    val avPlayerViewController = remember { AVPlayerViewController() }
    avPlayerViewController.player = remember { playbackStateController.avPlayer }
    avPlayerViewController.showsPlaybackControls = hidePlayback
    avPlayerViewController.view.setFrame(CGRectMake(0.0, 0.0, 0.10, 0.10))

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
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f)
    )
}