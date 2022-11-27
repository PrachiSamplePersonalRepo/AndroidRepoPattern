package com.repo.clean.presentation.moviedetails

import androidx.lifecycle.Observer
import com.repo.clean.presentation.base.BaseViewModelTest
import com.repo.clean.presentation.util.rules.runBlockingTest
import com.repo.clean.ui.moviedetails.MovieDetailsViewModel
import com.repo.clean.ui.moviedetails.MovieDetailsViewModel.MovieDetailsUiState
import com.repo.clean.util.ResourceProvider
import com.repo.domain.entities.MovieEntity
import com.repo.domain.usecase.AddMovieToFavorite
import com.repo.domain.usecase.CheckFavoriteStatus
import com.repo.domain.usecase.GetMovieDetails
import com.repo.domain.usecase.RemoveMovieFromFavorite
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Ali Asadi on 16/05/2020
 **/
@RunWith(MockitoJUnitRunner::class)
class MovieEntityDetailsViewModelTest : BaseViewModelTest() {

    private var movieId: Int = 1413

    private val movie = MovieEntity(movieId, "", "", "")

    @Mock
    lateinit var getMovieDetails: GetMovieDetails

    @Mock
    lateinit var checkFavoriteStatus: CheckFavoriteStatus

    @Mock
    lateinit var addMovieToFavorite: AddMovieToFavorite

    @Mock
    lateinit var removeMovieFromFavorite: RemoveMovieFromFavorite

    @Mock
    lateinit var resourceProvider: ResourceProvider

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(
            movieId = movieId,
            getMovieDetails = getMovieDetails,
            checkFavoriteStatus = checkFavoriteStatus,
            removeMovieFromFavorite = removeMovieFromFavorite,
            addMovieToFavorite = addMovieToFavorite,
            resourceProvider = resourceProvider,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_movieAvailable_showMovieDetails() = coroutineRule.runBlockingTest {

        val movieObs: Observer<MovieDetailsUiState> = mock()

        `when`(getMovieDetails.getMovie(movieId)).thenReturn(Result.Success(movie))

        viewModel.getMovieDetailsUiStateLiveData().observeForever(movieObs)

        viewModel.onInitialState()

        Mockito.verify(movieObs).onChanged(any())
    }

    @Test
    fun onInitialState_movieNotAvailable_doNothing() = coroutineRule.runBlockingTest {
        val movieObs: Observer<MovieDetailsUiState> = mock()

        `when`(getMovieDetails.getMovie(movieId)).thenReturn(Result.Error(mock()))

        viewModel.getMovieDetailsUiStateLiveData().observeForever(movieObs)

        viewModel.onInitialState()

        Mockito.verifyNoMoreInteractions(movieObs)
    }
}