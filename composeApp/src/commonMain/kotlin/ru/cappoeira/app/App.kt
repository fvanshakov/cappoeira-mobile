package ru.cappoeira.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import ru.cappoeira.app.search.di.searchScreenModule
import ru.cappoeira.app.search.screen.SearchScreen
import ru.cappoeira.app.songInfo.di.songInfoScreenModule

@Composable
@Preview
fun App() {
    KoinApplication(
        application = { modules(searchScreenModule + songInfoScreenModule) }
    ) {
        MaterialTheme {
            SearchScreen() {}
        }
    }
}