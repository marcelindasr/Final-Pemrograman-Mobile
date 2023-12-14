package com.D121211036.movieSearch.listMovie.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieAccess {
    @Upsert
    suspend fun upsertListMovie(listMovie: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id= :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE category= :category")
    suspend fun getListMovieByCategory(category: String): List<MovieEntity>
}












