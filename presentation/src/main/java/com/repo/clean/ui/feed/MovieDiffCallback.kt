package com.repo.clean.ui.feed

import androidx.recyclerview.widget.DiffUtil
import com.repo.clean.entities.MovieListItem

object MovieDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean =
        if (oldItem is MovieListItem.Movie && newItem is MovieListItem.Movie) {
            oldItem.id == newItem.id
        } else {
            oldItem == newItem
        }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean = oldItem == newItem
}