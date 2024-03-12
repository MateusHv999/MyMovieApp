package com.example.mymovieapp.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResponse (
    val id: Int,
    val posters: List<String>,
    val logos: List<String>,
    val backdrops: List<String>,
)