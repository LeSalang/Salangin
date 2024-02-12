package com.lesa.topfilms.network.models

import com.lesa.topfilms.models.Film
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetailsApiDto(
    @SerialName("kinopoiskId")
    val id: Int,
    @SerialName("nameRu")
    val name: String,
    @SerialName("year")
    val year: Int,
    @SerialName("genres")
    val genres: List<GenreApiDto>,
    @SerialName("countries")
    val countries: List<CountryApiDto>?,
    @SerialName("description")
    val description: String?,
    @SerialName("posterUrl")
    val posterUrl: String,
    @SerialName("posterUrlPreview")
    val posterUrlPreview: String
)

fun FilmDetailsApiDto.toDomain(
    favFilmsIds: Set<Int>
): Film {
    return Film(
        id = id,
        name = name,
        year = year.toString(),
        genres = genres.map { it.genre },
        countries = countries?.map { it.country } ?: emptyList(),
        description = description,
        posterUrlPreview = posterUrlPreview,
        posterUrl = posterUrl,
        isFav = favFilmsIds.contains(id)
    )
}
