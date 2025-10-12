package ru.cappoeira.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.cappoeira.analytics.Analytics
import ru.cappoeira.app.search.screen.SearchScreen

class SearchNavScreen(
    private val initialTag: Triple<String, String, Boolean>?
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SearchScreen(initialTag = initialTag) { id, songName ->
            Analytics.logEvent(
                name = "clickOnCard",
                params = mapOf(
                    "screen" to "Search",
                    "cardName" to songName,
                    "cardId" to id
                )
            )
            navigator.push(SongInfoNavScreen(id, songName))
        }
    }
}