package com.lesa.topfilms.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponse(
    @SerialName("films")
    val films: List<FilmApiDto>
)
