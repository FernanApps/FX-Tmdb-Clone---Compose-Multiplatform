package pe.fernan.kmp.tmdb.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
    class Dates(
        @SerialName("maximum")
        val maximum: String?,
        @SerialName("minimum")
        val minimum: String?
    )