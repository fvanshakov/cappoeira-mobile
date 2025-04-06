package ru.cappoeira.app.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cappoeira.app.network.NetworkResult
import ru.cappoeira.app.network.SongInfoApi
import ru.cappoeira.app.search.formatter.SongInfoBySearchFormatter.format
import ru.cappoeira.app.search.state.SearchUiState
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class SearchViewModel(
    private val songInfoApi: SongInfoApi
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(emptyList()))
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        _searchText
            .debounce(250)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isBlank()) {
                    _uiState.value = SearchUiState.Success(emptyList())
                    return@onEach
                }
                fetchUsers(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun fetchUsers(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val searchText = Base64.encode(query.encodeToByteArray())
                when(val callResult = songInfoApi.getSongsInfosByTextSearch(searchText)) {
                    is NetworkResult.Success -> {
                        _uiState.value = SearchUiState.Success(
                            callResult.data.songs.map { it.format() }
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = SearchUiState.Error(
                            callResult.message
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error("Ошибка загрузки: ${e.message}")
            }
        }
    }
}