package com.repo.data.repository.movie

import com.repo.domain.entities.MovieEntity
import com.repo.domain.util.Result


interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(): Result<List<MovieEntity>>
        suspend fun search(query: String): Result<List<MovieEntity>>
    }

    interface Local {
        suspend fun getMovies(): Result<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): Result<MovieEntity>
        suspend fun saveMovies(movieEntities: List<MovieEntity>)
        suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieEntity>>
    }

    interface Cache : Local
}