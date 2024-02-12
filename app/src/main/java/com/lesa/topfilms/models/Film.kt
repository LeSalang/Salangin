package com.lesa.topfilms.models

import com.lesa.topfilms.storage.FilmEntity

data class Film(
    val id: Int,
    val name: String,
    val year: String,
    val genres: List<String>,
    val countries: List<String>,
    val description: String?,
    val posterUrlPreview: String,
    val posterUrl: String,
    val isFav: Boolean
)

fun Film.toEntity(): FilmEntity {
    return FilmEntity(
        id = id,
        name = name,
        year = year,
        genres = genres,
        posterUrlPreview = posterUrlPreview,
        posterUrl = posterUrl,
        isFav = isFav,
        countries = countries,
        description = description
    )
}
