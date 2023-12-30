package pe.fernan.kmp.tmdb.data.remote

import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.ResultMovie
import pe.fernan.kmp.tmdb.domain.model.ResultTvSeries
import pe.fernan.kmp.tmdb.domain.repository.TmdbRepository

class TmdbRepositoryImp(private val api: TmdbClientApi) : TmdbRepository {

    private fun fixMediaTypeIfNotFound(mediaType: String, list: List<Result?>?): List<Result>? {
        return list?.filterNotNull()?.map {
            if (it.mediaType == null) {
                return@map it.copy(mediaType = mediaType)
            }
            it
        }
    }

    override suspend fun getTrendingList(timeWindows: String) =
        api.getTrendingAll(timeWindows).results?.filterNotNull()

    override suspend fun getDiscoverAll(mediaType: String): List<Result>? =
        fixMediaTypeIfNotFound(mediaType, api.getDiscoverAll(mediaType).results)

    override suspend fun getMovieList(movieListType: String): List<Result>? =
        fixMediaTypeIfNotFound(MediaType.MOVIE.value, api.getMovieList(movieListType).results)

    override suspend fun getTVSeriesList(tvSeriesListType: String): List<Result>? =
        fixMediaTypeIfNotFound(MediaType.TV.value, api.getTVSeriesList(tvSeriesListType).results)

    override suspend fun getList(keyType: String, key: String): List<Result>? =
        api.getList(keyType, key).results?.filterNotNull()

    override suspend fun getTvSeries(tmdbId: Int): ResultTvSeries = api.getTvSeries(tmdbId)
    override suspend fun getMovie(tmdbId: Int): ResultMovie = api.getMovie(tmdbId)
    override suspend fun getSearchList(query: String, mediaType: String): List<Result>? {
        return fixMediaTypeIfNotFound(
            mediaType, when (mediaType) {
                MediaType.MOVIE.value -> {
                    api.getSearchMovieList(query)?.results?.filterNotNull()
                }

                MediaType.TV.value -> {
                    api.getSearchTvSeriesList(query)?.results?.filterNotNull()
                }

                else -> {
                    null
                }
            }
        )
    }

}

