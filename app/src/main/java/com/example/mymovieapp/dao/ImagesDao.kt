package com.example.mymovieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.mymovieapp.data.Image

@Dao
abstract class ImagesDao: BaseDao<Image> {
    @Transaction
    @Query("SELECT * FROM movieImages WHERE movieId = :id")
    abstract suspend fun getImages(id: Int): Image?
    @Transaction
    @Query("SELECT * FROM movieImages")
    abstract suspend fun getAllImages(): List<Image>?
    @Transaction
    @Query("DELETE from movieImages")
    abstract suspend fun clearBackdropData()
}