package ru.cappoeira.app.songInfo.formatter

import ru.cappoeira.app.network.models.SongInfoByIdResponse
import ru.cappoeira.app.songInfo.modles.SongInfoByIdViewObject
import ru.cappoeira.app.songInfo.modles.SongInfoTextLineViewObject

object SongInfoByIdFormatter {

    fun SongInfoByIdResponse.format(): SongInfoByIdViewObject {
        return with(this) {
            SongInfoByIdViewObject(
                songName = songName,
                videoUrl = videoUrl,
                id = id,
                songLines = songLines.map {
                    SongInfoTextLineViewObject(
                        id = it.id,
                        text = it.text,
                        transcription = it.transcription,
                        translation = it.translation,
                        index = it.index,
                        isChoirPart = it.isChoirPart
                    )
                }
            )
        }
    }
}