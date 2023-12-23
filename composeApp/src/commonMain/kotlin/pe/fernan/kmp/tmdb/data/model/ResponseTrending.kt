package pe.fernan.kmp.tmdb.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResponseTrending(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<pe.fernan.kmp.tmdb.domain.model.Result?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)