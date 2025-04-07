package ru.cappoeira.app.songInfo.state

import ru.cappoeira.app.songInfo.modles.SongInfoByIdViewObject

sealed class SongInfoUIState {
    object Loading : SongInfoUIState()
    data class Success(val vo: SongInfoByIdViewObject) : SongInfoUIState()
    data class Error(val message: String) : SongInfoUIState()
}