package com.repo.domain.usecase

import com.repo.domain.repository.MovieRepository

class RemoveMovieFromFavorite(
    private val movieRepository: MovieRepository
) {
    suspend fun remove(movieId: Int) = movieRepository.removeMovieFromFavorite(movieId)
}