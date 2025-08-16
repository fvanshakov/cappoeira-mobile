package ru.cappoeira.app.songInfo.ui

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import ru.cappoeira.app.designSystem.elements.gaps.SimpleGap
import ru.cappoeira.app.songInfo.modles.SongInfoTextLineViewObject

@Composable
fun SwipeableLyricsView(
    lyrics: List<SongInfoTextLineViewObject>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

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

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(16.dp)
                        .alpha(alpha),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = line.text,
                        textAlign = TextAlign.Center,
                        fontWeight = if (line.isChoirPart) {
                            FontWeight.ExtraBold
                        } else {
                            FontWeight.Medium
                        },
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                    )
                    if (line.translation.isNotEmpty()) {
                        SimpleGap()
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = line.translation,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                    if (line.transcription.isNotEmpty()) {
                        SimpleGap()
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = line.transcription,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(400.dp))
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