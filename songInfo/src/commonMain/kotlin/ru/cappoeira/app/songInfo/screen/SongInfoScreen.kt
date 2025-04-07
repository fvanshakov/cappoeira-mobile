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
import ru.cappoeira.app.songInfo.state.SongInfoUIState
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel
import ru.cappoeira.app.videoPlayer.PlaybackView

@Composable
fun SongInfoScreen(
    viewModel: SongInfoViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.8f)
    ) {
        when(state) {
            is SongInfoUIState.Loading -> {
                Text("Загрузка")
            }
            is SongInfoUIState.Success -> {
                val vo = (state as SongInfoUIState.Success).vo
                vo.videoUrl?.let { url ->
                    PlaybackView(
                        isCustom = false,
                        id = vo.id,
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