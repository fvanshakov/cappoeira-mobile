package ru.cappoeira.app.videoPlayer.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.cappoeira.app.androidApp.CapoAndroidApplication
import ru.cappoeira.app.videoPlayer.CachedPlaybackDataSourceFactory
import ru.cappoeira.app.videoPlayer.CachedPlaybackDataSourceFactoryImpl
import ru.cappoeira.app.videoPlayer.Media3PlayerComponent
import ru.cappoeira.app.videoPlayer.Media3PlayerComponentImpl
import ru.cappoeira.app.videoPlayer.controller.PlaybackStateController

actual val platformVideoPlayerCoreModule: Module = module {
    single<Media3PlayerComponent> { Media3PlayerComponentImpl(CapoAndroidApplication.appInstance, get()) }
    single<PlaybackStateController> { PlaybackStateController(get()) }
    single<CachedPlaybackDataSourceFactory> {
        CachedPlaybackDataSourceFactoryImpl(
            CapoAndroidApplication.appInstance
        )
    }
}