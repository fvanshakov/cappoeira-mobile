package ru.cappoeira.app.videoPlayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cappoeira.app.videoPlayer.controller.PlaybackMediaItem
import ru.cappoeira.app.videoPlayer.controller.PlaybackState
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController
import ru.cappoeira.lifecycle.AppLifecycle

class PlaybackViewModel(
    private val playbackStateController: PlaybackStateController,
    private val url: String,
    private val id: String
) : ViewModel() {
    private val _playBackState = MutableStateFlow<PlaybackState>(PlaybackState.Buffering)
    private val _progressState = MutableStateFlow(0F)
    val playbackState = _playBackState.asStateFlow()

    val playBackStateUI = _playBackState.asStateFlow()
    val progressStateUI = _progressState.asStateFlow()

    fun getPlatformController(): PlaybackStateController {
        return playbackStateController
    }

    init {
        initialise()
    }

    private fun initialise() {
        viewModelScope.launch {
            handleStartPlayback(
                PlaybackMediaItem(
                    id = id,
                    url = url
                )
            )
            AppLifecycle.stateFlow.collect {
                if (it == AppLifecycle.AppState.BACKGROUND) {
                    pause()
                }
            }
        }
    }

    private fun handleStartPlayback(playbackMediaItem: PlaybackMediaItem) {
        try {
            playbackStateController.initPlayer(
                callback = { currentPosition, duration ->
                    _progressState.value = if (duration > 0) currentPosition.toFloat() / duration else 0f
                },
                playbackState = {
                    _playBackState.value = it
                }
            )
            playPause()
            playbackStateController.setItem(playbackMediaItem)
        } catch (e: Exception) {

        }
    }

    fun onSeekChanged(seekValue: Float) {
        viewModelScope.launch {
            _progressState.value = seekValue
            playbackStateController.seekTo((seekValue * playbackStateController.duration()).toLong())
        }
    }

    fun moveForward(secs: Double) {
        playbackStateController.moveForward(secs)
    }

    fun pause() {
        viewModelScope.launch {
            playbackStateController.pause(
                playbackState = {
                    _playBackState.value = it
                }
            )
        }
    }

    fun playPause() {
        viewModelScope.launch {
            if (_playBackState.value == PlaybackState.Playing) {
                playbackStateController.pause(
                    playbackState = {
                        _playBackState.value = it
                    }
                )
            } else {
                playbackStateController.play(
                    playbackState = { _playBackState.value = it }
                )
            }
        }
    }

    override fun onCleared() {
        playbackStateController.pause(
            playbackState = {
                _playBackState.value = it
            }
        )
        viewModelScope.cancel()
        super.onCleared()
    }
}