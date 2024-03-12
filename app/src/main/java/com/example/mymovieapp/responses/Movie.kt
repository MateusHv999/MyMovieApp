package com.example.mymovieapp.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie (
    val id: Int,
    val overview: String,
    val title: String,
){
    fun getContent() : String {
        return when{
            overview?.isNotEmpty() == true -> overview
            else -> "Conteudo nao disponivel"
        }
    }

    fun getMovieTitle() : String {
        return when{
            title?.isNotEmpty() == true -> title
            else -> "titulo nao disponivel"
        }
    }
}
