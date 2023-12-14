package com.D121211036.movieSearch.listMovie.data.remote.response

data class listMovieData(
    val page: Int,
    val results: List<MovieData>,
    val total_pages: Int,
    val total_results: Int
)