package com.example.mymovieapp.responses

import com.example.mymovieapp.Credentials
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailsResponse (
    val id: Int?,
    val title: String?,
    val poster_path: String?,
    val popularity: Double?,
    val overview: String?,
){
    fun getContentDetails(): String {
        return when {
            overview?.isNotEmpty() == true -> overview
            else -> "Conteudo nao disponivel"
        }
    }
    fun getMovieTitle(): String {
        return when {
            title?.isNotEmpty() == true -> title
            else -> "titulo nao disponivel"
        }
    }
    fun getImageDetails() = Credentials.imgUrl + poster_path
}

