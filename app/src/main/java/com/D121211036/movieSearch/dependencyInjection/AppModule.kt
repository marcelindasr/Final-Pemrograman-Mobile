package com.D121211036.movieSearch.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.D121211036.movieSearch.listMovie.data.local.movie.MovieDatabase
import com.D121211036.movieSearch.listMovie.data.remote.ApiMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesApiMovie() : ApiMovie {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiMovie.BASE_URL)
            .client(client)
            .build()
            .create(ApiMovie::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDb(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviedb.db"
        ).build()
    }

}






