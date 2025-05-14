package ru.cappoeira.app.search.screen

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import ru.cappoeira.app.designSystem.elements.chips.BigChip
import ru.cappoeira.app.designSystem.elements.chips.Chip
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip
import ru.cappoeira.app.designSystem.elements.chips.OutlinedDottedChip
import ru.cappoeira.app.designSystem.elements.colors.DesignColors
import ru.cappoeira.app.designSystem.elements.gaps.ScreenTopGap
import ru.cappoeira.app.designSystem.elements.gaps.TopBarGap
import ru.cappoeira.app.designSystem.elements.searchField.SearchField
import ru.cappoeira.app.designSystem.elements.texts.GeneralText
import ru.cappoeira.app.designSystem.elements.topbar.SwipeableTopbar
import ru.cappoeira.app.search.components.SkeletonCard
import ru.cappoeira.app.search.components.SongCard
import ru.cappoeira.app.search.state.SearchUiState
import ru.cappoeira.app.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onClickOnCard: (id: String, songName: String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var search by remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(16.dp)
    ) {

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            item { TopBarGap() }

            when (state) {

                is SearchUiState.Loading -> {
                    items(12) {
                        SkeletonCard()
                    }
                }

                is SearchUiState.Success -> {
                    val songs = (state as SearchUiState.Success).songs
                    if (songs.isEmpty()) {
                        item { GeneralText("Ничего не найдено") }
                    } else {
                        itemsIndexed(songs) { index, song ->
                            SongCard(song, onClickOnCard)
                            if (index == songs.lastIndex - 3) {
                                viewModel.onLimitReached(search)
                            }
                        }
                    }
                }

                is SearchUiState.Error -> {
                    item { GeneralText("Ошибка: ${(state as SearchUiState.Error).message}") }
                }
            }
        }
    }

    SwipeableTopbar(
        listState
    ) {
        ScreenTopGap()
        SearchField(viewModel::onSearchTextChanged)
    }
}
