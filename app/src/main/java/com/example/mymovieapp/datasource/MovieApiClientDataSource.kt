package com.example.mymovieapp.datasource

import com.example.mymovieapp.api.Credentials
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApiClientDataSource : MovieDataSource {

    val retrofit =
        Retrofit.Builder()
            .baseUrl(Credentials.baseurl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private val MovieService = retrofit.create(com.example.mymovieapp.api.MovieService::class.java)

    // API Responses
    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            val request = MovieService.getMovie(Credentials.apikey)
            when {
                request.isSuccessful -> Result.success(request.body()?.results)
                else -> Result.failure(Throwable(request.message()))
            }
        }

    override suspend fun getMovieImage(movieId: Int): Result<ImageResponse?> =
        withContext(Dispatchers.IO) {
            val request = MovieService.getImage(movieId, Credentials.apikey)
            when {
                request.isSuccessful -> Result.success(request.body())
                else -> Result.failure(Throwable(request.message()))
            }
        }

    override suspend fun getMovieDetails(movieId: Int): Result<Details?> =
        withContext(Dispatchers.IO) {
            val request = MovieService.getDetails(movieId, Credentials.apikey)
            when {
                request.isSuccessful -> Result.success(request.body())
                else -> Result.failure(Throwable(request.message()))
            }
        }

    // NO-OP
    override suspend fun saveMovieData(movieList: List<Movie>) {
        // NO-OP
    }

    override suspend fun saveDetailsData(details: Details, movieId: Int) {
        // NO-OP
    }

    override suspend fun saveImageData(image: ImageResponse, movieId: Int) {
        // NO-OP
    }

    override suspend fun clearMovieData() {
        // NO-OP
    }

    override suspend fun clearDetailsData() {
        // NO-OP
    }

    override suspend fun clearImagesData() {
        // NO-OP
    }
}
