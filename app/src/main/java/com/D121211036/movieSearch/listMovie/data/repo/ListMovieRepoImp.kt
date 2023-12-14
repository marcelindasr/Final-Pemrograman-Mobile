package com.D121211036.movieSearch.listMovie.data.repo

import com.D121211036.movieSearch.listMovie.data.local.movie.MovieDatabase
import com.D121211036.movieSearch.listMovie.data.remote.ApiMovie
import com.D121211036.movieSearch.listMovie.domain.repository.listMovieRepo

import com.D121211036.movieSearch.listMovie.data.mappers.toMovie
import com.D121211036.movieSearch.listMovie.data.mappers.toMovieEntity
import com.D121211036.movieSearch.listMovie.util.Resource
import com.D121211036.movieSearch.listMovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ListMovieRepoImp @Inject constructor(
    private val ApiMovie: ApiMovie,
    private val movieDatabase: MovieDatabase
) : listMovieRepo {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieAccess.getListMovieByCategory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                ApiMovie.getListMovies(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { MovieData ->
                    MovieData.toMovieEntity(category)
                }
            }

            movieDatabase.movieAccess.upsertListMovie(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {

            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieAccess.getMovieById(id)

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such movie"))
            emit(Resource.Loading(false))

        }
    }
}


















