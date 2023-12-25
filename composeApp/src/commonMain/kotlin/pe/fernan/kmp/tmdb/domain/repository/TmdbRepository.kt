package pe.fernan.kmp.tmdb.domain.repository

import pe.fernan.kmp.tmdb.domain.model.Result

interface TmdbRepository {
    suspend fun getTrendingList(timeWindows: String): List<Result>?
    suspend fun getDiscoverAll(mediaType: String): List<Result>?
    suspend fun getMovieList(movieListType: String): List<Result>?
    suspend fun getTVSeriesList(tvSeriesListType: String): List<Result>?

    suspend fun getList(keyType: String, key: String): List<Result>?

}
