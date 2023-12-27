package pe.fernan.kmp.tmdb.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Result(
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
    val voteCount: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("first_air_date")
    val firstAirDate: String?,
    @SerialName("origin_country")
    val originCountry: List<String?>?,



){
    constructor(id: Int): this(false,"",id,"","","","","","", listOf<Int>(),0.0,"",false,0.0,0,"","","",
        listOf<String>())

    // 2023-12-15
    val date get() = if(mediaType == "movie") releaseDate else firstAirDate

}

