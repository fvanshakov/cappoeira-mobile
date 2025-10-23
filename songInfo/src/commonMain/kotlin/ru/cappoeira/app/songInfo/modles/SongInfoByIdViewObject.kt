package ru.cappoeira.app.songInfo.modles

import ru.cappoeira.app.network.models.Transition

class SongInfoByIdViewObject(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songLines: List<SongInfoTextLineViewObject>,
    val songTags: SongTagsViewObject,
    val themeTags: SongTagsViewObject,
    val optimalTransitions: List<Transition>,
    val warning: String?
)

data class SongInfoTextLineViewObject(
    val id: String,
    val index: Int,
    val text: String,
    val translation: List<SongTextChunkViewObject>,
    val transcription: List<SongTextChunkViewObject>,
    val isChoirPart: Boolean
)

data class SongTagsViewObject(
    val tagsWithValues: Map<String, List<String>>
)

data class SongTextChunkViewObject(
    val text: String,
    val definition: String?
)
