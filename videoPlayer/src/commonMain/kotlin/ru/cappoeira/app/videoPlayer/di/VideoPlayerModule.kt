package ru.cappoeira.app.videoPlayer.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.cappoeira.app.network.di.networkModule
import ru.cappoeira.app.videoPlayer.PlaybackMediaItemRepository
import ru.cappoeira.app.videoPlayer.PlaybackMediaItemRepositoryImpl
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

expect val platformVideoPlayerCoreModule: Module

val commonVideoPlayerModule = module {
    includes(networkModule)
    single<PlaybackMediaItemRepository> { PlaybackMediaItemRepositoryImpl(get()) }
    viewModel { (id: String) -> PlaybackViewModel(get(), get(), id) }
}

val videoPlayerModule: Module
    get() = module {
        includes(commonVideoPlayerModule + platformVideoPlayerCoreModule)
    }