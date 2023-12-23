package pe.fernan.kmp.tmdb.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pe.fernan.kmp.tmdb.domain.model.Dates
import pe.fernan.kmp.tmdb.domain.model.Result

@Serializable
class ResponseMovieList(
    @SerialName("dates")
    val dates: Dates?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<Result?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)