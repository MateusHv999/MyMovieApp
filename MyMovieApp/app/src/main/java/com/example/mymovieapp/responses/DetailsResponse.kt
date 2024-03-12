package com.example.mymovieapp.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailsResponse (
    val id: Int,
    val title: String,
    val poster_path: String,
    val popularity: Double,
    val overview: String,
)