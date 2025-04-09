package ru.cappoeira.app.search.screen

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
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

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
                viewModel.onSearchTextChanged(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("ПОИСК") },
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        when (state) {
            is SearchUiState.Loading -> {
                LazyColumn {
                    items(5) {
                        SkeletonCard()
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }

            is SearchUiState.Success -> {
                val songs = (state as SearchUiState.Success).songs
                if (songs.isEmpty()) {
                    Text(
                        text = "Ничего не найдено",
                        style = MaterialTheme.typography.caption
                    )
                } else {
                    LazyColumn {
                        itemsIndexed(songs) { index, song ->
                            SongCard(song, onClickOnCard)
                            Spacer(Modifier.height(8.dp))
                            if (index == songs.lastIndex - 3) {
                                 viewModel.onLimitReached(search)
                            }
                        }
                    }
                }
            }

            is SearchUiState.Error -> {
                Text(
                    text = "Ошибка: ${(state as SearchUiState.Error).message}",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
