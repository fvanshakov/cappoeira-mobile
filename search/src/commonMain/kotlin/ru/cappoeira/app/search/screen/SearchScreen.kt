package ru.cappoeira.app.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.koin.compose.viewmodel.koinViewModel
import ru.cappoeira.app.designSystem.elements.BottomSheet
import ru.cappoeira.app.designSystem.elements.chips.BigChip
import ru.cappoeira.app.designSystem.elements.chips.Chip
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip
import ru.cappoeira.app.designSystem.elements.gaps.ScreenTopGap
import ru.cappoeira.app.designSystem.elements.gaps.SimpleGap
import ru.cappoeira.app.designSystem.elements.gaps.TopBarGap
import ru.cappoeira.app.designSystem.elements.searchField.SearchField
import ru.cappoeira.app.designSystem.elements.texts.GeneralText
import ru.cappoeira.app.designSystem.elements.topbar.SwipeableTopbar
import ru.cappoeira.app.search.SongType
import ru.cappoeira.app.search.components.SkeletonCard
import ru.cappoeira.app.search.components.SongCard
import ru.cappoeira.app.search.events.SearchEvent
import ru.cappoeira.app.search.viewmodel.SearchViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onClickOnCard: (id: String, songName: String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val songType by viewModel.songType.collectAsState()
    val tags by viewModel.tags.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val corridoListState = rememberLazyListState()
    val ladanihnaListState = rememberLazyListState()
    val skeletonListState = rememberLazyListState()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            val isEmptyResult = when(songType) {
                SongType.LADAINHA -> state.ladainhaState.songs.isEmpty()
                SongType.CORRIDO -> state.corridoState.songs.isEmpty()
            }

            if (state.error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    state.error?.let {
                        GeneralText(it.errorMessage)
                    }
                }
            }

            this@Column.AnimatedVisibility(
                visible = isEmptyResult && !state.isSkeletonShown && state.error == null,
                enter = fadeIn(
                    animationSpec = tween(delayMillis = 150, durationMillis = 150),
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 150),
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    GeneralText("Не удалось найти ничего по запросу: ${viewModel.searchText.value}")
                }
            }

            this@Column.AnimatedVisibility(
                visible = state.isSkeletonShown,
                enter = fadeIn(
                    animationSpec = tween(delayMillis = 150, durationMillis = 150),
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 150),
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    state = skeletonListState,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item { TopBarGap() }

                    items(12) {
                        SkeletonCard()
                    }
                }
            }

            this@Column.AnimatedVisibility(
                visible = songType == SongType.CORRIDO && !state.isSkeletonShown,
                enter = slideInHorizontally(
                    animationSpec = tween(delayMillis = 350, durationMillis = 300),
                    initialOffsetX = { -it } // slides in from right
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 300),
                    targetOffsetX = { -it } // slides out to right
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    state = corridoListState,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    item { TopBarGap() }

                    itemsIndexed(state.corridoState.songs, key = { _, song -> song.id }) { index, song ->
                        SongCard(song, onClickOnCard)
                        if (index >= state.corridoState.songs.lastIndex - 5) {
                            viewModel.handleEvent(SearchEvent.Paginate)
                        }
                    }
                }
            }

            this@Column.AnimatedVisibility(
                visible = songType == SongType.LADAINHA && !state.isSkeletonShown,
                enter = slideInHorizontally(
                    animationSpec = tween(delayMillis = 350, durationMillis = 300),
                    initialOffsetX = { it }
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 300),
                    targetOffsetX = { it }
                ),
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                LazyColumn(
                    state = ladanihnaListState,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item { TopBarGap() }

                    itemsIndexed(state.ladainhaState.songs, key = { _, song -> song.id }) { index, song -> SongCard(song, onClickOnCard)
                        if (index >= state.ladainhaState.songs.lastIndex - 5) {
                            viewModel.handleEvent(SearchEvent.Paginate)
                        }
                    }
                }
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                onDismiss = { showBottomSheet = false }
            ) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    tags.entries.forEach { (key, value) ->
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                GeneralText(text = key)
                            }
                        }
                        item {
                            FlowRow {
                                value.values.forEach { tag ->
                                    val event = SearchEvent.SelectTag(
                                        key = key,
                                        value = tag.value,
                                        isPlural = value.isPlural
                                    )
                                    BigChip(
                                        isSelected = tag.isChosen,
                                        text = tag.value,
                                        onClick = {
                                            viewModel.handleEvent(
                                                event
                                            )
                                        },
                                        additionalColor = Color.LightGray,
                                        paddingValues = PaddingValues(4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val swipeableListState = when(songType) {
        SongType.LADAINHA -> ladanihnaListState
        SongType.CORRIDO -> corridoListState
    }

    SwipeableTopbar(
        swipeableListState
    ) {
        ScreenTopGap()
        SearchField(viewModel.searchText.value) { text -> viewModel.handleEvent(SearchEvent.ChangeSearchText(text)) }
        OutlinedChip(
            color = Color.White,
            isShimmering = false,
            onClick = {  },
            Content = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    SongType.entries.forEach { type ->
                        BigChip(
                            isSelected = songType == type,
                            text = type.value,
                            onClick = {
                                viewModel.handleEvent(SearchEvent.ChangeSongType(type))
                            }
                        )
                    }
                }
            }
        )
        Chip(
            isSelected = tags.entries.flatMap { it.value.values }.any { it.isChosen },
            text = "тэги",
            isClosable = true,
            inactiveColor = Color.White,
            onClose = { viewModel.handleEvent(SearchEvent.ClearTags) },
            onClick = { showBottomSheet = true }
        )
    }
}
