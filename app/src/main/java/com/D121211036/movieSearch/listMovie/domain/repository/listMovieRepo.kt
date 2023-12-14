package com.D121211036.movieSearch.listMovie.domain.repository

import com.D121211036.movieSearch.listMovie.domain.model.Movie
import com.D121211036.movieSearch.listMovie.util.Resource
import kotlinx.coroutines.flow.Flow

interface listMovieRepo {

    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}