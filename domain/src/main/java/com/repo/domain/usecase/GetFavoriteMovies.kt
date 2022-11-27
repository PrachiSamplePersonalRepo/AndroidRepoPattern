package com.repo.domain.usecase

import com.repo.domain.entities.MovieEntity
import com.repo.domain.repository.MovieRepository
import com.repo.domain.util.Result

class GetFavoriteMovies(
    private val movieRepository: MovieRepository
) {
    suspend fun getFavoriteMovies(): Result<List<MovieEntity>> = movieRepository.getFavoriteMovies()
}