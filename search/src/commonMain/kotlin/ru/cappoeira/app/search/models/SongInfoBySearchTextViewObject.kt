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
    val translation: List<SongTextChunkViewObject>,
    val transcription: String,
    val isChoirPart: Boolean
)

data class SongTextChunkViewObject(
    val text: String,
    val definition: String?
)