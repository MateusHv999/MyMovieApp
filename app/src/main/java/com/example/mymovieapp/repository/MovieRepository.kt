package com.example.mymovieapp.repository

import android.content.Context
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie
import com.example.mymovieapp.datasource.MovieApiClientDataSource
import com.example.mymovieapp.datasource.MovieDatabaseDataSource

class MovieRepository(context: Context) {
    private val MovieApiClientDataSource = MovieApiClientDataSource()
    private val MovieDatabaseDataSource = MovieDatabaseDataSource(context)

    // Save Data & Handlers
    suspend fun getMovieData(): Result<List<Movie>?> {
        return try {
            val result = MovieApiClientDataSource.getMovieData()
            if (result.isSuccess) {
                result.getOrNull()?.let { persistMovieData(it) }
                result
            } else {
                getLocalMovieData()
            }
        } catch (e: Exception) {
            getLocalMovieData()
        }
    }

    suspend fun getDetailsData(movieId: Int): Result<Details?> {
        return try {
            val result = MovieApiClientDataSource.getMovieDetails(movieId)
            if (result.isSuccess) {
                result.getOrNull()?.let { persistDetailsData(it, movieId) }
            }
            result
        } catch (e: Exception) {
            getLocalDetailsData(movieId)
        }
    }

    suspend fun getImageData(movieId: Int): Result<ImageResponse?> {
        return try {
            val result = MovieApiClientDataSource.getMovieImage(movieId)
            if (result.isSuccess) {
                result.getOrNull()?.let { persistImageData(it, movieId) }
            }
            result
        } catch (e: Exception) {
            getLocalImageData(movieId)
        }
    }

    // Persist
    private suspend fun persistMovieData(movieList: List<Movie>) {
        MovieDatabaseDataSource.clearMovieData()
        movieList?.let { MovieDatabaseDataSource.saveMovieData(it) }
    }

    private suspend fun persistDetailsData(details: Details, movieId: Int) {
        val detailsData = Details()
        detailsData.title = details.title
        detailsData.overview = details.overview
        detailsData.poster_path = details.poster_path
        detailsData.movieId = movieId
        details?.let { MovieDatabaseDataSource.saveDetailsData(details, movieId) }
    }

    private suspend fun persistImageData(image: ImageResponse, movieId: Int) {
        image.movieId = movieId
        MovieDatabaseDataSource.clearImagesData()
        image?.let { MovieDatabaseDataSource.saveImageData(image, movieId) }
    }

    // Localdata
    private suspend fun getLocalMovieData(): Result<List<Movie>?> =
        MovieDatabaseDataSource.getMovieData()

    private suspend fun getLocalDetailsData(movieId: Int): Result<Details?> =
        MovieDatabaseDataSource.getMovieDetails(movieId)

    private suspend fun getLocalImageData(movieId: Int): Result<ImageResponse?> =
        MovieDatabaseDataSource.getMovieImage(movieId)
}
