package ru.cappoeira.app.videoPlayer.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.cappoeira.app.network.di.networkModule
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

expect val platformVideoPlayerCoreModule: Module

val commonVideoPlayerModule = module {
    includes(networkModule)
    viewModel { (url: String, id: String) -> PlaybackViewModel(get(),url, id) }
}

val videoPlayerModule: Module
    get() = module {
        includes(commonVideoPlayerModule + platformVideoPlayerCoreModule)
    }