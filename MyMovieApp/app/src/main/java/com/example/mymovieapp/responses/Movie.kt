package com.example.mymovieapp.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie (
    val id: Int,
    val overview: String,
    val title: String,
    val poster_path: String
)