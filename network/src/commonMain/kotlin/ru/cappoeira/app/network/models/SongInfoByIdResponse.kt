package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SongInfoByIdResponse(
    val id: String,
    val songName: String,
    val videoUrl: String?
)