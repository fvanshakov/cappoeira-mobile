package ru.cappoeira.app.videoPlayer.controller

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerTimeControlStatusWaitingToPlayAtSpecifiedRate
import platform.AVFoundation.AVQueuePlayer
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.isPlaybackLikelyToKeepUp
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.rate
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVFoundation.seekToTime
import platform.AVFoundation.timeControlStatus
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMake
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSTimer
import platform.Foundation.NSURL

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class PlaybackStateController {

    val avPlayer: AVQueuePlayer = AVQueuePlayer()

    var timer: NSTimer? = null

    actual fun initPlayer(
        callback: (Long, Long) -> Unit,
        playbackState: (PlaybackState) -> Unit
    ) {
        timer?.invalidate()
        timer = NSTimer.scheduledTimerWithTimeInterval(1.0, true) {
            callback(currentPosition(), duration())
            playbackState(playerState())
        }
    }

    actual fun setItem(mediaItem: PlaybackMediaItem) {
        val nsUrl = NSURL.URLWithString(mediaItem.url)
        val playerItem = nsUrl?.let { AVPlayerItem(it) }
        if (playerItem != null) {
            avPlayer.replaceCurrentItemWithPlayerItem(playerItem)
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

    actual fun moveForward(sec: Double) {
        val currentTime = CMTimeGetSeconds(avPlayer.currentTime())
        val newTime = currentTime + sec
        avPlayer.seekToTime(CMTimeMakeWithSeconds(newTime, preferredTimescale = 600))
    }

    actual fun addItems(items: List<PlaybackMediaItem>) {
        items.forEach { item ->
            setItem(item)
        }
    }

    private fun playerState(): PlaybackState {
        return when {
            isPlaying() -> { PlaybackState.Playing }
            avPlayer.currentItem?.isPlaybackLikelyToKeepUp() == false-> { PlaybackState.Buffering }
            else -> { PlaybackState.Paused }
        }
    }
}