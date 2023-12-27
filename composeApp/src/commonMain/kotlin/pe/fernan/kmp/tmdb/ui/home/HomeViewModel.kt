package pe.fernan.kmp.tmdb.ui.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.ResultMovie
import pe.fernan.kmp.tmdb.domain.model.ResultTvSeries
import pe.fernan.kmp.tmdb.domain.model.TimeWindows
import pe.fernan.kmp.tmdb.domain.repository.TmdbRepository


data class HomeState(
    var mainBackgroundPath: String? = null,
    val trendingList: List<Result>? = null,
    val discoverBackgroundPath: String? = null,
    val discoverList: List<Result>? = null,
    val movieList: List<Result>? = null,
    val tvSeriesList: List<Result>? = null,
)

data class ItemsState(
    val itemsList: List<Result>? = null,
)

data class SearchState(
    val itemsList: List<Result>? = null,
)

data class DetailsState(
    val mediaType: MediaType? = null,
    val movie: ResultMovie? = null,
    val tvSeries: ResultTvSeries? = null
)


class HomeViewModel(
    private val repository: TmdbRepository
) : ViewModel() {

    private val _homeState =
        MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private val _itemsState =
        MutableStateFlow(ItemsState())
    val itemsState = _itemsState.asStateFlow()

    private val _detailsState =
        MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    private val _searchState =
        MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    fun setMediaType(mediaType: String) {
        _detailsState.update { it.copy(mediaType = MediaType.entries.find { _mediaType -> _mediaType.value == mediaType }) }
    }


    fun getSearchList(query: String, mediaType: MediaType) {
        viewModelScope.launch {
            _searchState.update { it.copy(itemsList = null) }
            try {
                val list = repository.getSearchList(query, mediaType.value)
                _searchState.update { it.copy(itemsList = list) }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }



    fun getList(keyType: String, key: String) {
        viewModelScope.launch {
            _itemsState.update { it.copy(itemsList = null) }
            try {
                val list = repository.getList(keyType, key)
                _itemsState.update { it.copy(itemsList = list) }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }




    fun getTrendingList(key: String = TimeWindows.DAY.value) {
        viewModelScope.launch {
            _homeState.update { it.copy(trendingList = null) }
            try {
                val list = repository.getTrendingList(key)
                _homeState.update { it.copy(trendingList = list) }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }

    fun getDiscover(key: String) {
        viewModelScope.launch {
            _homeState.update { it.copy(discoverList = null) }
            try {
                // Fix Without backdrop is Null
                val list = repository.getDiscoverAll(key)!!.filter { it.backdropPath != null }
                _homeState.update {
                    it.copy(
                        discoverBackgroundPath = list.firstOrNull()?.posterPath,
                        discoverList = list,
                        mainBackgroundPath = list.random().posterPath
                    )
                }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }


    fun setBackgroundDiscover(path: String?) {
        if (path != _homeState.value.discoverBackgroundPath) {
            _homeState.update { it.copy(discoverBackgroundPath = path) }
        }
    }

    fun getMovieList(key: String) {
        viewModelScope.launch {
            _homeState.update { it.copy(movieList = null) }
            try {
                // Fix Without backdrop is Null
                val list = repository.getMovieList(key)!!.filter { it.backdropPath != null }
                _homeState.update { it.copy(movieList = list) }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }

    fun getTVSeriesList(key: String) {
        viewModelScope.launch {
            _homeState.update { it.copy(tvSeriesList = null) }
            try {
                // Fix Without backdrop is Null
                val list = repository.getTVSeriesList(key)!!.filter { it.backdropPath != null }
                _homeState.update { it.copy(tvSeriesList = list) }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }

    fun getResult(mediaType: String, id: Int) {
        setMediaType(mediaType)
        viewModelScope.launch {

            try {
                if(_detailsState.value.mediaType == MediaType.TV){
                    _detailsState.update { it.copy( tvSeries = null) }
                } else {
                    _detailsState.update { it.copy( movie = null) }
                }
                if(_detailsState.value.mediaType == MediaType.TV){
                    _detailsState.update { it.copy( tvSeries = repository.getTvSeries(id)) }
                } else {
                    _detailsState.update { it.copy( movie = repository.getMovie(id)) }
                }

            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }

}