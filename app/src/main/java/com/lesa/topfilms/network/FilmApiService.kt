package com.lesa.topfilms.network

import com.lesa.topfilms.network.models.FilmDetailsApiDto
import com.lesa.topfilms.network.models.FilmsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApiService {
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(
        @Header("x-api-key") apiKey: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b",
        @Query("page") page: Int
    ): FilmsResponse

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilm(
        @Header("x-api-key") apiKey: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b",
        @Path("id") id: Int
    ): FilmDetailsApiDto
}