package com.repo.domain.usecase

import com.repo.domain.repository.MovieRepository

class AddMovieToFavorite(
    private val movieRepository: MovieRepository
) {
    suspend fun add(movieId: Int) = movieRepository.addMovieToFavorite(movieId)
}