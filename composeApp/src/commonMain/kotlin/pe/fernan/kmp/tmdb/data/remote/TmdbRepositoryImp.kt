package pe.fernan.kmp.tmdb.data.remote

import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.repository.TmdbRepository

class TmdbRepositoryImp(private val api: TmdbClientApi) : TmdbRepository {
    override suspend fun getTrendingList(timeWindows: String) =
        api.getTrendingAll(timeWindows).results?.filterNotNull()

    override suspend fun getDiscoverAll(mediaType: String): List<Result>? =
        api.getDiscoverAll(mediaType).results?.filterNotNull()

    override suspend fun getMovieList(movieListType: String): List<Result>? =
        api.getMovieList(movieListType).results?.filterNotNull()

    override suspend fun getTVSeriesList(tvSeriesListType: String): List<Result>? =
        api.getTVSeriesList(tvSeriesListType).results?.filterNotNull()

    override suspend fun getList(keyType: String, key: String): List<Result>? =
        api.getList(keyType, key).results?.filterNotNull()


}