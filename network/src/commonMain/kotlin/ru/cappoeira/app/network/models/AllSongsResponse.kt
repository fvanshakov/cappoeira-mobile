package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
class AllSongsResponse(
    val count: Int,
    val songs: List<SongInfo>
)