package ru.cappoeira.app.videoPlayer.controller

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVQueuePlayer
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.isPlaybackBufferEmpty
import platform.AVFoundation.isPlaybackBufferFull
import platform.AVFoundation.isPlaybackLikelyToKeepUp
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.rate
import platform.AVFoundation.seekToTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSTimer
import platform.Foundation.NSURL

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class PlaybackStateController {

    val avPlayer: AVQueuePlayer = AVQueuePlayer()

    actual fun initPlayer(
        callback: (Long, Long) -> Unit,
        playbackState: (PlaybackState) -> Unit
    ) {
        NSTimer.scheduledTimerWithTimeInterval(1.0, true) {
            if (isPlaying()) {
                callback(currentPosition(), duration())
                playbackState(playerState())
            }
            if (avPlayer.currentItem?.isPlaybackBufferEmpty() == true) {
                playbackState(PlaybackState.Buffering)
            }
        }
    }

    actual fun addItem(mediaItem: PlaybackMediaItem) {
        val nsUrl = NSURL.URLWithString(mediaItem.url)
        val playerItem = nsUrl?.let { AVPlayerItem(it) }
        if (playerItem != null) {
            avPlayer.insertItem(playerItem, null)
        }
    }

    actual fun pause(playbackState: (PlaybackState) -> Unit) {
        (avPlayer as AVPlayer).pause()
        playbackState(PlaybackState.Paused)
    }

    actual fun play(playbackState: (PlaybackState) -> Unit) {
        resume()
        playbackState(PlaybackState.Playing)
    }

    actual fun resume() {
        avPlayer.play()
    }

    actual fun release() {
        avPlayer.removeAllItems()
    }

    actual fun isPlaying(): Boolean {
        return avPlayer.rate.toLong().toInt() != 0 && avPlayer.error == null
    }

    actual fun duration(): Long {
        val duration = avPlayer.currentItem?.let { CMTimeGetSeconds(it.duration) }
        return duration?.toLong() ?: 0L
    }

    actual fun currentPosition(): Long {
        val currentTime = avPlayer.currentItem?.let { CMTimeGetSeconds(it.currentTime()) }
        return currentTime?.toLong() ?: 0L
    }

    actual fun seekTo(position: Long) {
        val seekPosition = CMTimeMake(position, 1)
        avPlayer.seekToTime(seekPosition)
    }

    actual fun addItems(items: List<PlaybackMediaItem>) {
        items.forEach { item ->
            addItem(item)
        }
    }

    private fun playerState(): PlaybackState {
        val item = avPlayer.currentItem
        return when {
            item?.isPlaybackLikelyToKeepUp() == true || item?.isPlaybackBufferFull() == true -> {
                PlaybackState.Playing
            }
            item?.isPlaybackBufferEmpty() == true -> { PlaybackState.Buffering }
            avPlayer.error != null -> {
                PlaybackState.Error(avPlayer.error!!.code().toString() + "Something went wrong")
            }
            else -> { PlaybackState.Buffering }
        }
    }
}