package com.example.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.dao.DetailsDao
import com.example.mymovieapp.dao.ImageListDao
import com.example.mymovieapp.dao.ImagesDao
import com.example.mymovieapp.dao.MovieDao
import com.example.mymovieapp.database.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context):MovieDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDataBase::class.java,
            "movie_data_base"
        )   .fallbackToDestructiveMigration()
            .build()
    @Provides
    fun provideMovieDao(movieDb: MovieDataBase): MovieDao =
        movieDb.movieDao(movieDb)
    @Provides
    fun provideoImageDao(movieDb: MovieDataBase): ImagesDao =
        movieDb.imagesDao()
    @Provides
    fun provideDetailsDao(movieDb: MovieDataBase): DetailsDao =
        movieDb.detailsDao(movieDb)
    @Provides
    fun provideImageListDao(movieDb: MovieDataBase): ImageListDao =
        movieDb.imageListDao(movieDb)
}