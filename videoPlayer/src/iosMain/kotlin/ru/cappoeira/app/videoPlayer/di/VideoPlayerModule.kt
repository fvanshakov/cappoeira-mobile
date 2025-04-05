package ru.cappoeira.app.videoPlayer.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController

actual val platformVideoPlayerCoreModule: Module = module {
    single<PlaybackStateController> { PlaybackStateController() }
}