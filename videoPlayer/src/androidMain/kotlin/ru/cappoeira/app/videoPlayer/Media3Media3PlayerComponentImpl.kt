package ru.cappoeira.app.videoPlayer

import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import ru.cappoeira.app.videoPlayer.controller.PlaybackMediaItem
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController

interface Media3PlayerComponent {

    fun initPlayer()

    fun setMediaItem(mediaItem: PlaybackMediaItem)

    fun addMediaItem(mediaItem: PlaybackMediaItem)

    fun play()

    fun getMediaController(): MediaController?

    fun setControllerListener(
        playbackControllerListener: PlaybackStateController.PlaybackControllerListener
    )

    fun releasePlayer()

    fun addAll(mediaItems: List<PlaybackMediaItem>)
}

@OptIn(UnstableApi::class)
class Media3PlayerComponentImpl(
    private val context: Application,
    private val cachedPlaybackDataSourceFactory: CachedPlaybackDataSourceFactory
) : Media3PlayerComponent {
    private lateinit var player: ExoPlayer
    private var mediaController: MediaController? = null
    private var mediaSession: MediaSession? = null

    override fun initPlayer() {
        player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(cachedPlaybackDataSourceFactory.buildCacheDataSourceFactory())
            .build()
        mediaSession = MediaSession.Builder(context, player)
            .build()
        mediaController = mediaSession?.token?.let { token ->
            MediaController.Builder(context, token)
                .buildAsync()
                .get()
        }
    }


    override fun setMediaItem(mediaItem: PlaybackMediaItem) {
        val mediaItemWithMetadata = MediaItem.Builder()
            .setUri(mediaItem.url)
            .setMediaId(mediaItem.id)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(mediaItem.name)
                    .build()
            )
            .build()
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItemWithMetadata)
        player.setMediaSource(hlsMediaSource)
    }

    override fun addMediaItem(mediaItem: PlaybackMediaItem) {
        val mediaItemWithMetadata = MediaItem.Builder()
            .setUri(mediaItem.url)
            .setMediaId(mediaItem.url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(mediaItem.name)
                    .setArtist("Artist Name")
                    .build()
            )
            .build()
        player.addMediaItem(mediaItemWithMetadata)

    }

    override fun addAll(mediaItems: List<PlaybackMediaItem>) {
        mediaItems.forEach { mediaItem ->
            addMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
        }
    }

    override fun releasePlayer() {
        player.release()
        mediaSession?.release()
        mediaSession = null
    }

    override fun getMediaController(): MediaController? {
        return mediaController
    }

    override fun setControllerListener(
        playbackControllerListener: PlaybackStateController.PlaybackControllerListener
    ) {
        mediaController?.addListener(playbackControllerListener)
    }

    override fun play() {
        mediaController?.play()
    }
}