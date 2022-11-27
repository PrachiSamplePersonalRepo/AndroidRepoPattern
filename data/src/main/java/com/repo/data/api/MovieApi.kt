package com.repo.data.api

import com.repo.data.entities.MovieData
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/movies")
    suspend fun getMovies(): List<MovieData>

    @GET("/movies")
    suspend fun search(@Query("q") query: String): List<MovieData>
}