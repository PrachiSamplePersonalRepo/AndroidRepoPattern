package com.repo.data.repository.movie

import com.repo.data.db.movies.MovieDao
import com.repo.data.exception.DataNotAvailableException
import com.repo.data.mapper.MovieDataMapper
import com.repo.data.util.DiskExecutor
import com.repo.domain.entities.MovieEntity
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import com.repo.domain.util.Result

class MovieLocalDataSource(
    private val executor: DiskExecutor,
    private val movieDao: MovieDao,
) : MovieDataSource.Local {

    override suspend fun getMovies(): Result<List<MovieEntity>> = withContext(executor.asCoroutineDispatcher()) {
        val movies = movieDao.getMovies()
        return@withContext if (movies.isNotEmpty()) {
            Result.Success(movies.map { MovieDataMapper.toDomain(it) })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext movieDao.getMovie(movieId)?.let {
            Result.Success(MovieDataMapper.toDomain(it))
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movieEntities: List<MovieEntity>) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.saveMovies(movieEntities.map { MovieDataMapper.toDbData(it) })
    }

    override suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieEntity>> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext Result.Success(movieDao.getFavoriteMovies(movieIds).map { MovieDataMapper.toDomain(it) })
    }
}