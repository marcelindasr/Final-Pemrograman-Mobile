package com.D121211036.movieSearch.listMovie.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.D121211036.movieSearch.listMovie.domain.repository.listMovieRepo
import com.D121211036.movieSearch.listMovie.util.Category
import com.D121211036.movieSearch.listMovie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val listMovieRepo: listMovieRepo
): ViewModel() {
    private var _listMovieState = MutableStateFlow(ListMovieState())
    val listMovieState = _listMovieState.asStateFlow()

    init{
        getPopularListMovie(true)
        getUpcomingListMovie(true)
    }

    fun onEvent(event: ListMovieUiEvent) {
        when(event) {
            ListMovieUiEvent.Navigate -> {
                _listMovieState.update {
                    it.copy(
                        isCurrentPopularScreen = !listMovieState.value.isCurrentPopularScreen
                    )
                }
            }
            is ListMovieUiEvent.Paginate -> {
                if(event.category == Category.POPULAR) {
                    getPopularListMovie(true)
                } else if (event.category == Category.UPCOMING){
                    getUpcomingListMovie(true)
                }
            }
        }
    }

    private fun getPopularListMovie(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _listMovieState.update {
                it.copy(isLoading = true)
            }

            listMovieRepo.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                listMovieState.value.popularListMoviePage
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _listMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _listMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let{
                                popularList ->
                            _listMovieState.update {
                                it.copy(
                                    popularListMovie = listMovieState.value.popularListMovie
                                            + popularList,
                                    popularListMoviePage = listMovieState.value.popularListMoviePage+1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getUpcomingListMovie(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _listMovieState.update {
                it.copy(isLoading = true)
            }

            listMovieRepo.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                listMovieState.value.upcomingListMoviePage
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _listMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _listMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let{
                                upcomingList ->
                            _listMovieState.update {
                                it.copy(
                                    upcomingListMovie = listMovieState.value.upcomingListMovie
                                            + upcomingList,
                                    upcomingListMoviePage = listMovieState.value.upcomingListMoviePage+1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
