package com.example.mymovieapp.responses

import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@JsonClass(generateAdapter = true)
data class ImageResponse(
    val backdrops: List<Image>?,
    val posters: List<Image>?,
){
    fun getCarouselImages() = backdrops?.map {
        CarouselItem(imageUrl = it.getFullPath())
    }
}

