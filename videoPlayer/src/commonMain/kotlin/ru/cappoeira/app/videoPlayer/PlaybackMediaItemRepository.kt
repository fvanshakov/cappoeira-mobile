package ru.cappoeira.app.videoPlayer

import ru.cappoeira.app.videoPlayer.controller.PlaybackMediaItem

sealed class MediaItemDataState {
    data class Success(val items: List<PlaybackMediaItem>) : MediaItemDataState()
    data object Failure : MediaItemDataState()
    data object Loading : MediaItemDataState()
}

interface PlaybackMediaItemRepository {
    suspend fun fetchMediaItems(): MediaItemDataState
}

class PlaybackMediaItemRepositoryImpl : PlaybackMediaItemRepository {
    private val remoteDataSourceData = mutableListOf(
        PlaybackMediaItem(
            id = "1",
            position = 1,
            url = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8"
        ),
    )

    override suspend fun fetchMediaItems(): MediaItemDataState {
        return MediaItemDataState.Success(remoteDataSourceData)
    }
}