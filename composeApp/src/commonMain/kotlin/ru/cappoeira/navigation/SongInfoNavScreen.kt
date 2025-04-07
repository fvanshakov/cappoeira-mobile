package ru.cappoeira.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.songInfo.screen.SongInfoScreen

class SongInfoNavScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        SongInfoScreen(
            koinViewModel(
                parameters = { parametersOf(id) }
            )
        )
    }
}