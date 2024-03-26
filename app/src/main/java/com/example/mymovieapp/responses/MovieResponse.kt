package com.example.mymovieapp.responses
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage
import com.squareup.moshi.JsonClass
import kotlin.collections.contains as contains1

@JsonClass(generateAdapter = true)
 data class MovieResponse (
    val page: Int?,
    val results: List<Movie>?,
)