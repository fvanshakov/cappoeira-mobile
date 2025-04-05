package ru.cappoeira.app.videoPlayer.controller

data class PlaybackMediaItem(
    val id: String,
    val position: Int,
    val name: String? = null,
    val url: String
)

sealed class PlaybackState {
    data object Playing: PlaybackState()
    data object Paused: PlaybackState()
    data object Buffering: PlaybackState()
    data class Error(val message: String): PlaybackState()
}

expect class PlaybackStateController {
    fun initPlayer(callback: (Long, Long) -> Unit, playbackState: (PlaybackState) -> Unit)
    fun addItem(mediaItem: PlaybackMediaItem)
    fun pause(playbackState: (PlaybackState) -> Unit)
    fun play(playbackState: (PlaybackState) -> Unit)
    fun resume()
    fun release()
    fun isPlaying(): Boolean
    fun duration(): Long
    fun currentPosition(): Long
    fun seekTo(position: Long)
    fun addItems(items: List<PlaybackMediaItem>)
}