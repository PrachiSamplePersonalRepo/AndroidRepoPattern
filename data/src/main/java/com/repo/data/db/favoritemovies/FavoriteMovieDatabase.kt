package com.repo.data.db.favoritemovies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.repo.data.entities.FavoriteMovieDbData

@Database(
    entities = [FavoriteMovieDbData::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}