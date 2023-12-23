package pe.fernan.kmp.tmdb.data.remote

import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.MovieListType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.TVSeriesListType
import pe.fernan.kmp.tmdb.domain.model.TimeWindows
import pe.fernan.kmp.tmdb.domain.repository.TmdbRepository

class TmdbRepositoryImp(private val api: TmdbClientApi) : TmdbRepository {
    override suspend fun getTrendingList(timeWindows: TimeWindows) =
        api.getTrendingAll(timeWindows).results?.filterNotNull()

    override suspend fun getDiscoverAll(mediaType: MediaType): List<Result>? =
        api.getDiscoverAll(mediaType).results?.filterNotNull()

    override suspend fun getMovieList(movieListType: MovieListType): List<Result>?  =
        api.getMovieList(movieListType).results?.filterNotNull()

    override suspend fun getTVSeriesList(tvSeriesListType: TVSeriesListType): List<Result>?  =
        api.getTVSeriesList(tvSeriesListType).results?.filterNotNull()


}