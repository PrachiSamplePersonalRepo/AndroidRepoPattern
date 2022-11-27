package com.repo.domain.usecase

import com.repo.domain.entities.MovieEntity
import com.repo.domain.repository.MovieRepository
import com.repo.domain.util.Result

class SearchMovies(
    private val movieRepository: MovieRepository
) {
    suspend fun search(query: String): Result<List<MovieEntity>> = movieRepository.search(query)
}
