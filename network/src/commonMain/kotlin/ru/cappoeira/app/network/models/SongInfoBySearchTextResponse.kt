package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SongInfoBySearchTextResponse(
    val count: Int,
    val songs: List<SongInfo>
)

@Serializable
data class SongInfo(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songType: String,
    val songLines: List<SongLine>
)

@Serializable
data class SongLine(
    val id: String,
    val index: Int,
    val text: String,
    val translation: List<SongTextChunk>,
    val transcription: List<SongTextChunk>,
    val isChoirPart: Boolean
)

@Serializable
data class SongTextChunk(
    val text: String,
    val definition: String?
)