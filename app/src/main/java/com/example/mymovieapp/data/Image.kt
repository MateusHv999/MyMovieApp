package com.example.mymovieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mymovieapp.api.Credentials
import com.squareup.moshi.JsonClass
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ImageResponse::class,
            parentColumns = ["id"],
            childColumns = ["imageId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val file_path: String?,
    @ColumnInfo(index = true)
    var imageId: Int?
){
  fun getFullPath(): String{
     return Credentials.imgUrl + file_path
  }
}