package ru.cappoeira.app.songInfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cappoeira.app.analytics.Analytics
import ru.cappoeira.app.network.NetworkResult
import ru.cappoeira.app.network.SongInfoApi
import ru.cappoeira.app.songInfo.events.SongInfoEvent
import ru.cappoeira.app.songInfo.formatter.SongInfoByIdFormatter.format
import ru.cappoeira.app.songInfo.state.SongInfoUIState

class SongInfoViewModel(
    private val songInfoApi: SongInfoApi,
    private val id: String
): ViewModel() {
    private val _uiState = MutableStateFlow<SongInfoUIState>(SongInfoUIState.Loading)
    private val _lyricsType = MutableStateFlow(listOf(LyricsType.TEXT))
    val uiState: StateFlow<SongInfoUIState> = _uiState.asStateFlow()
    val lyricsType = _lyricsType.asStateFlow()

    init {
        Analytics.sendEvent(
            eventName = "openScreen",
            params = mapOf(
                "screen" to "SongInfo"
            )
        )
        viewModelScope.launch {
            try {
                when(val callResult = songInfoApi.getSongInfoById(id)) {
                    is NetworkResult.Success -> {
                        _uiState.value = SongInfoUIState.Success(
                            callResult.data.format()
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = SongInfoUIState.Error(
                            callResult.message
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = SongInfoUIState.Error("Ошибка загрузки: ${e.message}")
            }
        }
    }

    private fun switchSongLyricsType(type: LyricsType) {
        if (_lyricsType.value.size == 1 && _lyricsType.value[0] == type) {
            return
        }
        if (_lyricsType.value.contains(type)) {
            _lyricsType.value -= type
        } else {
            _lyricsType.value += type
        }
    }

    fun handleEvent(event: SongInfoEvent) {
        when(event) {
            is SongInfoEvent.ChangeSongLyricsType -> {
                switchSongLyricsType(event.songType)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    enum class LyricsType(val stringValue: String) {
        TEXT("текст"),
        TRANSLATION("перевод"),
        TRANSCRIPTION("транскрипция")
    }
}