package com.example.mymovieapp

import com.example.mymovieapp.responses.DetailsResponse
import com.example.mymovieapp.responses.ImageResponse
import com.example.mymovieapp.responses.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("3/movie/popular")
    suspend fun getMovie(
        @Query("api_key") apiKey: String
    ) : Response<MovieResponse>

    @GET("3/movie/{movie_id}/images")
    suspend fun getImage(
        @Path("movie_id") id: Int?,
        @Query("api_key") apiKey: String
    ) : Response<ImageResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") id: Int?,
        @Query("api_key") apiKey: String
    ) : Response<DetailsResponse>
}