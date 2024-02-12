package com.lesa.topfilms.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(ListStringConverter::class)
@Database(
    version = 1,
    entities = [FilmEntity::class]
)
abstract class FilmRoomDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object{
        @Volatile
        private var INSTANCE: FilmRoomDatabase? = null

        fun getDatabase(context: Context): FilmRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}