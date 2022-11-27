package com.repo.clean.mapper

import com.repo.clean.entities.MovieListItem
import com.repo.domain.entities.MovieEntity


object MovieEntityMapper {

    fun toPresentation(movieEntity: MovieEntity) = MovieListItem.Movie(
        id = movieEntity.id,
        imageUrl = movieEntity.image
    )
}