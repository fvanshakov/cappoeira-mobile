package ru.cappoeira.app.search.formatter

import ru.cappoeira.app.network.models.SongInfo
import ru.cappoeira.app.search.models.SongInfoTextLineViewObject
import ru.cappoeira.app.search.models.SongInfoViewObject

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
                            transcription = it.transcription,
                            translation = it.translation,
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