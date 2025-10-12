package ru.cappoeira.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.songInfo.screen.SongInfoScreen
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel

class SongInfoNavScreen(
    private val id: String,
    private val songName: String
) : Screen {

    override val key: String
        get() = "song_info_${id}_${songName}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        key(id) {
            SongInfoScreen(
                songName,
                onBackPressed = {
                    navigator.pop()
                    if (navigator.lastItem is SearchNavScreen) {
                        navigator.replaceAll(SearchNavScreen(null))
                    }
                },
                onNextPressed = { id, name ->
                    navigator.push(SongInfoNavScreen(id, name))
                },
                onTagPressed = { navigator.replaceAll(SearchNavScreen(it)) },
                viewModel = koinViewModel(
                    key = "${SongInfoViewModel::class}$id",
                    parameters = { parametersOf(id) }
                )
            )
        }
    }
}