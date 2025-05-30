package ru.cappoeira.app.songInfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
    ) {
        item { TopBarGap() }

        when(state) {
            is SongInfoUIState.Loading -> {
                item { GeneralText("Загрузка") }
            }
            is SongInfoUIState.Success -> {
                val vo = (state as SongInfoUIState.Success).vo
                val url = vo.videoUrl
                val usablePlayBackViewModel = playBackViewModel
                if (url != null && usablePlayBackViewModel != null) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            PlaybackView(
                                isCustom = false,
                                id = vo.id,
                                url = url,
                                viewModel = usablePlayBackViewModel
                            )
                        }
                    }
                } else {
                    item {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .height(250.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            GeneralText("Простите")
                            GeneralText("Пока что данная песня не готова")
                        }
                    }
                }
            }
            is SongInfoUIState.Error -> {
                item {
                    GeneralText(
                        text = "Ошибка: ${(state as SongInfoUIState.Error).message}",
                    )
                }
            }
        }
    }

    SwipeableTopbar(
        listState
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
                BackIcon(onBackPressed)
            }
            GeneralText(
                text = songName,
                color = Color.White
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            playBackViewModel = null
        }
    }
}