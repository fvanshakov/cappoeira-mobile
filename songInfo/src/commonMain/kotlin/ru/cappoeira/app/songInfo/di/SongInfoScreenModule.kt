package ru.cappoeira.app.songInfo.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.cappoeira.app.network.di.networkModule
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel
import ru.cappoeira.app.videoPlayer.di.videoPlayerModule

val songInfoScreenModule = module {
    includes(networkModule, videoPlayerModule)
    viewModel { (id: String) -> SongInfoViewModel(get(), id) }
}