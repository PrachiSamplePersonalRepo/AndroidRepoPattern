package com.repo.domain.usecase

import com.repo.domain.entities.MovieEntity
import com.repo.domain.repository.MovieRepository
import com.repo.domain.util.Result

class GetMovieDetails(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovie(movieId: Int): Result<MovieEntity> = movieRepository.getMovie(movieId)
}
