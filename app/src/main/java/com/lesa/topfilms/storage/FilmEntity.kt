package com.lesa.topfilms.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lesa.topfilms.models.Film

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("name_ru")
    val name: String,
    @ColumnInfo("year")
    val year: String,
    @ColumnInfo("genres")
    val genres: List<String>,
    @ColumnInfo("countries")
    val countries: List<String>,
    @ColumnInfo("description")
    val description: String?,
    @ColumnInfo("poster_url_preview")
    val posterUrlPreview: String,
    @ColumnInfo("poster_url")
    val posterUrl: String,
    @ColumnInfo("is_fav")
    val isFav: Boolean
)

fun FilmEntity.toDomain(): Film {
    return Film(
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
