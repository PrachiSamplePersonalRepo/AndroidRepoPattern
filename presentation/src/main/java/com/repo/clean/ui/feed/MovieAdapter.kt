package com.repo.clean.ui.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.repo.clean.R
import com.repo.clean.entities.MovieListItem
import com.repo.clean.ui.feed.viewholder.MovieViewHolder
import com.repo.clean.ui.feed.viewholder.SeparatorViewHolder

class MovieAdapter(
    private val onMovieClick: (movieId: Int) -> Unit,
    private val imageFixedSize: Int,
) : ListAdapter<MovieListItem, ViewHolder>(MovieDiffCallback) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MovieListItem.Movie -> R.layout.item_movie
        is MovieListItem.Separator -> R.layout.item_separator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        R.layout.item_movie -> MovieViewHolder(parent, onMovieClick, imageFixedSize)
        R.layout.item_separator -> SeparatorViewHolder(parent)
        else -> throw RuntimeException("Illegal view type")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieViewHolder -> holder.bind(item as MovieListItem.Movie)
            is SeparatorViewHolder -> holder.bind(item as MovieListItem.Separator)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        when (holder) {
            is MovieViewHolder -> holder.unBind()
        }
    }
}