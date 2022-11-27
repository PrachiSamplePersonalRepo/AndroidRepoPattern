package com.repo.clean.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.repo.clean.MovieDetailsGraphDirections
import com.repo.clean.databinding.FragmentFeedBinding
import com.repo.clean.ui.feed.FeedViewModel
import com.repo.clean.ui.base.BaseFragment
import com.repo.clean.ui.feed.FeedViewModel.NavigationState.MovieDetails
import com.repo.clean.ui.feed.FeedViewModel.UiState.*
import com.repo.clean.util.hide
import com.repo.clean.util.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private val viewModel: FeedViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter(viewModel::onMovieClicked, getImageFixedSize()) }

    private val detailsNavController by lazy { binding.container.getFragment<Fragment>().findNavController() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFeedBinding = FragmentFeedBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        setupRecyclerView()
    }

    private fun setupRecyclerView(config: MovieAdapterSpanSize.Config = MovieAdapterSpanSize.Config(
        3
    )
    ) = with(binding.recyclerView) {
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

    private fun observeViewModel() = with(viewModel) {

        getUiState().observe {
            when (it) {
                is FeedUiState -> movieAdapter.submitList(it.movies)
                is Loading -> binding.progressBar.show()
                is NotLoading -> binding.progressBar.hide()
                is Error -> Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }

        getNavigationState().observe {
            when (it) {
                is MovieDetails -> showOrNavigateToMovieDetails(it.movieId)
            }
        }
    }

    private fun showOrNavigateToMovieDetails(movieId: Int) = if (binding.root.isSlideable) {
        navigateToMovieDetails(movieId)
    } else {
        showMovieDetails(movieId)
    }

    private fun navigateToMovieDetails(movieId: Int) = findNavController().navigate(
        FeedFragmentDirections.toMovieDetailsActivity(movieId)
    )

    private fun showMovieDetails(movieId: Int) = detailsNavController.navigate(
        MovieDetailsGraphDirections.toMovieDetails(movieId)
    )

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}