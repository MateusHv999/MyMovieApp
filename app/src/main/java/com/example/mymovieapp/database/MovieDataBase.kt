package com.example.mymovieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovieapp.dao.ImagesDao
import com.example.mymovieapp.dao.DetailsDao
import com.example.mymovieapp.dao.ImageListDao
import com.example.mymovieapp.dao.MovieDao
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.Image
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie

@Database(
    entities = [Movie::class, Details::class, ImageResponse::class, Image::class],
    version = 3
)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun detailsDao(movieDatabase: MovieDataBase): DetailsDao
    abstract fun imageListDao(movieDataBase: MovieDataBase): ImageListDao
    abstract fun movieDao(movieDataBase: MovieDataBase): MovieDao
    abstract fun imagesDao(): ImagesDao

    companion object{
        @Volatile
        private var instance: MovieDataBase? = null

        fun getDataBase(context: Context): MovieDataBase{
            return instance ?: synchronized(this){
                val dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "movie_data_base"
                )   .fallbackToDestructiveMigration()
                    .build()

                this.instance = dataBase
                return dataBase
            }
        }
    }
}