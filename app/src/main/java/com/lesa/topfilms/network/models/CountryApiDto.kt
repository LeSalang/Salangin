package com.lesa.topfilms.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryApiDto(
    @SerialName("country")
    val country: String
)
