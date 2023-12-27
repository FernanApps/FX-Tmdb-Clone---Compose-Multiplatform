package pe.fernan.kmp.tmdb.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResultMovie(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @SerialName("budget")
    val budget: Int?,
    @SerialName("genres")
    val genres: List<Genre?>?,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_title")
    val originalTitle: String?,
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
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("revenue")
    val revenue: Int?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerialName("status")
    val status: String?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("video")
    val video: Boolean?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
    @SerialName("videos")
    val videos: Videos?,
    @SerialName("casts")
    val casts: Casts?,
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
    class BelongsToCollection(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("poster_path")
        val posterPath: String?,
        @SerialName("backdrop_path")
        val backdropPath: String?
    )

    @Serializable
    class Genre(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?
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
    class Casts(
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
            @SerialName("cast_id")
            val castId: Int?,
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
        @SerialName("keywords")
        val keywords: List<Keyword?>?
    ) {
        @Serializable
        class Keyword(
            @SerialName("id")
            val id: Int?,
            @SerialName("name")
            val name: String?
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
            val voteAverage: Int?,
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
            @SerialName("title")
            val title: String?,
            @SerialName("original_language")
            val originalLanguage: String?,
            @SerialName("original_title")
            val originalTitle: String?,
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
            @SerialName("release_date")
            val releaseDate: String?,
            @SerialName("video")
            val video: Boolean?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?
        )
    }

    @Serializable
    class ExternalIds(
        @SerialName("imdb_id")
        val imdbId: String?,
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
            @SerialName("original_language")
            val originalLanguage: String?,
            @SerialName("original_title")
            val originalTitle: String?,
            @SerialName("overview")
            val overview: String?,
            @SerialName("popularity")
            val popularity: Double?,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("release_date")
            val releaseDate: String?,
            @SerialName("title")
            val title: String?,
            @SerialName("video")
            val video: Boolean?,
            @SerialName("vote_average")
            val voteAverage: Double?,
            @SerialName("vote_count")
            val voteCount: Int?
        )
    }
}