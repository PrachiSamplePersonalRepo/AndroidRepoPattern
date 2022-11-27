package com.repo.data.db.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repo.data.entities.MovieDbData

@Dao
interface MovieDao {
    /**
     * Get all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieDbData>

    /**
     * Get all favorite movies from the movies table.
     */
    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun getFavoriteMovies(movieIds: List<Int>): List<MovieDbData>

    /**
     * Get movie by id.
     * **/
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieDbData?

    /**
     * Insert all movies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<MovieDbData>)

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM movies")
    fun deleteMovies()
}