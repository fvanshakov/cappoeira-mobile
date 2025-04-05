package ru.cappoeira.app.videoPlayer.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.cappoeira.app.videoPlayer.PlaybackMediaItemRepository
import ru.cappoeira.app.videoPlayer.PlaybackMediaItemRepositoryImpl
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

expect val platformVideoPlayerCoreModule: Module

val commonVideoPlayerModule = module {
    single<PlaybackMediaItemRepository> { PlaybackMediaItemRepositoryImpl() }
    viewModel { PlaybackViewModel(get(), get()) }
}

val videoPlayerModule: Module
    get() = module {
        includes(commonVideoPlayerModule + platformVideoPlayerCoreModule)
    }