package ru.cappoeira.app.search.models

data class SongTagViewObject(
    val values: List<SongTagValueViewObject>,
    val isPlural: Boolean
)

data class SongTagValueViewObject(
    val value: String,
    val isChosen: Boolean
)