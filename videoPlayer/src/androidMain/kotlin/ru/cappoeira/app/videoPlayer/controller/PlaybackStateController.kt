package ru.cappoeira.app.videoPlayer.controller

import android.os.Handler
import android.os.Looper
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import ru.cappoeira.app.videoPlayer.Media3PlayerComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlaybackStateController(private val media3PlayerComponent: Media3PlayerComponent) {


    actual fun addItem(mediaItem: PlaybackMediaItem) {
        media3PlayerComponent.addMediaItem(mediaItem)
    }

    actual fun initPlayer(callback: (Long, Long) -> Unit, playbackState: (PlaybackState) -> Unit) {
        media3PlayerComponent.initPlayer()
        media3PlayerComponent.setControllerListener(PlaybackControllerListener(playbackState))
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (media3PlayerComponent.getMediaController() != null &&
                    media3PlayerComponent.getMediaController()?.isPlaying == true) {
                    callback(currentPosition(), duration())
                    playbackState(PlaybackState.Playing)
                }
                val state = media3PlayerComponent.getMediaController()?.playbackState
                if (state == Player.STATE_BUFFERING) {
                    playbackState(PlaybackState.Buffering)
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    actual fun pause(playbackState: (PlaybackState) -> Unit) {
        media3PlayerComponent.getMediaController()?.pause()
        playbackState(PlaybackState.Paused)
    }

    actual fun release() {
        media3PlayerComponent.releasePlayer()
    }

    actual fun resume() {
        media3PlayerComponent.play()
    }

    actual fun isPlaying(): Boolean {
        return media3PlayerComponent.getMediaController()?.isPlaying == true
    }

    actual fun duration(): Long {
        return media3PlayerComponent.getMediaController()?.duration ?: 0L
    }

    actual fun currentPosition(): Long {
        return media3PlayerComponent.getMediaController()?.currentPosition ?: 0L
    }

    actual fun seekTo(position: Long) {
        media3PlayerComponent.getMediaController()?.seekTo(position)
    }

    actual fun play(playbackState: (PlaybackState) -> Unit) {
        media3PlayerComponent.getMediaController()?.play()
        playbackState(PlaybackState.Playing)
    }

    internal fun getController(): MediaController? {
        return media3PlayerComponent.getMediaController()
    }

    actual fun addItems(items: List<PlaybackMediaItem>) {
        media3PlayerComponent.addAll(items)
    }

    class PlaybackControllerListener(val playbackState: (PlaybackState) -> Unit) : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            error.message
                ?.let { PlaybackState.Error(message = it) }
                ?.let { playbackState(it) }
        }
    }
}