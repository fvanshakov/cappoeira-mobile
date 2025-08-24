package ru.cappoeira.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.songInfo.screen.SongInfoScreen
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel
import ru.cappoeira.app.topbar.ScaffoldWithToolbar

class SongInfoNavScreen(
    private val id: String,
    private val songName: String
) : Screen {

    override val key: String
        get() = "song_info_${id}_${songName}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SongInfoScreen(
            songName,
            { navigator.pop() },
            { id, name ->
                navigator.push(SongInfoNavScreen(id, name))
            },
            koinViewModel(
                key = "${SongInfoViewModel::class}$id",
                parameters = { parametersOf(id) }
            )
        )
    }
}