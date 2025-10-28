package ru.cappoeira.app.videoPlayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.cappoeira.app.designSystem.elements.colors.DesignColors
import ru.cappoeira.app.designSystem.elements.icons.PlayIcon
import ru.cappoeira.app.videoPlayer.controller.PlaybackState
import ru.cappoeira.app.videoPlayer.view.PlatformMediaPlayerView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaybackView(
    isCustom: Boolean,
    viewModel: PlaybackViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(Color.Black),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            PlatformMediaPlayerView(
                modifier = Modifier,
                viewModel,
                hidePlayback = isCustom
            )
            PlaybackBufferingIndicator(viewModel)
            PlayPauseControl(viewModel)
            PlaybackSeekBar(viewModel)
        }
    }
}

@Composable
fun PlaybackBufferingIndicator(
    viewModel: PlaybackViewModel
) {
    val state = viewModel.playBackStateUI.collectAsState()
    AnimatedVisibility(
        visible = state.value == PlaybackState.Buffering,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CircularProgressIndicator(
            color = DesignColors.Orange,
            strokeWidth = 4.dp,
            modifier = Modifier.wrapContentSize()
        )
    }
}


@Composable
fun PlaybackSeekBar(
    viewModel: PlaybackViewModel
) {
    val state = viewModel.progressStateUI.collectAsState()
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Slider(
            value = state.value,
            onValueChange = {
                viewModel.onSeekChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = SliderDefaults.colors(
                activeTrackColor = DesignColors.Orange,
                inactiveTickColor = Color.White,
                thumbColor = DesignColors.Orange
            )
        )
    }
}


@Composable
fun PlayPauseControl(
    viewModel: PlaybackViewModel,
) {
    val state = viewModel.playBackStateUI.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val interactionSource = remember { MutableInteractionSource() }


        AnimatedVisibility(
            visible = state.value == PlaybackState.Paused,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PlayIcon(
                onClick = {
                    viewModel.playPause()
                }
            )
        }
    }
}