package ru.cappoeira.app.songInfo.events

import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel

sealed class SongInfoEvent {
    data class ChangeSongLyricsType(val songType: SongInfoViewModel.LyricsType) : SongInfoEvent()

    data class ChangeChosenFunctionality(val functionalityType: SongInfoViewModel.FunctionalityType) : SongInfoEvent()
}