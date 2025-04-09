package ru.cappoeira.app

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import ru.cappoeira.app.analytics.Analytics
import ru.cappoeira.app.search.di.searchScreenModule
import ru.cappoeira.app.songInfo.di.songInfoScreenModule
import ru.cappoeira.navigation.SearchNavScreen

@Composable
@Preview
fun App() {
    Analytics.initialize()
    KoinApplication(
        application = { modules(searchScreenModule + songInfoScreenModule) }
    ) {
        Navigator(SearchNavScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}