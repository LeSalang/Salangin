package com.lesa.topfilms.data

import com.lesa.topfilms.models.Film
import com.lesa.topfilms.models.toEntity
import com.lesa.topfilms.network.FilmApiService
import com.lesa.topfilms.network.models.toDomain
import com.lesa.topfilms.storage.FilmDao
import com.lesa.topfilms.storage.toDomain
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface FilmRepository {
    val topFilms: Flow<List<Film>>
    val favFilms: Flow<List<Film>>
    fun search(name: String): Flow<List<Film>>
    fun searchFromFav(name: String): Flow<List<Film>>
    fun getFilmById(id: Int): Flow<Film?>
    suspend fun getTopFilms()
    suspend fun loadFilm(id: Int)
    suspend fun updateFilm(film: Film)
}

class FilmRepositoryImpl(
    private val filmApiService: FilmApiService,
    private val filmDao: FilmDao
): FilmRepository {
    override val topFilms: Flow<List<Film>>
        get() = filmDao.getAllFilmsFromDb().map {
            it.map {
                it.toDomain()
            }
        }

    override val favFilms: Flow<List<Film>>
        get() = filmDao.getAllFavFilmsFromDb().map {
            it.map {
                it.toDomain()
            }
        }

    override fun search(name: String): Flow<List<Film>> {
        return filmDao.searchByName(name = name).map {
            it.map {
                it.toDomain()
            }
        }
    }

    override fun searchFromFav(name: String): Flow<List<Film>> {
        return filmDao.searchByNameFromFav(name = name).map {
            it.map {
                it.toDomain()
            }
        }
    }

    override fun getFilmById(id: Int): Flow<Film?> {
        return filmDao.getFilmById(id).map { it?.toDomain() }
    }

    override suspend fun getTopFilms() {
        coroutineScope{
            for (i in 1..5) {
                val dtos = filmApiService.getTopFilms(page = i).films
                val fav = filmDao
                    .getAllFavFilmsFromDb()
                    .first()
                    .map { it.id }
                    .toSet()
                val films = dtos.map {
                    it.toDomain(fav)
                }
                saveFilms(films)
            }
        }
    }

    override suspend fun loadFilm(id: Int) {
        val fav = filmDao
            .getAllFavFilmsFromDb()
            .first()
            .map { it.id }
            .toSet()
        val film = filmApiService.getFilm(id = id).toDomain(fav)
        updateFilm(film)
    }

    override suspend fun updateFilm(film: Film) {
        filmDao.updateFilm(film.toEntity())
    }

    private suspend fun saveFilms(films: List<Film>) {
        filmDao.insertFilms(films.map { it.toEntity() })
    }
}