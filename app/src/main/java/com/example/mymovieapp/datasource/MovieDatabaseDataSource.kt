package com.example.mymovieapp.datasource

import android.content.Context
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.ImagesWithAllProperties
import com.example.mymovieapp.data.Movie
import com.example.mymovieapp.database.MovieDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDatabaseDataSource(context: Context) : MovieDataSource {
    private val movieDB = MovieDataBase.getDataBase(context)
    private val movieDao = movieDB.movieDao(movieDB)
    private val imageListDao = movieDB.imageListDao(movieDB)
    private val detailsDao = movieDB.detailsDao(movieDB)

    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            try {
                val movieList = movieDao.getAllMovies()
                Result.success(movieList)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getMovieDetails(movieId: Int): Result<Details?> =
        withContext(Dispatchers.IO) {
            try {
                val details = detailsDao.getDetails(movieId)
                Result.success(details)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getMovieImage(movieId: Int): Result<ImageResponse?> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(loadPersistedImageData(movieId))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun saveMovieData(movieList: List<Movie>) {
        movieDao.insertMovieList(movieList)
    }

    override suspend fun saveDetailsData(details: Details, movieId: Int) {
        val detailsData = Details()
        detailsData.title = details.title
        detailsData.overview = details.overview
        detailsData.poster_path = details.poster_path
        detailsData.movieId = movieId
        detailsDao.insertDetails(detailsData)
    }

    override suspend fun saveImageData(image: ImageResponse, movieId: Int) {
        image.movieId = movieId
        imageListDao.insertImage(image)
    }

    override suspend fun clearMovieData() {
        movieDao.clearMoviesData()
    }

    override suspend fun clearDetailsData() {
        detailsDao.clearDetailsData()
    }

    override suspend fun clearImagesData() {
        imageListDao.clearImagesData()
    }

    private fun mapImagesWithPropertiesToImages(
        imageWithAllProperties: ImagesWithAllProperties
    ): ImageResponse {
        imageWithAllProperties.images.backdrops = imageWithAllProperties.backdrops
        return imageWithAllProperties.images
    }

    private suspend fun loadPersistedImageData(movieId: Int): ImageResponse? {
        val mapImagesWithPropertiesToImages = imageListDao.getImage(movieId)
        return mapImagesWithPropertiesToImages?.let { mapImagesWithPropertiesToImages(it) }
    }
}
