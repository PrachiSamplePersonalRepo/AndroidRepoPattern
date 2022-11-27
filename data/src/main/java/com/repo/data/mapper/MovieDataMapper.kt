package com.repo.data.mapper

import com.repo.data.entities.MovieData
import com.repo.data.entities.MovieDbData
import com.repo.domain.entities.MovieEntity

object MovieDataMapper {

    fun toDomain(movieData: MovieData): MovieEntity = MovieEntity(
        id = movieData.id,
        image = movieData.image,
        description = movieData.description,
        title = movieData.title,
        category = movieData.category
    )

    fun toDomain(movieDbData: MovieDbData): MovieEntity = MovieEntity(
        id = movieDbData.id,
        image = movieDbData.image,
        description = movieDbData.description,
        title = movieDbData.title,
        category = movieDbData.category
    )

    fun toDbData(movieEntity: MovieEntity): MovieDbData = MovieDbData(
        id = movieEntity.id,
        image = movieEntity.image,
        description = movieEntity.description,
        title = movieEntity.title,
        category = movieEntity.category
    )
}