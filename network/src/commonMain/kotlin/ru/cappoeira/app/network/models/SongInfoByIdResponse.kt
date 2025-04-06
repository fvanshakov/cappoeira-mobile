package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
class SongInfoByIdResponse(
    val id: String,
    val songName: String,
    val videoUrl: String?
)