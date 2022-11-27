package com.repo.data.repository.movie

import com.repo.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.repo.domain.entities.MovieEntity
import com.repo.domain.repository.MovieRepository
import com.repo.domain.util.Result
import com.repo.domain.util.onSuccess
import com.repo.domain.util.getResult

class MovieRepositoryImpl constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val cache: MovieDataSource.Cache,
    private val localFavorite: FavoriteMoviesDataSource.Local
) : MovieRepository {

    override suspend fun getMovies(): Result<List<MovieEntity>> = getMoviesFromCache()

    override suspend fun search(query: String): Result<List<MovieEntity>> = remote.search(query)

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = getMovieFromCache(movieId)

    override suspend fun getFavoriteMovies(): Result<List<MovieEntity>> = getFavoriteMoviesFromLocal()

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> {
        return localFavorite.checkFavoriteStatus(movieId)
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        localFavorite.addMovieToFavorite(movieId)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        localFavorite.removeMovieFromFavorite(movieId)
    }

    private suspend fun getMovieFromCache(movieId: Int): Result<MovieEntity> = cache.getMovie(movieId).getResult({
        it
    }, {
        getMovieFromLocal(movieId)
    })

    private suspend fun getMovieFromLocal(movieId: Int): Result<MovieEntity> = local.getMovie(movieId)

    private suspend fun getMoviesFromCache(): Result<List<MovieEntity>> = cache.getMovies().getResult({
        it
    }, {
        getMoviesFromLocal()
    })

    private suspend fun getMoviesFromLocal(): Result<List<MovieEntity>> = local.getMovies().getResult({
        refreshCache(it.data)
        it
    }, {
        getMoviesFromRemote()
    })

    private suspend fun getMoviesFromRemote(): Result<List<MovieEntity>> = remote.getMovies().onSuccess {
        saveMovies(it)
        refreshCache(it)
    }

    private suspend fun saveMovies(movieEntities: List<MovieEntity>) {
        local.saveMovies(movieEntities)
    }

    private suspend fun refreshCache(movieEntities: List<MovieEntity>) {
        cache.saveMovies(movieEntities)
    }

    private suspend fun getFavoriteMoviesFromLocal(): Result<List<MovieEntity>> {
        return localFavorite.getFavoriteMovieIds().getResult({
            local.getFavoriteMovies(it.data.map { it.movieId })
        }, {
            Result.Error(it.error)
        })
    }
}