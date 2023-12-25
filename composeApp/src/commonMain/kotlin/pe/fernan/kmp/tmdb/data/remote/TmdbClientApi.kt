package pe.fernan.kmp.tmdb.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import pe.fernan.kmp.tmdb.data.model.ResponseList
import pe.fernan.kmp.tmdb.data.model.ResponseMovieList
import pe.fernan.kmp.tmdb.data.model.ResponseTVSeriesList
import pe.fernan.kmp.tmdb.data.model.ResponseTrending
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.utils.Constant.BASE_URL
import pe.fernan.kmp.tmdb.utils.Constant.DEFAULT_PATH
import pe.fernan.kmp.tmdb.utils.Constant.TIMEOUT
import pe.fernan.kmp.tmdb.utils.toJson

object TmdbClientApi {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                explicitNulls = false
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.EMPTY
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
        }
    }


    suspend fun getTrendingAll(timeWindows: String): ResponseTrending {
        val url = BASE_URL + "/trending/all/" + timeWindows + "?" +DEFAULT_PATH
        return client.get(url).body()
    }

    val sortByOptions = listOf(
        "popularity.asc",
        "popularity.desc",
        "revenue.asc",
        "revenue.desc",
        "primary_release_date.asc",
        "primary_release_date.desc",
        "vote_average.asc",
        "vote_average.desc",
        "vote_count.asc",
        "vote_count.desc"
    )
    /*

    GET /3/discover/movie?include_adult=false&page=1&sort_by=popularity.asc
    GET /3/discover/tv?include_adult=true&page=1&sort_by=popularity.desc


     */

    suspend fun getDiscoverAll(mediaType: String): ResponseTrending {
        val url =
            BASE_URL + "/discover/" + mediaType +"?page=1" + /*sortByOptions.first() + */"&" + DEFAULT_PATH
        return client.get(url).body()
    }

    suspend fun getMovieList(movieListType: String): ResponseMovieList {
        val url = BASE_URL + "/movie/" + movieListType + "?" + DEFAULT_PATH
        return client.get(url).body()
    }

    suspend fun getTVSeriesList(tvSeriesListType: String): ResponseTVSeriesList {
        val url = BASE_URL + "/tv/" + tvSeriesListType + "?" + DEFAULT_PATH
        return client.get(url).body()
    }

    suspend fun getList(keyType: String, key: String): ResponseList {
        val url = BASE_URL + "/$keyType/" + key + "?" + DEFAULT_PATH
        return client.get(url).body()
    }


}


suspend fun main() = coroutineScope {
    launch {
        //println(TmdbClientApi.getTrendingAll(TimeWindows.DAY).toJson())
        println(TmdbClientApi.getDiscoverAll(MediaType.MOVIE.value).toJson())
    }
    println("Hello")
}