package com.example.mymovieapp.responses

import com.example.mymovieapp.Credentials
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Image(
  val file_path: String?,
){
  fun getFullPath(): String{
     return Credentials.imgUrl + file_path
  }
}