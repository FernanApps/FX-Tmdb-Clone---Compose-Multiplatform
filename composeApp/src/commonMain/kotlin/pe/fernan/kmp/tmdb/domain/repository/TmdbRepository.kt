package pe.fernan.kmp.tmdb.domain.repository

import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.MovieListType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.TVSeriesListType
import pe.fernan.kmp.tmdb.domain.model.TimeWindows

interface TmdbRepository {
    suspend fun getTrendingList(timeWindows: TimeWindows): List<Result>?
    suspend fun getDiscoverAll(mediaType: MediaType): List<Result>?
    suspend fun getMovieList(movieListType: MovieListType): List<Result>?
    suspend fun getTVSeriesList(tvSeriesListType: TVSeriesListType): List<Result>?

}
