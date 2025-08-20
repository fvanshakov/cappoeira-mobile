package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SongInfoByIdResponse(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songLines: List<SongLine>,
    val songTags: SongTags
)

@Serializable
data class SongTags(
    val tagsWithValues: Map<String, List<String>>
)