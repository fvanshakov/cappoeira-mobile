package ru.cappoeira.app.search.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.cappoeira.app.network.di.networkModule
import ru.cappoeira.app.search.viewmodel.SearchViewModel

val searchScreenModule = module {
    includes(networkModule)
    viewModel { SearchViewModel(get()) }
}