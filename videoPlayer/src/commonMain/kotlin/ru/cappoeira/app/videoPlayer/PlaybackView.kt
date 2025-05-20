package ru.cappoeira.app.videoPlayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.videoPlayer.controller.PlaybackState
import ru.cappoeira.app.videoPlayer.view.PlatformMediaPlayerView

@Composable
fun PlaybackView(
    isCustom: Boolean,
    url: String,
    id: String,
    viewModel: PlaybackViewModel
) {
    val controller = remember { viewModel.getPlatformController() }

    Box(
        Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(Color.Black)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        PlatformMediaPlayerView(
            modifier = Modifier,
            controller,
            hidePlayback = isCustom
        )
    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(Color.Black)
    )
}

@Composable
fun PlaybackBufferingIndicator(
    id: String,
    url: String
) {
    val viewModel: PlaybackViewModel = koinViewModel(
        parameters = { parametersOf(url, id) }
    )
    val state = viewModel.playBackStateUI.collectAsState()
    AnimatedVisibility(
        visible = state.value == PlaybackState.Buffering,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CircularProgressIndicator(
            color = Color.Blue,
            strokeWidth = 4.dp,
            modifier = Modifier.wrapContentSize()
        )
    }
}


@Composable
fun PlaybackSeekBar() {
    val viewModel: PlaybackViewModel = koinViewModel()
    val state = viewModel.progressStateUI.collectAsState()
    Column {
        Slider(
            value = state.value,
            onValueChange = {
                viewModel.onSeekChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = SliderDefaults.colors(
                activeTrackColor = Color.Red,
                inactiveTickColor = Color.Red.copy(alpha = 1.0f),
                thumbColor = Color.Red
            )
        )
    }
}


@Composable
fun PlayPauseControl(
    url: String,
    id: String,
    onPlayPause: () -> Unit
) {
    val viewModel: PlaybackViewModel = koinViewModel(
        parameters = { parametersOf(url, id) }
    )
    val state = viewModel.playBackStateUI.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        IconButton(
            onClick = onPlayPause,
            modifier = Modifier
                .size(56.dp)
                .background(Color.Gray, CircleShape)
        ) {
            val icon = if (state.value == PlaybackState.Playing) "❚❚" else "▶"
            Text(icon, fontSize = 24.sp, color = Color.White)
        }
    }
}