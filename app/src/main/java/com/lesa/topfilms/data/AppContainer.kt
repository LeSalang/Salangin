package com.lesa.topfilms.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lesa.topfilms.network.FilmApiService
import com.lesa.topfilms.storage.FilmRoomDatabase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val filmRepository: FilmRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://kinopoiskapiunofficial.tech"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val filmApiService: FilmApiService by lazy {
        retrofit.create(FilmApiService::class.java)
    }

    private val filmRoomDataBase: FilmRoomDatabase by lazy {
        FilmRoomDatabase.getDatabase(context = context)
    }

    override val filmRepository: FilmRepository by lazy {
        FilmRepositoryImpl(filmApiService, filmRoomDataBase.filmDao())
    }
}