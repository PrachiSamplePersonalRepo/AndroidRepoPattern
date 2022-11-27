package com.repo.data.repository.movie.favorite

import com.repo.data.entities.FavoriteMovieDbData
import com.repo.domain.util.Result

interface FavoriteMoviesDataSource {

    interface Local {
        suspend fun getFavoriteMovieIds(): Result<List<FavoriteMovieDbData>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
        suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    }

}