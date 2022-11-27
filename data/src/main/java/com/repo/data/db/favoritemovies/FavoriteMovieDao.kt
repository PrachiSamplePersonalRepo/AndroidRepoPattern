package com.repo.data.db.favoritemovies

import androidx.room.*
import com.repo.data.entities.FavoriteMovieDbData

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): List<FavoriteMovieDbData>

    @Query("SELECT * FROM favorite_movies where movieId=:movieId")
    fun get(movieId: Int): FavoriteMovieDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: FavoriteMovieDbData)

    @Delete
    fun remove(favoriteMovieDbData: FavoriteMovieDbData)

}