package ru.cappoeira.app.videoPlayer

import ru.cappoeira.app.network.NetworkResult
import ru.cappoeira.app.network.SongInfoApi
import ru.cappoeira.app.videoPlayer.controller.PlaybackMediaItem

sealed class MediaItemDataState {
    data class Success(val items: List<PlaybackMediaItem>) : MediaItemDataState()
    data object Failure : MediaItemDataState()
    data object Loading : MediaItemDataState()
}

interface PlaybackMediaItemRepository {
    suspend fun fetchMediaItems(id: String): MediaItemDataState
}

class PlaybackMediaItemRepositoryImpl(
    private val songInfoApi: SongInfoApi
) : PlaybackMediaItemRepository {

    override suspend fun fetchMediaItems(id: String): MediaItemDataState {
        return songInfoApi.getSongInfoById(id).let { res ->
            when (res) {
                is NetworkResult.Success -> {
                    val item = res.data.videoUrl?.let { url ->
                        PlaybackMediaItem(
                            id = res.data.id,
                            url = url,
                        )
                    }
                    MediaItemDataState.Success(listOfNotNull(item))
                }
                is NetworkResult.Error -> MediaItemDataState.Failure
            }
        }
    }
}