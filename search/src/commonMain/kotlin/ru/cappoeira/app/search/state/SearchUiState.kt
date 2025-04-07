package ru.cappoeira.app.search.state

import ru.cappoeira.app.search.models.SongInfoViewObject

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val songs: List<SongInfoViewObject>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}