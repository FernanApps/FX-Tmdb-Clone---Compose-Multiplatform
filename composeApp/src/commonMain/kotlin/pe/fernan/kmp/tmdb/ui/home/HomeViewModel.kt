package pe.fernan.kmp.tmdb.ui.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.MovieListType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.TVSeriesListType
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


class HomeViewModel(
    private val repository: TmdbRepository
) : ViewModel() {
    private val _homeState =
        MutableStateFlow<HomeState>(HomeState())
    val homeState = _homeState.asStateFlow()

    fun getTrendingList(key: TimeWindows = TimeWindows.DAY) {
        viewModelScope.launch {
            _homeState.update { it.copy(trendingList = null) }
            try {
                val list = repository.getTrendingList(key)!!
                _homeState.update { it.copy(trendingList = list) }
            } catch (e: Exception) {
                val error = e.message.toString()
            }

        }
    }

    fun getDiscover(key: MediaType) {
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

    fun getMovieList(key: MovieListType) {
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

    fun getTVSeriesList(key: TVSeriesListType) {
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

}