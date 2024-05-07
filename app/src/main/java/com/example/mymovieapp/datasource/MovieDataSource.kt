package com.example.mymovieapp.datasource

import androidx.room.Entity
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie

interface MovieDataSource{
    suspend fun getMovieData() : Result<List<Movie>?>
    suspend fun getMovieImage(movieId: Int) : Result<ImageResponse?>
    suspend fun getMovieDetails(movieId: Int) : Result<Details?>
    suspend fun saveMovieData(movieList: List<Movie>)
    suspend fun saveDetailsData(details: Details, movieId: Int)
    suspend fun saveImageData(image: ImageResponse, movieId: Int)
    suspend fun clearMovieData()
    suspend fun clearDetailsData()
    suspend fun clearImagesData()
}