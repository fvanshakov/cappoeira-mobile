package ru.cappoeira.app.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
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
import ru.cappoeira.app.search.SongType
import ru.cappoeira.app.search.events.SearchEvent
import ru.cappoeira.app.search.formatter.SongInfoBySearchFormatter.format
import ru.cappoeira.app.search.models.SongInfoViewObject
import ru.cappoeira.app.search.state.SearchUiState
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class SearchViewModel(
    private val songInfoApi: SongInfoApi
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private var _songType = MutableStateFlow(SongType.CORRIDO)
    private val _uiState = MutableStateFlow(SearchUiState.DEFAULT)
    private var _page: Int = 0
    private var _noMorePages: Boolean = false
    private var _job: Job? = null
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    val songType: StateFlow<SongType> = _songType.asStateFlow()
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    init {
        Analytics.sendEvent(
            eventName = "openScreen",
            params = mapOf(
                "screen" to "Search"
            )
        )
        _searchText
            .debounce(400)
            .distinctUntilChanged()
            .onEach { query ->
                _searchText.value = query
                loadSongs()
            }
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        if (_searchText.value.isEmpty() || _searchText.value.isBlank()) {
            loadAllSongs()
        } else {
            loadByText()
        }
    }

    fun handleEvent(
        event: SearchEvent
    ) {
        when(event) {
            is SearchEvent.ChangeSongType -> {
                switchType(event.songType)
            }
            is SearchEvent.ChangeSearchText -> {
                changeText(event.text)
            }
            is SearchEvent.Paginate -> {
                if (_noMorePages == false) {
                    paginate()
                }
            }
        }
    }

    private fun changeText(query: String) {
        if (query != _searchText.value) {
            clearPreviousJob()
            _page = 0
            _noMorePages = false
            when (_songType.value) {
                SongType.CORRIDO -> { _uiState.value = SearchUiState.DEFAULT.copy(ladainhaState = _uiState.value.ladainhaState) }
                SongType.LADAINHA -> { _uiState.value = SearchUiState.DEFAULT.copy(corridoState = _uiState.value.corridoState) }
            }
            _searchText.value = query
        }
    }

    private fun switchType(songType: SongType) {
        if (songType != _songType.value) {
            clearPreviousJob()
            _page = 0
            _noMorePages = false
            _songType.value = songType
            when (_songType.value) {
                SongType.CORRIDO -> { _uiState.value = SearchUiState.DEFAULT.copy(ladainhaState = _uiState.value.ladainhaState) }
                SongType.LADAINHA -> { _uiState.value = SearchUiState.DEFAULT.copy(corridoState = _uiState.value.corridoState) }
            }
            loadSongs()
        }
    }

    private fun paginate() {
        loadSongs()
    }

    private fun processError(errorMessage: String) {
        _page = -1
        _uiState.value = _uiState.value.copy(
            error = SearchUiState.SearchUiErrorState(errorMessage)
        )
    }

    private fun processSuccess(songs: List<SongInfoViewObject>) {
        appendSongsToSongTypeList(songs)
        if (songs.isEmpty()) {
            _noMorePages = true
        } else {
            _page++
        }
    }

    private fun clearPreviousJob() {
        _job?.cancel()
        _job = null
    }

    private fun loadAllSongs() {
        if (_job == null) {
            _job = viewModelScope.launch(context = Dispatchers.IO) {
                val result = songInfoApi.getAllSongs(
                    songType = _songType.value.serverValue,
                    page = _page
                )
                when(result) {
                    is NetworkResult.Success -> {
                        processSuccess(result.data.songs.map { it.format() })
                    }
                    is NetworkResult.Error -> {
                        processError(result.message)
                    }
                }
                _uiState.value = _uiState.value.copy(isSkeletonShown = false)
                _job = null
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun loadByText() {
        if (_job == null) {
            _job = viewModelScope.launch(context = Dispatchers.IO)  {
                val result = songInfoApi.getSongsInfosByTextSearch(
                    textSearch = Base64.encode(_searchText.value.encodeToByteArray()),
                    songType = _songType.value.serverValue,
                    page = _page
                )
                when(result) {
                    is NetworkResult.Success -> {
                        processSuccess(result.data.songs.map { it.format() })
                    }
                    is NetworkResult.Error -> {
                        processError(result.message)
                    }
                }
                _uiState.value = _uiState.value.copy(isSkeletonShown = false)
                _job = null
            }
        }
    }

    private fun appendSongsToSongTypeList(
        songs: List<SongInfoViewObject>
    ) {
        var corridoState = _uiState.value.corridoState
        var ladainhaState = _uiState.value.ladainhaState
        when(_songType.value)  {
            SongType.CORRIDO -> corridoState = corridoState.copy(songs = corridoState.songs + songs)
            SongType.LADAINHA -> ladainhaState = ladainhaState.copy(songs = ladainhaState.songs + songs)
        }
        _uiState.value = _uiState.value.copy(
            corridoState = corridoState,
            ladainhaState = ladainhaState
        )
    }
}