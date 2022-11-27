package com.repo.clean.entities


sealed class MovieListItem {
    data class Movie(
        val id: Int,
        val imageUrl: String,
    ) : MovieListItem()

    data class Separator(val category: String) : MovieListItem()
}
