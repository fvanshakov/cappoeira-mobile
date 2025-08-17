package ru.cappoeira.app.search.state

import ru.cappoeira.app.network.models.SongTag
import ru.cappoeira.app.search.SongType
import ru.cappoeira.app.search.models.SongInfoViewObject

data class SearchUiState(
    val isSkeletonShown: Boolean,
    val error: SearchUiErrorState?,
    val corridoState: SearchUiSongListState,
    val ladainhaState: SearchUiSongListState,
    val chosenType: SongType,
    val chosenTags: List<SongTag>
) {

    data class SearchUiSongListState(
        val songType: SongType,
        val songs: List<SongInfoViewObject>,
        val isLoading: Boolean
    )

    data class SearchUiErrorState(
        val errorMessage: String,
    )

    companion object {
        val DEFAULT = SearchUiState(
            isSkeletonShown = true,
            error = null,
            chosenType = SongType.CORRIDO,
            corridoState = SearchUiSongListState(
                songType = SongType.CORRIDO,
                songs = emptyList(),
                isLoading = false
            ),
            ladainhaState = SearchUiSongListState(
                songType = SongType.LADAINHA,
                songs = emptyList(),
                isLoading = false
            ),
            chosenTags = emptyList()
        )
    }
}