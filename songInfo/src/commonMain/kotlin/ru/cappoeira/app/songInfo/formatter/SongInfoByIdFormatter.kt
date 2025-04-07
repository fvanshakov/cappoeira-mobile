package ru.cappoeira.app.songInfo.formatter

import ru.cappoeira.app.network.models.SongInfoByIdResponse
import ru.cappoeira.app.songInfo.modles.SongInfoByIdViewObject

object SongInfoByIdFormatter {

    fun SongInfoByIdResponse.format(): SongInfoByIdViewObject {
        return with(this) {
            SongInfoByIdViewObject(
                songName = songName,
                videoUrl = videoUrl
            )
        }
    }
}