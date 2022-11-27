package com.repo.data.repository.movie.favorite

import com.repo.data.db.favoritemovies.FavoriteMovieDao
import com.repo.data.entities.FavoriteMovieDbData
import com.repo.data.exception.DataNotAvailableException
import com.repo.data.util.DiskExecutor
import com.repo.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext


class FavoriteMoviesLocalDataSource(
    private val executor: DiskExecutor,
    private val favoriteMovieDao: FavoriteMovieDao,
) : FavoriteMoviesDataSource.Local {

    override suspend fun getFavoriteMovieIds(): Result<List<FavoriteMovieDbData>> =
        withContext(executor.asCoroutineDispatcher()) {
            val movieIds = favoriteMovieDao.getAll()
            return@withContext if (movieIds.isNotEmpty()) {
                Result.Success(movieIds)
            } else {
                Result.Error(DataNotAvailableException())
            }
        }

    override suspend fun addMovieToFavorite(movieId: Int) =
        withContext(executor.asCoroutineDispatcher()) {
            favoriteMovieDao.add(FavoriteMovieDbData(movieId))
        }

    override suspend fun removeMovieFromFavorite(movieId: Int) =
        withContext(executor.asCoroutineDispatcher()) {
            favoriteMovieDao.remove(FavoriteMovieDbData(movieId))
        }

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> =
        withContext(executor.asCoroutineDispatcher()) {
            return@withContext Result.Success(favoriteMovieDao.get(movieId) != null)
        }

}