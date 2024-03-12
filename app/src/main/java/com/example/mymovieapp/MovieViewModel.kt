package com.example.mymovieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieapp.fragments.MovieDetails
import com.example.mymovieapp.fragments.MovieDetailsFragment
import com.example.mymovieapp.fragments.MovieFragment
import com.example.mymovieapp.responses.DetailsResponse
import com.example.mymovieapp.responses.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.mymovieapp.Credentials


class MovieViewModel : ViewModel() {
    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()
    private val _movieListLivedata = MutableLiveData<List<Movie>?>()
    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

    val retrofit = Retrofit.Builder()
        .baseUrl(Credentials.baseurl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val MovieService = retrofit.create(MovieService::class.java)

    val movieDetailsLiveData : LiveData<MovieDetails>
        get() = _movieDetailsLiveData
    val movieListLivedata : LiveData<List<Movie>?>
        get() = _movieListLivedata
    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    fun onMovieSelected(position: Int) {
        val movieDetails = MovieDetails("Minha HQ", "Este é apenas texto")
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }

    init {
        getMovie()
    }

    fun getMovie(){
        MovieService.getMovie(Credentials.apikey).enqueue(object: Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    val movie = response.body()
                    if(movie !=null) {
                        _movieListLivedata.postValue(listOf(movie))
                    }else{
                        _movieListLivedata.postValue(emptyList())
                    }
                }
            }
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                println("Failed to fetch movie: ${t.message}")
            }

        })
    }

}