package com.D121211036.movieSearch.listMovie.util

sealed class Screen (val rout :String){
    object Home : Screen("main")
    object PopularMovieList : Screen( "popularMovie")
    object UpcomingMovieList : Screen( "upcomingMovie")
    object Details : Screen("details")
    object Search : Screen("searchMovie")
}