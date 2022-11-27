package com.repo.clean.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.repo.clean.databinding.FragmentFavoritesBinding
import com.repo.clean.ui.favorites.FavoritesViewModel
import com.repo.clean.ui.base.BaseFragment
import com.repo.clean.ui.favorites.FavoritesViewModel.FavoriteUiState
import com.repo.clean.ui.favorites.FavoritesViewModel.NavigationState
import com.repo.clean.ui.favorites.FavoritesViewModel.NavigationState.MovieDetails
import com.repo.clean.ui.feed.MovieAdapter
import com.repo.clean.ui.feed.MovieAdapterSpanSize
import com.repo.clean.util.hide
import com.repo.clean.util.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesViewModel by viewModels()

    private val movieAdapter by lazy {
        MovieAdapter(viewModel::onMovieClicked, getImageFixedSize())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        setupRecyclerView()
    }

    private fun setupRecyclerView(config: MovieAdapterSpanSize.Config = MovieAdapterSpanSize.Config(3)) = with(binding.recyclerView) {
        adapter = movieAdapter
        layoutManager = createMovieGridLayoutManager(config)
        setHasFixedSize(true)
        setItemViewCacheSize(0)
    }

    private fun createMovieGridLayoutManager(config: MovieAdapterSpanSize.Config): GridLayoutManager = GridLayoutManager(
        requireActivity(),
        config.gridSpanSize,
        RecyclerView.VERTICAL,
        false
    ).apply {
        spanSizeLookup = MovieAdapterSpanSize.Lookup(config, movieAdapter)
    }

    private fun setupObservers() = with(viewModel) {
        getFavoriteUiState().observe { handleFavoriteUiState(it) }
        getNavigateState().observe { handleNavigationState(it) }
    }

    private fun handleFavoriteUiState(favoriteUiState: FavoriteUiState) = with(favoriteUiState) {
        if (isLoading) {
            binding.progressBar.show()
            if (binding.noDataView.isVisible) binding.noDataView.hide()
        } else {
            if (noDataAvailable) binding.noDataView.show() else binding.noDataView.hide()
            binding.progressBar.hide()
            movieAdapter.submitList(movies)
        }
    }

    private fun handleNavigationState(navigationState: NavigationState) = when (navigationState) {
        is MovieDetails -> navigateToMovieDetails(navigationState.movieId)
    }

    private fun navigateToMovieDetails(movieId: Int) = findNavController().navigate(
        FavoritesFragmentDirections.toMovieDetailsActivity(movieId)
    )

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}