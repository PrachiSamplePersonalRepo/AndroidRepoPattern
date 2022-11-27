package com.repo.data.repository.movie

import com.repo.data.util.DispatchersProvider
import com.repo.data.api.MovieApi
import com.repo.data.mapper.MovieDataMapper
import com.repo.domain.entities.MovieEntity

import kotlinx.coroutines.withContext
import com.repo.domain.util.Result

class MovieRemoteDataSource(
    private val movieApi: MovieApi,
    private val dispatchers: DispatchersProvider
) : MovieDataSource.Remote {

    override suspend fun getMovies(): Result<List<MovieEntity>> = withContext(dispatchers.getIO()) {
        return@withContext try {
            val result = movieApi.getMovies()
            Result.Success(result.map {
                MovieDataMapper.toDomain(it)
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun search(query: String): Result<List<MovieEntity>> = withContext(dispatchers.getIO()) {
        return@withContext try {
            val result = movieApi.search(query)
            Result.Success(result.map {
                MovieDataMapper.toDomain(it)
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}