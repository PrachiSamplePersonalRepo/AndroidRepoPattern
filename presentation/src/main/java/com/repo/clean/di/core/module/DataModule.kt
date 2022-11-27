package com.repo.clean.di.core.module

import com.repo.data.util.DispatchersProvider
import com.repo.data.api.MovieApi
import com.repo.data.db.favoritemovies.FavoriteMovieDao
import com.repo.data.db.movies.MovieDao

import com.repo.domain.usecase.*
import com.repo.data.repository.movie.*
import com.repo.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.repo.data.repository.movie.favorite.FavoriteMoviesLocalDataSource
import com.repo.data.util.DiskExecutor
import com.repo.domain.repository.MovieRepository
import com.repo.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemote: MovieDataSource.Remote,
        movieLocal: MovieDataSource.Local,
        movieCache: MovieDataSource.Cache,
        favoriteLocal: FavoriteMoviesDataSource.Local,
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemote, movieLocal, movieCache, favoriteLocal)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        executor: DiskExecutor, movieDao: MovieDao
    ): MovieDataSource.Local {
        return MovieLocalDataSource(executor, movieDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieLocalDataSource(
        executor: DiskExecutor,
        favoriteMovieDao: FavoriteMovieDao
    ): FavoriteMoviesDataSource.Local {
        return FavoriteMoviesLocalDataSource(executor, favoriteMovieDao)
    }

    @Provides
    @Singleton
    fun provideMovieCacheDataSource(diskExecutor: DiskExecutor): MovieDataSource.Cache {
        return MovieCacheDataSource(diskExecutor)
    }


    @Provides
    @Singleton
    fun provideMovieRemoveDataSource(movieApi: MovieApi, dispatchers: DispatchersProvider): MovieDataSource.Remote {
        return MovieRemoteDataSource(movieApi, dispatchers)
    }

    @Provides
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMovies {
        return GetMovies(movieRepository)
    }

    @Provides
    fun provideSearchMoviesUseCase(movieRepository: MovieRepository): SearchMovies {
        return SearchMovies(movieRepository)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetails {
        return GetMovieDetails(movieRepository)
    }

    @Provides
    fun provideGetFavoriteMoviesUseCase(movieRepository: MovieRepository): GetFavoriteMovies {
        return GetFavoriteMovies(movieRepository)
    }

    @Provides
    fun provideCheckFavoriteStatusUseCase(movieRepository: MovieRepository): CheckFavoriteStatus {
        return CheckFavoriteStatus(movieRepository)
    }

    @Provides
    fun provideAddMovieToFavoriteUseCase(movieRepository: MovieRepository): AddMovieToFavorite {
        return AddMovieToFavorite(movieRepository)
    }

    @Provides
    fun provideRemoveMovieFromFavoriteUseCase(movieRepository: MovieRepository): RemoveMovieFromFavorite {
        return RemoveMovieFromFavorite(movieRepository)
    }
}