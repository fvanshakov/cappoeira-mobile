package ru.cappoeira.app.songInfo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.songInfo.state.SongInfoUIState
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel
import ru.cappoeira.app.videoPlayer.PlaybackView

@Composable
fun SongInfoScreen(
    id: String,
) {
   val  viewModel: SongInfoViewModel = koinViewModel(
       parameters = { parametersOf(id) }
   )
    val state by viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        when(state) {
            is SongInfoUIState.Loading -> {

            }

            is SongInfoUIState.Success -> {
                val vo = (state as SongInfoUIState.Success).vo
                vo.videoUrl?.let { url ->
                    PlaybackView(
                        isCustom = false,
                        id = id,
                        url = url
                    )
                }
            }

            is SongInfoUIState.Error -> {
                Text(
                    text = "Ошибка: ${(state as SongInfoUIState.Error).message}",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}