package pe.fernan.kmp.tmdb.utils

import pe.fernan.kmp.tmdb.ui.navigation.format

object Constant {
    /**
    w600_and_h900_bestv2
    w185_and_h278_bestv2
    https://image.tmdb.org/t/p/w185_and_h278_bestv2/q2YR6o6MKqO2szOhmmSSAnjs2aC.jpg
     */
    const val TMDB_PATCH_IMAGES = "https://image.tmdb.org/t/p/w400"
    const val TMDB_IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w185_and_h278_bestv2"
    const val TMDB_IMAGE_BASE_PATH_CARD = "https://image.tmdb.org/t/p/w220_and_h330_face"
    const val TMDB_IMAGE_BASE_PATH_CARD_HORIZONTAL =
        "https://image.tmdb.org//t/p/w355_and_h200_multi_faces"

    //const val IMAGE_BASE_PATH_CARD_HORIZONTAL_LARGE = "https://www.themoviedb.org/t/p/w1920_and_h427_multi_faces"
    //             "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/xpba0Dxz3sxV3QgYLR8UIe1LAAX.jpg"
    const val TMDB_IMAGE_BASE_PATH_CARD_HORIZONTAL_LARGE = "https://www.themoviedb.org/t/p/w780"
    const val TMDB_IMAGE_BASE_PATH_CARD_HORIZONTAL_SUPER_LARGE =
        "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces"


    //
    const val TIMEOUT = 300000L


    private const val API_KEY = "e8f3986a51e210346f0f8ddc19287ed4"


    /** https://api.themoviedb.org/3/ */
    private const val END_POINT = "https://api.themoviedb.org/3/"

    // en-US
    /** ?api_key= API_KEY &language=es */
    private const val DEFAULT_PATH = "?api_key=$API_KEY&language=es"

    private const val PATH_TMDB_SEARCH_MOVIE_FORMAT = "search/movie" + DEFAULT_PATH + "&query=%s"
    private const val PATH_TMDB_SEARCH_SERIE_FORMAT = "search/tv" + DEFAULT_PATH + "&query=%s"
    private const val PATH_TMDB_ACTOR_FORMAT = "person/%s" + DEFAULT_PATH
    private const val PATH_TMDB_SEASON_FORMAT = "tv/%d/season/%d" + DEFAULT_PATH
    private const val PATH_TMDB_TRENDING_ALL_FORMAT = "/trending/all/%s" + DEFAULT_PATH
    private const val PATH_TMDB_DISCOVER_ALL_FORMAT = "/discover/%s" + DEFAULT_PATH + "&page=1" /*sortByOptions.first() + */
    private const val PATH_TMDB_MOVIE_LIST_FORMAT = "/movie/%s" + DEFAULT_PATH
    private const val PATH_TMDB_TV_SERIES_LIST_FORMAT = "/tv/%s" + DEFAULT_PATH
    private const val PATH_TMDB_LIST_FORMAT = "/%s/%s" + DEFAULT_PATH
    private const val PATH_TMDB_FIND_IMDB = "find/%s" + DEFAULT_PATH
    private const val PATH_TMDB_SERIE_OR_MOVIE_FORMAT = "%s/%s" + DEFAULT_PATH + "&append_to_response=videos,%s,keywords,images,recommendations,external_ids,latest,similar"
    private const val PATH_TMDB_COLLECTION_FORMAT = "collection/%d" + DEFAULT_PATH

    /**
    TVSeries Status :V
    ['Returning Series', 'Planned', 'In Production', 'Ended', 'Canceled', 'Pilot']
     */

    fun getTmdbMovieSearchUrl(query: String)=  END_POINT + PATH_TMDB_SEARCH_MOVIE_FORMAT.format(query)
    fun getTmdbTvSearchUrl(query: String)= END_POINT + PATH_TMDB_SEARCH_SERIE_FORMAT.format(query)
    fun getTmdbMovieUrl(id: Int)= END_POINT + PATH_TMDB_SERIE_OR_MOVIE_FORMAT.format("movie", id, "casts")
    fun getTmdbTvSeriesUrl(id: Int) = END_POINT + PATH_TMDB_SERIE_OR_MOVIE_FORMAT.format("tv", id, "credits")
    fun getTmdbActorUrl(id: Int) = END_POINT + PATH_TMDB_ACTOR_FORMAT.format(id)
    fun getTmdbSeasonUrl(id: Int, seasonNumber: Int) = END_POINT + PATH_TMDB_SEASON_FORMAT.format(id, seasonNumber)
    fun getTmdbCollectionUrl(id: Int) = END_POINT + PATH_TMDB_COLLECTION_FORMAT.format(id)
    fun getFindImdbUrl(tmdbId: String) = END_POINT + PATH_TMDB_FIND_IMDB.format(tmdbId)
    fun getTrendingAllUrl(timeWindows: String) = END_POINT + PATH_TMDB_TRENDING_ALL_FORMAT.format(timeWindows)
    fun getDiscoverAllUrl(mediaType: String) = END_POINT + PATH_TMDB_DISCOVER_ALL_FORMAT.format(mediaType)
    fun getMovieListUrl(movieListType: String) = END_POINT + PATH_TMDB_MOVIE_LIST_FORMAT.format(movieListType)
    fun getTvSeriesUrl(tvSeriesListType: String) = END_POINT + PATH_TMDB_TV_SERIES_LIST_FORMAT.format(tvSeriesListType)
    fun getListUrl(keyType: String, key: String) = END_POINT + PATH_TMDB_LIST_FORMAT.format(keyType, key)
    fun getYoutubeUrl(videoKey: String) = "https://www.youtube.com/watch?v=$videoKey"
    fun getYoutubeEmbedUrl(videoKey: String) = "https://www.youtube.com/embed/$videoKey"


}