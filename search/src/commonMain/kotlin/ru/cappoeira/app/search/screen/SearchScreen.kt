package ru.cappoeira.app.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.cappoeira.app.designSystem.elements.chips.BigChip
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip
import ru.cappoeira.app.designSystem.elements.gaps.ScreenTopGap
import ru.cappoeira.app.designSystem.elements.gaps.TopBarGap
import ru.cappoeira.app.designSystem.elements.searchField.SearchField
import ru.cappoeira.app.designSystem.elements.texts.GeneralText
import ru.cappoeira.app.designSystem.elements.topbar.SwipeableTopbar
import ru.cappoeira.app.search.SongType
import ru.cappoeira.app.search.components.SkeletonCard
import ru.cappoeira.app.search.components.SongCard
import ru.cappoeira.app.search.events.SearchEvent
import ru.cappoeira.app.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onClickOnCard: (id: String, songName: String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val songType by viewModel.songType.collectAsState()

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
    }
}
