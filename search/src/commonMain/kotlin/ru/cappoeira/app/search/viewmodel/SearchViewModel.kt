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
import ru.cappoeira.app.analytics.Analytics
import ru.cappoeira.app.network.NetworkResult
import ru.cappoeira.app.network.SongInfoApi
import ru.cappoeira.app.search.formatter.SongInfoBySearchFormatter.format
import ru.cappoeira.app.search.models.SongInfoViewObject
import ru.cappoeira.app.search.state.SearchUiState
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class SearchViewModel(
    private val songInfoApi: SongInfoApi
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(emptyList()))
    private val _songs = MutableStateFlow(emptyList<SongInfoViewObject>())
    private val _isPaginationLoading = MutableStateFlow(false)
    private var page: Int = 0
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        Analytics.sendEvent(
            eventName = "openScreen",
            params = mapOf(
                "screen" to "Search"
            )
        )
        _searchText
            .debounce(250)
            .distinctUntilChanged()
            .onEach { query ->
                page = 0
                _isPaginationLoading.value = false
                _songs.value = emptyList()
                fetchUsers(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    @OptIn(ExperimentalEncodingApi::class)
    private suspend fun loadPage(query: String) {

        suspend fun getAllSongs() {
            when(val callResult = songInfoApi.getAllSongs(page)) {
                is NetworkResult.Success -> {
                    val newResult = callResult.data.songs.map { it.format() }
                    page = if (newResult.size < 10) {
                        -1
                    } else {
                        page + 1
                    }
                    _songs.value += newResult
                    _uiState.value = SearchUiState.Success(
                        _songs.value
                    )
                }
                is NetworkResult.Error -> {
                    resetStateOnError()
                    _uiState.value = SearchUiState.Error(
                        callResult.message
                    )
                }
            }
        }

        suspend fun getSongsBySearchText(searchText: String) {
            when(val callResult = songInfoApi.getSongsInfosByTextSearch(searchText, page)) {
                is NetworkResult.Success -> {
                    val newResult = callResult.data.songs.map { it.format() }
                    page = if (newResult.size < 10) {
                        -1
                    } else {
                        page + 1
                    }
                    _songs.value += newResult
                    _uiState.value = SearchUiState.Success(
                        _songs.value
                    )
                }
                is NetworkResult.Error -> {
                    resetStateOnError()
                    _uiState.value = SearchUiState.Error(
                        callResult.message
                    )
                }
            }
        }

        try {
            val searchText = Base64.encode(query.encodeToByteArray())
            if (searchText.isEmpty()) {
                getAllSongs()
            } else {
                getSongsBySearchText(searchText)
            }

        } catch (e: Exception) {
            resetStateOnError()
            _uiState.value = SearchUiState.Error("Ошибка загрузки: ${e.message}")
        }
    }

    fun onLimitReached(query: String) {
        if (page == -1 || _uiState.value is SearchUiState.Loading || _isPaginationLoading.value) {
            return
        }
        viewModelScope.launch {
            _isPaginationLoading.value = true
            loadPage(query)
            _isPaginationLoading.value = false
        }
    }

    private fun fetchUsers(query: String) {
        Analytics.sendEvent(
            eventName = "searchSong",
            params = mapOf(
                "searchText" to query,
                "screen" to "Search"
            )
        )
        viewModelScope.launch {
            _songs.value = emptyList()
            _uiState.value = SearchUiState.Loading
            page = 0
            loadPage(query)
        }
    }

    private fun resetStateOnError() {
        page = -1
        _songs.value = emptyList()
    }
}