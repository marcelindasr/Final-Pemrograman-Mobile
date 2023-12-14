package com.D121211036.movieSearch.listMovie.view

import com.D121211036.movieSearch.listMovie.domain.model.Movie

data class ListMovieState (
    val isLoading: Boolean = false,

    val popularListMoviePage: Int = 1,
    val upcomingListMoviePage: Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularListMovie: List<Movie> = emptyList(),
    val upcomingListMovie: List<Movie> = emptyList()

)