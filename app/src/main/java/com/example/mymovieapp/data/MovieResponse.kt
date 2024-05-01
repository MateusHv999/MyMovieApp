package com.example.mymovieapp.data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
 data class MovieResponse (
    val page: Int?,
    val results: List<Movie>?,
)