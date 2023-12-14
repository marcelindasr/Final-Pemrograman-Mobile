package com.D121211036.movieSearch.listMovie.view

sealed interface ListMovieUiEvent {
    data class Paginate(val category: String) : ListMovieUiEvent
    object Navigate : ListMovieUiEvent
}