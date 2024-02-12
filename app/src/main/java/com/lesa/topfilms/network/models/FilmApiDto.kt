package com.lesa.topfilms.network.models

import com.lesa.topfilms.models.Film
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmApiDto(
    @SerialName("filmId")
    val id: Int,
    @SerialName("nameRu")
    val name: String,
    @SerialName("year")
    val year: String,
    @SerialName("genres")
    val genres: List<GenreApiDto>,
    @SerialName("posterUrlPreview")
    val posterUrlPreview: String,
    @SerialName("posterUrl")
    val posterUrl: String
)

fun FilmApiDto.toDomain(
    favFilmsIds: Set<Int>
): Film {
    return Film(
        id = id,
        name = name,
        year = year,
        genres = genres.map {
            it.genre
        },
        posterUrlPreview = posterUrlPreview,
        posterUrl = posterUrl,
        isFav = favFilmsIds.contains(id),
        countries = listOf(),
        description = null
    )
}