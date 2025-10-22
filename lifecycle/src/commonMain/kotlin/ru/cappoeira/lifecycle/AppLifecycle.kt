package ru.cappoeira.lifecycle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

object AppLifecycle {

    enum class AppState {
        FOREGROUND,
        BACKGROUND
    }

    private val _stateFlow = MutableStateFlow<AppState>(AppState.FOREGROUND)
    val stateFlow = _stateFlow.asSharedFlow()

    fun notifyForeground() {
        _stateFlow.tryEmit(AppState.FOREGROUND)
    }

    fun notifyBackground() {
        _stateFlow.tryEmit(AppState.BACKGROUND)
    }
}