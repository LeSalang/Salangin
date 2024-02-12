package com.lesa.topfilms.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM films")
    fun getAllFilmsFromDb(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE is_fav = 1")
    fun getAllFavFilmsFromDb(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE id =:id")
    fun getFilmById(id: Int): Flow<FilmEntity?>

    @Query("SELECT * FROM films WHERE name_ru LIKE '%' || :name || '%'")
    fun searchByName(name: String): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE name_ru LIKE '%' || :name || '%' AND is_fav =1")
    fun searchByNameFromFav(name: String): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFilm(filmEntity: FilmEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilms(films: List<FilmEntity>)



}