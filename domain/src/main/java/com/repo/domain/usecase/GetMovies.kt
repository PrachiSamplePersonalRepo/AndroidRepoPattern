package com.repo.domain.usecase

import com.repo.domain.entities.MovieEntity
import com.repo.domain.repository.MovieRepository
import com.repo.domain.util.Result

open class GetMovies(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(): Result<List<MovieEntity>> = movieRepository.getMovies()
}
