package ru.cappoeira.app.songInfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.designSystem.elements.gaps.ScreenTopGap
import ru.cappoeira.app.designSystem.elements.gaps.TopBarGap
import ru.cappoeira.app.designSystem.elements.icons.BackIcon
import ru.cappoeira.app.designSystem.elements.texts.GeneralText
import ru.cappoeira.app.designSystem.elements.topbar.SwipeableTopbar
import ru.cappoeira.app.songInfo.state.SongInfoUIState
import ru.cappoeira.app.songInfo.ui.SwipeableLyricsView
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel
import ru.cappoeira.app.videoPlayer.PlaybackView
import ru.cappoeira.app.videoPlayer.PlaybackViewModel

@Composable
fun SongInfoScreen(
    songName: String,
    onBackPressed: () -> Unit,
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

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.Black)
    ) {
        ScreenTopGap()

        when(state) {
            is SongInfoUIState.Loading -> {
                GeneralText("Загрузка")
            }
            is SongInfoUIState.Success -> {
                val vo = (state as SongInfoUIState.Success).vo
                val url = vo.videoUrl
                val usablePlayBackViewModel = playBackViewModel
                if (url != null && usablePlayBackViewModel != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(color = Color.Black),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(
                            Modifier
                                .height(75.dp)
                        )
                        PlaybackView(
                            isCustom = false,
                            id = vo.id,
                            url = url,
                            viewModel = usablePlayBackViewModel
                        )
                        Spacer(
                            Modifier
                                .height(25.dp)
                        )
                    }
                    SwipeableLyricsView(vo.songLines)
                } else {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .height(250.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        GeneralText("Простите", color = Color.White)
                        GeneralText("Пока что данная песня не готова", color = Color.White)
                    }
                }
            }
            is SongInfoUIState.Error -> {
                GeneralText(
                    text = "Ошибка: ${(state as SongInfoUIState.Error).message}",
                    color = Color.White
                )
            }
        }
    }

    SwipeableTopbar(
        listState,
        isMainTheme = false
    ) {
        ScreenTopGap()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .wrapContentWidth()
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                BackIcon(isMainTheme = false, onClick = onBackPressed)
            }
            GeneralText(
                text = songName,
                color = Color.Black
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            playBackViewModel?.playPause()
            playBackViewModel = null
        }
    }
}