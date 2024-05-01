package com.example.mymovieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
@Entity(
    tableName = "movieImages",
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
class ImageResponse(){
    @PrimaryKey
    var id: Int? = null
    @Ignore
    var backdrops: List<Image>? = null
    @Ignore
    var posters: List<Image>? = null
    @ColumnInfo(index = true)
    var movieId : Int?  = null
    @Ignore
    constructor(
        id: Int? = null,
        backdrops: List<Image>? = null,
        posters: List<Image>? = null,
        movieId: Int? = null
    ) : this() {
        this.id = id
        this.backdrops = backdrops
        this.posters = posters
        this.movieId = movieId
    }
    fun getCarouselImages() = backdrops?.map {
        CarouselItem(imageUrl = it.getFullPath())
    }
}

