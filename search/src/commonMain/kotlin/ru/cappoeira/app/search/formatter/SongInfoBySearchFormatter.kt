package ru.cappoeira.app.search.formatter

import ru.cappoeira.app.network.models.SongInfo
import ru.cappoeira.app.search.models.SongInfoTextLineViewObject
import ru.cappoeira.app.search.models.SongInfoViewObject
import ru.cappoeira.app.search.models.SongTextChunkViewObject

object SongInfoBySearchFormatter {

    fun SongInfo.format(isWithSongLines: Boolean) : SongInfoViewObject {
        return with(this) {
            SongInfoViewObject(
                id = id,
                songName = songName,
                songLines = if (isWithSongLines) {
                    songLines.map {
                        SongInfoTextLineViewObject(
                            id = it.id,
                            text = it.text,
                            transcription = it.transcription.map { translationChunk ->
                                SongTextChunkViewObject(
                                    text = translationChunk.text,
                                    definition = translationChunk.definition
                                )
                            },
                            translation = it.translation.map { translationChunk ->
                                SongTextChunkViewObject(
                                    text = translationChunk.text,
                                    definition = translationChunk.definition
                                )
                            },
                            index = it.index,
                            isChoirPart = it.isChoirPart
                        )
                    }
                } else {
                    emptyList()
                }
            )
        }
    }
}