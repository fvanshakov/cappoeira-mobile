package ru.cappoeira.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.cappoeira.app.search.screen.SearchScreen

object SearchNavScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SearchScreen { id ->
            navigator.push(SongInfoNavScreen(id))
        }
    }
}