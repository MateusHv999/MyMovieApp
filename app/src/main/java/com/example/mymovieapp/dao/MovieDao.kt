package com.example.mymovieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.mymovieapp.database.MovieDataBase
import com.example.mymovieapp.data.Movie

@Dao
abstract class MovieDao(movieDatabase: MovieDataBase): BaseDao<Movie> {
    @Transaction
    @Query("SELECT * FROM movie")
    abstract suspend fun getAllMovies(): List<Movie>?

    @Transaction
    @Query("SELECT * FROM movie WHERE id=:movieId")
    abstract suspend fun getMovie(movieId: Int): Movie?
    @Transaction
    open suspend fun insertMovie(movie: Movie){
         insert(movie)
    }
    @Transaction
    @Query("DELETE from movie")
    abstract suspend fun clearMoviesData()

    @Transaction
    open suspend fun insertMovieList(movieList: List<Movie>){
        movieList.forEach{insertMovie(it)}
    }
}