package ru.cappoeira.app.search.formatter

import ru.cappoeira.app.network.models.SongInfo
import ru.cappoeira.app.search.models.SongInfoViewObject

object SongInfoBySearchFormatter {

    fun SongInfo.format() : SongInfoViewObject {
        return with(this) {
            SongInfoViewObject(
                id = id,
                songName = songName
            )
        }
    }
}