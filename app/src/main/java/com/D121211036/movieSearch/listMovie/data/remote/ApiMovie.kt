package com.D121211036.movieSearch.listMovie.data.remote

import com.D121211036.movieSearch.listMovie.data.remote.response.listMovieData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovie {

    @GET("movie/{category}")
    suspend fun getListMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String = API_KEY
    ): listMovieData

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/3/t/p/w500"
        const val API_KEY = "f6707fe48c0e27df16600fa9efd12133"

    }

}