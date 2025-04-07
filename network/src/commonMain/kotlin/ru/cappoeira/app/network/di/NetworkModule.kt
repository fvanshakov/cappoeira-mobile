package ru.cappoeira.app.network.di

import org.koin.dsl.module
import ru.cappoeira.app.network.SongInfoApi
import ru.cappoeira.app.network.SongInfoApiImpl

val networkModule = module {
    single<SongInfoApi> { SongInfoApiImpl() }
}