package com.example.mymovieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.mymovieapp.database.MovieDataBase
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.ImagesWithAllProperties
import javax.inject.Inject

@Dao
abstract class ImageListDao(): BaseDao<ImageResponse> {
    @Inject
    lateinit var backdropDao : ImagesDao
    @Transaction
    @Query("SELECT * FROM movieImages")
    abstract suspend fun getAllImages() : List<ImagesWithAllProperties>?
    @Transaction
    @Query("SELECT * FROM movieImages WHERE movieId=:movieId")
    abstract suspend fun getImage(movieId: Int): ImagesWithAllProperties?
    @Transaction
    open suspend fun insertImage(image:ImageResponse){
        image.backdrops?.forEach{
            it.imageId = image.id
        }
        insert(image)
        image.backdrops?.let { backdropDao.insertList(it) }
    }
    @Transaction
    @Query("DELETE from movieImages")
    abstract suspend fun clearImagesData()
}