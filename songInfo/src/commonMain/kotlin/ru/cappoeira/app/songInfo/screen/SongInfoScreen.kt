package ru.cappoeira.app.songInfo.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.songInfo.state.SongInfoUIState
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel
import ru.cappoeira.app.videoPlayer.PlaybackView
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

@Composable
fun SongInfoScreen(
    viewModel: SongInfoViewModel
) {
    val state by viewModel.uiState.collectAsState()
    var playBackViewModel: PlaybackViewModel? = (state as? SongInfoUIState.Success)?.vo?.let { vo ->
        vo.videoUrl?.let { url ->
            koinViewModel(
                parameters = { parametersOf(url, vo.id) }
            )
        }
    }

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
                val url = vo.videoUrl
                val usablePlayBackViewModel = playBackViewModel
                if (url != null && usablePlayBackViewModel != null) {
                    PlaybackView(
                        isCustom = false,
                        id = vo.id,
                        url = url,
                        viewModel = usablePlayBackViewModel
                    )
                } else {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("ПРОСТИТЕ")
                        Text("ПОКА ЧТО ДАННАЯ ПЕСНЯ НЕ ГОТОВА")
                    }
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

    DisposableEffect(Unit) {
        onDispose {
            playBackViewModel = null
        }
    }
}