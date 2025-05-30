package ru.cappoeira.app.search.events

import ru.cappoeira.app.search.SongType

sealed class SearchEvent {
    data class ChangeSongType(val songType: SongType) : SearchEvent()
    data class ChangeSearchText(val text: String) : SearchEvent()
    data object Paginate : SearchEvent()
}