package com.repo.domain.usecase

import com.repo.domain.repository.MovieRepository
import com.repo.domain.util.Result

class CheckFavoriteStatus(
    private val movieRepository: MovieRepository
) {
    suspend fun check(movieId: Int): Result<Boolean> = movieRepository.checkFavoriteStatus(movieId)
}