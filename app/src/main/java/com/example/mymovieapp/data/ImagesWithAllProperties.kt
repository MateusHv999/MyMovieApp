package com.example.mymovieapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class ImagesWithAllProperties (
    @Embedded var images: ImageResponse,
    @Relation(
        entity = Image::class,
        parentColumn = "id",
        entityColumn = "imageId"
    ) var backdrops: List<Image>,
)