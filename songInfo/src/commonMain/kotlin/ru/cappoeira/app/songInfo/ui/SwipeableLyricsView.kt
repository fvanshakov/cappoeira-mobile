package ru.cappoeira.app.songInfo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip
import ru.cappoeira.app.designSystem.elements.gaps.SimpleGap
import ru.cappoeira.app.songInfo.modles.SongInfoTextLineViewObject
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel

@Composable
fun SwipeableLyricsView(
    lyrics: List<SongInfoTextLineViewObject>,
    scrollState: LazyListState,
    lyricsTypes: List<SongInfoViewModel.LyricsType>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(lyrics) { index, line ->
                val alpha by animateFloatAsState(
                    targetValue = calculateAlpha(
                        currentIndex = scrollState.firstVisibleItemIndex + 1,
                        itemIndex = index
                    ),
                    label = "text_alpha"
                )

                Box(
                    modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (line.isChoirPart) {
                        OutlinedChip(
                            color = Color.White,
                            alpha = alpha
                        ) {
                            LyricsText(line, lyricsTypes, alpha)
                        }
                    } else {
                        LyricsText(line, lyricsTypes, alpha)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(400.dp))
            }
        }
    }
}

@Composable
private fun LyricsText(
    line: SongInfoTextLineViewObject,
    lyricsTypes: List<SongInfoViewModel.LyricsType>,
    alpha: Float
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp)
            .alpha(alpha),
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = SongInfoViewModel.LyricsType.TEXT in lyricsTypes,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            SimpleGap()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = line.text,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
            )
        }
        if (line.translation.isNotEmpty()) {
            AnimatedVisibility(
                visible = SongInfoViewModel.LyricsType.TRANSLATION in lyricsTypes,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                SimpleGap()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = line.translation,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
        if (line.transcription.isNotEmpty()) {
            AnimatedVisibility(
                visible = SongInfoViewModel.LyricsType.TRANSCRIPTION in lyricsTypes,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                SimpleGap()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = line.transcription,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

private fun calculateAlpha(currentIndex: Int, itemIndex: Int): Float {
    return when {
        itemIndex == currentIndex -> 1f
        else -> 1f - (itemIndex - currentIndex) * 0.4f
    }
}