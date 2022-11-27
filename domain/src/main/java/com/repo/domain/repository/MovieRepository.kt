package com.repo.domain.repository

import com.repo.domain.entities.MovieEntity
import com.repo.domain.util.Result

interface MovieRepository {
    suspend fun getMovies(): Result<List<MovieEntity>>
    suspend fun search(query: String): Result<List<MovieEntity>>
    suspend fun getMovie(movieId: Int): Result<MovieEntity>
    suspend fun getFavoriteMovies(): Result<List<MovieEntity>>
    suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    suspend fun addMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
}