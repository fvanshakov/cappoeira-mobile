package ru.cappoeira.app.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SongTagsResponse(
    val tags: Map<String, SongTag>
)

@Serializable
data class SongTag(
    val values: List<String>,
    val isPlural: Boolean
)