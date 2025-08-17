package ru.cappoeira.app.songInfo.events

import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel

sealed class SongInfoEvent {
    data class ChangeSongInfoType(val songType: SongInfoViewModel.InfoType) : SongInfoEvent()
}