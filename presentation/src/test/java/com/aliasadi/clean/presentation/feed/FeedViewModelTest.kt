package com.repo.clean.presentation.feed

import androidx.lifecycle.Observer
import com.repo.clean.presentation.base.BaseViewModelTest
import com.repo.clean.presentation.util.rules.runBlockingTest
import com.repo.clean.ui.feed.FeedViewModel
import com.repo.clean.ui.feed.FeedViewModel.NavigationState
import com.repo.clean.ui.feed.FeedViewModel.NavigationState.MovieDetails
import com.repo.clean.ui.feed.FeedViewModel.UiState
import com.repo.clean.ui.feed.FeedViewModel.UiState.*
import com.repo.domain.usecase.GetMovies
import com.repo.domain.util.Result
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Ali Asadi on 16/05/2020
 **/

@RunWith(MockitoJUnitRunner::class)
class FeedViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var getMovies: GetMovies

    private lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        viewModel = FeedViewModel(
            getMovies = getMovies,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_loadMovies_onSuccess_hideLoadingAndShowMovies() = coroutineRule.runBlockingTest {
        val uiStateObs: Observer<UiState> = mock()

        viewModel.getUiState().observeForever(uiStateObs)

        `when`(getMovies.execute()).thenReturn(Result.Success(listOf()))

        viewModel.onInitialState()

        val argumentCapture = ArgumentCaptor.forClass(UiState::class.java)
        verify(uiStateObs, times(3)).onChanged(argumentCapture.capture())

        assert(argumentCapture.allValues[0] is Loading)
        assert(argumentCapture.allValues[1] is NotLoading)
        assert(argumentCapture.allValues[2] is FeedUiState)
    }

    @Test
    fun onInitialState_loadMovies_onFailure_hideLoadingAndShowErrorMessage() = coroutineRule.runBlockingTest {
        val uiStateObs: Observer<UiState> = mock()

        viewModel.getUiState().observeForever(uiStateObs)


        `when`(getMovies.execute()).thenReturn(Result.Error(mock()))

        viewModel.onInitialState()

        val argumentCapture = ArgumentCaptor.forClass(UiState::class.java)
        verify(uiStateObs, times(3)).onChanged(argumentCapture.capture())

        assert(argumentCapture.allValues[0] is Loading)
        assert(argumentCapture.allValues[1] is NotLoading)
        assert(argumentCapture.allValues[2] is Error)
    }


    @Test
    fun onInitialState_loadMovies_onLoading_showLoadingView() = coroutineRule.runBlockingTest {
        val uiStateObs: Observer<UiState> = mock()
        viewModel.getUiState().observeForever(uiStateObs)

        viewModel.onInitialState()

        verify(uiStateObs).onChanged(isA(Loading.javaClass))
    }


    @Test
    fun onMovieClicked_navigateToMovieDetails() {
        val navigateObs: Observer<NavigationState> = mock()
        val movieId = 1

        viewModel.getNavigationState().observeForever(navigateObs)

        viewModel.onMovieClicked(movieId)

        verify(navigateObs).onChanged(isA(MovieDetails::class.java))
    }

}