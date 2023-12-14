package com.D121211036.movieSearch.details.view

import com.D121211036.movieSearch.listMovie.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)