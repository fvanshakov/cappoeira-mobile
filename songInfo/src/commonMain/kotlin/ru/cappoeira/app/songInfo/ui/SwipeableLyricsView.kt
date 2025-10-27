package ru.cappoeira.app.songInfo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cappoeira.app.designSystem.elements.colors.DesignColors
import ru.cappoeira.app.designSystem.elements.gaps.SimpleGap
import ru.cappoeira.app.songInfo.modles.SongInfoTextLineViewObject
import ru.cappoeira.app.songInfo.modles.SongTextChunkViewObject
import ru.cappoeira.app.songInfo.viewmodel.SongInfoViewModel

@Composable
fun SwipeableLyricsView(
    lyrics: List<SongInfoTextLineViewObject>,
    scrollState: LazyListState,
    lyricsTypes: List<SongInfoViewModel.LyricsType>,
    onDefinitionClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) {
            itemsIndexed(lyrics) { index, line ->

                Box(
                    modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    LyricsText(line, lyricsTypes, line.isChoirPart, onDefinitionClicked)
                }
            }

            item {
                Spacer(modifier = Modifier.height(400.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LyricsText(
    line: SongInfoTextLineViewObject,
    lyricsTypes: List<SongInfoViewModel.LyricsType>,
    isChoir: Boolean,
    onDefinitionClicked: (String) -> Unit
) {

    val columnPaddings = if (lyricsTypes.size == 1) {
        4.dp
    } else {
        8.dp
    }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = columnPaddings),
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
                fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                color = Color.White,
                fontFamily = FontFamily.Default,
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
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    line.translation.forEach { chunk ->
                        if (chunk.definition == null) {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                textAlign = TextAlign.Center,
                                text = chunk.text,
                                fontFamily = FontFamily.Default,
                                fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .background(DesignColors.Orange, CircleShape)
                                    .clickable {
                                        onDefinitionClicked(chunk.definition)
                                    },
                                textAlign = TextAlign.Center,
                                text = chunk.text,
                                fontFamily = FontFamily.Default,
                                fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                                fontSize = 20.sp,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
        if (line.transcription.isNotEmpty()) {
            AnimatedVisibility(
                visible = SongInfoViewModel.LyricsType.TRANSCRIPTION in lyricsTypes,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                SimpleGap()
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val buffer = ArrayDeque<SongTextChunkViewObject>()

                    line.transcription.forEachIndexed { index, chunk ->

                        if (buffer.lastOrNull()?.text?.endsWith(" ") == true) {
                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                buffer.forEach { bufferedElement ->
                                    val definition = bufferedElement.definition
                                    if (definition == null) {
                                        Text(
                                            modifier = Modifier.wrapContentWidth(),
                                            textAlign = TextAlign.Center,
                                            text = bufferedElement.text,
                                            fontFamily = FontFamily.Default,
                                            fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    } else {
                                        Text(
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .background(DesignColors.Orange, CircleShape)
                                                .clickable {
                                                    onDefinitionClicked(definition)
                                                },
                                            textAlign = TextAlign.Center,
                                            text = bufferedElement.text,
                                            fontFamily = FontFamily.Default,
                                            fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                        )
                                    }
                                }
                            }
                            buffer.clear()
                        }

                        buffer.add(chunk)
                    }

                    if (buffer.isNotEmpty()) {
                        Row(modifier = Modifier.wrapContentWidth()) {
                            buffer.forEach { bufferedElement ->
                                val definition = bufferedElement.definition
                                if (definition == null) {
                                    Text(
                                        modifier = Modifier.wrapContentWidth(),
                                        textAlign = TextAlign.Center,
                                        text = bufferedElement.text,
                                        fontFamily = FontFamily.Default,
                                        fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .background(DesignColors.Orange, CircleShape)
                                            .clickable {
                                                onDefinitionClicked(definition)
                                            },
                                        textAlign = TextAlign.Center,
                                        text = bufferedElement.text,
                                        fontFamily = FontFamily.Default,
                                        fontWeight = if (isChoir) FontWeight.Bold else FontWeight.Light,
                                        fontSize = 20.sp,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
