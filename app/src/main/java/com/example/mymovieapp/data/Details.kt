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
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
class Details(){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var title: String? = null
    var poster_path: String? = null
    var overview: String? = null
    @ColumnInfo(index = true)
    var movieId : Int?  = null
    fun getContentDetails(): String {
        val overview = overview
        return when {
            overview?.isNotEmpty() == true -> overview
            else -> "Conteudo nao disponivel"
        }
    }
    fun getMovieTitle(): String {
        val title = title
        return when {
            title?.isNotEmpty() == true -> title
            else -> "titulo nao disponivel"
        }
    }
    fun getImageDetails() = Credentials.imgUrl + poster_path
}

