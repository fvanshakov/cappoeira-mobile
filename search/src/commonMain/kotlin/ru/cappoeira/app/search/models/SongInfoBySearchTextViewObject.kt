package ru.cappoeira.app.search.models

data class SongInfoViewObject(
    val id: String,
    val songName: String,
    val songLines: List<SongInfoTextLineViewObject>
)

data class SongInfoTextLineViewObject(
    val id: String,
    val index: Int,
    val text: String,
    val translation: String,
    val transcription: String,
    val isChoirPart: Boolean
)