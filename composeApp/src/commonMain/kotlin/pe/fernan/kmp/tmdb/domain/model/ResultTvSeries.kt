package pe.fernan.kmp.tmdb.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResultTvSeries(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("created_by")
    val createdBy: List<CreatedBy?>?,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int?>?,
    @SerialName("first_air_date")
    val firstAirDate: String?,
    @SerialName("genres")
    val genres: List<Genre?>?,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("in_production")
    val inProduction: Boolean?,
    @SerialName("languages")
    val languages: List<String?>?,
    @SerialName("last_air_date")
    val lastAirDate: String?,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir?,
    @SerialName("name")
    val name: String?,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir?,
    @SerialName("networks")
    val networks: List<Network?>?,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerialName("origin_country")
    val originCountry: List<String?>?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany?>?,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry?>?,
    @SerialName("seasons")
    val seasons: List<Season?>?,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerialName("status")
    val status: String?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
    @SerialName("videos")
    val videos: Videos?,
    @SerialName("credits")
    val credits: Credits?,
    @SerialName("keywords")
    val keywords: Keywords?,
    @SerialName("images")
    val images: Images?,
    @SerialName("recommendations")
    val recommendations: Recommendations?,
    @SerialName("external_ids")
    val externalIds: ExternalIds?,
    @SerialName("similar")
    val similar: Similar?
) {
    @Serializable
    class CreatedBy(
        @SerialName("id")
        val id: Int?,
        @SerialName("credit_id")
        val creditId: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("gender")
        val gender: Int?,
        @SerialName("profile_path")
        val profilePath: String?
    )

    @Serializable
    class Genre(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?
    )

    @Serializable
    class LastEpisodeToAir(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("overview")
        val overview: String?,
        @SerialName("vote_average")
        val voteAverage: Double?,
        @SerialName("vote_count")
        val voteCount: Int?,
        @SerialName("air_date")
        val airDate: String?,
        @SerialName("episode_number")
        val episodeNumber: Int?,
        @SerialName("episode_type")
        val episodeType: String?,
        @SerialName("production_code")
        val productionCode: String?,
        @SerialName("runtime")
        val runtime: Int?,
        @SerialName("season_number")
        val seasonNumber: Int?,
        @SerialName("show_id")
        val showId: Int?,
        @SerialName("still_path")
        val stillPath: String?
    )

    @Serializable
    class NextEpisodeToAir(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("overview")
        val overview: String?,
        @SerialName("vote_average")
        val voteAverage: Double?,
        @SerialName("vote_count")
        val voteCount: Int?,
        @SerialName("air_date")
        val airDate: String?,
        @SerialName("episode_number")
        val episodeNumber: Int?,
        @SerialName("episode_type")
        val episodeType: String?,
        @SerialName("production_code")
        val productionCode: String?,
        @SerialName("runtime")
        val runtime: Int?,
        @SerialName("season_number")
        val seasonNumber: Int?,
        @SerialName("show_id")
        val showId: Int?,
        @SerialName("still_path")
        val stillPath: String?
    )

    @Serializable
    class Network(
        @SerialName("id")
        val id: Int?,
        @SerialName("logo_path")
        val logoPath: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("origin_country")
        val originCountry: String?
    )

    @Serializable
    class ProductionCompany(
        @SerialName("id")
        val id: Int?,
        @SerialName("logo_path")
        val logoPath: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("origin_country")
        val originCountry: String?
    )

    @Serializable
    class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String?,
        @SerialName("name")
        val name: String?
    )

    @Serializable
    class Season(
        @SerialName("air_date")
        val airDate: String?,
        @SerialName("episode_count")
        val episodeCount: Int?,
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("overview")
        val overview: String?,
        @SerialName("poster_path")
        val posterPath: String?,
        @SerialName("season_number")
        val seasonNumber: Int?,
        @SerialName("vote_average")
        val voteAverage: Double?
    )

    @Serializable
    class SpokenLanguage(
        @SerialName("english_name")
        val englishName: String?,
        @SerialName("iso_639_1")
        val iso6391: String?,
        @SerialName("name")
        val name: String?
    )

    @Serializable
    class Videos(
        @SerialName("results")
        val results: List<Result?>?
    ) {
        @Serializable
        class Result(
            @SerialName("iso_639_1")
            val iso6391: String?,
            @SerialName("iso_3166_1")
            val iso31661: String?,
            @SerialName("name")
            val name: String?,
            @SerialName("key")
            val key: String?,
            @SerialName("site")
            val site: String?,
            @SerialName("size")
            val size: Int?,
            @SerialName("type")
            val type: String?,
            @SerialName("official")
            val official: Boolean?,
            @SerialName("published_at")
            val publishedAt: String?,
            @SerialName("id")
            val id: String?
        )
    }

    @Serializable
    class Credits(
        @SerialName("cast")
        val cast: List<Cast?>?,
        @SerialName("crew")
        val crew: List<Crew?>?
    ) {
        @Serializable
        class Cast(
            @SerialName("adult")
            val adult: Boolean?,
            @SerialName("gender")
            val gender: Int?,
            @SerialName("id")
            val id: Int?,
            @SerialName("known_for_department")
            val knownForDepartment: String?,
            @SerialName("name")
            val name: String?,
            @SerialName("original_name")
            val originalName: String?,
            @SerialName("popularity")
            val popularity: Double?,
            @SerialName("profile_path")
            val profilePath: String?,
            @SerialName("character")
            val character: String?,
            @SerialName("credit_id")
            val creditId: String?,
            @SerialName("order")
            val order: Int?
        )

        @Serializable
        class Crew(
            @SerialName("adult")
            val adult: Boolean?,
            @SerialName("gender")
            val gender: Int?,
            @SerialName("id")
            val id: Int?,
            @SerialName("known_for_department")
            val knownForDepartment: String?,
            @SerialName("name")
            val name: String?,
            @SerialName("original_name")
            val originalName: String?,
            @SerialName("popularity")
            val popularity: Double?,
            @SerialName("profile_path")
            val profilePath: String?,
            @SerialName("credit_id")
            val creditId: String?,
            @SerialName("department")
            val department: String?,
            @SerialName("job")
            val job: String?
        )
    }

    @Serializable
    class Keywords(
        @SerialName("results")
        val results: List<Result?>?
    ) {
        @Serializable
        class Result(
            @SerialName("name")
            val name: String?,
            @SerialName("id")
            val id: Int?
        )
    }

    @Serializable
    class Images(
        @SerialName("backdrops")
        val backdrops: List<Backdrop?>?,
        @SerialName("logos")
        val logos: List<Logo?>?,
        @SerialName("posters")
        val posters: List<Poster?>?
    ) {
        @Serializable
        class Backdrop(
            @SerialName("aspect_ratio")
            val aspectRatio: Double?,
            @SerialName("height")
            val height: Int?,
            @SerialName("iso_639_1")
            val iso6391: String?,
            @SerialName("file_path")
            val filePath: String?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?,
            @SerialName("width")
            val width: Int?
        )

        @Serializable
        class Logo(
            @SerialName("aspect_ratio")
            val aspectRatio: Double?,
            @SerialName("height")
            val height: Int?,
            @SerialName("iso_639_1")
            val iso6391: String?,
            @SerialName("file_path")
            val filePath: String?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?,
            @SerialName("width")
            val width: Int?
        )

        @Serializable
        class Poster(
            @SerialName("aspect_ratio")
            val aspectRatio: Double?,
            @SerialName("height")
            val height: Int?,
            @SerialName("iso_639_1")
            val iso6391: String?,
            @SerialName("file_path")
            val filePath: String?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?,
            @SerialName("width")
            val width: Int?
        )
    }

    @Serializable
    class Recommendations(
        @SerialName("page")
        val page: Int?,
        @SerialName("results")
        val results: List<Result?>?,
        @SerialName("total_pages")
        val totalPages: Int?,
        @SerialName("total_results")
        val totalResults: Int?
    ) {
        @Serializable
        class Result(
            @SerialName("adult")
            val adult: Boolean?,
            @SerialName("backdrop_path")
            val backdropPath: String?,
            @SerialName("id")
            val id: Int?,
            @SerialName("name")
            val name: String?,
            @SerialName("original_language")
            val originalLanguage: String?,
            @SerialName("original_name")
            val originalName: String?,
            @SerialName("overview")
            val overview: String?,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("media_type")
            val mediaType: String?,
            @SerialName("genre_ids")
            val genreIds: List<Int?>?,
            @SerialName("popularity")
            val popularity: Double?,
            @SerialName("first_air_date")
            val firstAirDate: String?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?,
            @SerialName("origin_country")
            val originCountry: List<String?>?
        )
    }

    @Serializable
    class ExternalIds(
        @SerialName("imdb_id")
        val imdbId: String?,
        @SerialName("freebase_mid")
        val freebaseMid: String?,
        @SerialName("freebase_id")
        val freebaseId: String?,
        @SerialName("tvdb_id")
        val tvdbId: Int?,
        @SerialName("tvrage_id")
        val tvrageId: Int?,
        @SerialName("wikidata_id")
        val wikidataId: String?,
        @SerialName("facebook_id")
        val facebookId: String?,
        @SerialName("instagram_id")
        val instagramId: String?,
        @SerialName("twitter_id")
        val twitterId: String?
    )

    @Serializable
    class Similar(
        @SerialName("page")
        val page: Int?,
        @SerialName("results")
        val results: List<Result?>?,
        @SerialName("total_pages")
        val totalPages: Int?,
        @SerialName("total_results")
        val totalResults: Int?
    ) {
        @Serializable
        class Result(
            @SerialName("adult")
            val adult: Boolean?,
            @SerialName("backdrop_path")
            val backdropPath: String?,
            @SerialName("genre_ids")
            val genreIds: List<Int?>?,
            @SerialName("id")
            val id: Int?,
            @SerialName("origin_country")
            val originCountry: List<String?>?,
            @SerialName("original_language")
            val originalLanguage: String?,
            @SerialName("original_name")
            val originalName: String?,
            @SerialName("overview")
            val overview: String?,
            @SerialName("popularity")
            val popularity: Double?,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("first_air_date")
            val firstAirDate: String?,
            @SerialName("name")
            val name: String?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?
        )
    }
}