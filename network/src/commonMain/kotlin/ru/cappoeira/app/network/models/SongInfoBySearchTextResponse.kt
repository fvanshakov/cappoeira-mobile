package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
class SongInfoBySearchTextResponse(
    val count: Int,
    val songs: List<SongInfo>
)

@Serializable
data class SongInfo(
    val id: String,
    val songName: String,
    val videoUrl: String?
)