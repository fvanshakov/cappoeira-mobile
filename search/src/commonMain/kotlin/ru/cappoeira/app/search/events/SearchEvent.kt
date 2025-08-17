package ru.cappoeira.app.search.events

import ru.cappoeira.app.search.SongType

sealed class SearchEvent {
    data class ChangeSongType(val songType: SongType) : SearchEvent()
    data class ChangeSearchText(val text: String) : SearchEvent()
    data object Paginate : SearchEvent()
    data class SelectTag(
        val key: String,
        val value: String,
        val isPlural: Boolean
    ) : SearchEvent()
    data object ClearTags : SearchEvent()
}