package com.example.mymovieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieapp.fragments.MovieDetails
import com.example.mymovieapp.fragments.MovieDetailsFragment
import com.example.mymovieapp.fragments.MovieFragment
import com.example.mymovieapp.responses.DetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MovieViewModel : ViewModel() {
    private val mutableMovieDetailsLD = MutableLiveData<MovieDetailsFragment>()
    private val mutablemovieLD = MutableLiveData<MovieFragment>()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val MovieService = retrofit.create(MovieService::class.java)

    val movieDetailsLiveData : LiveData<MovieDetailsFragment>
        get() = movieDetailsLiveData
    val movieLiveData : LiveData<MovieFragment>
        get() = movieLiveData

    fun loadDetails(): MovieDetails {
        return MovieDetails("Django", "lorem ipslum")
    }
    init {
        getMovieDetails()
    }
    fun getMovieDetails(){
        MovieService.getDetails(151512, "00ffc59cf678282887b43d9e6f6f5d4c")
            .enqueue(object: Callback<DetailsResponse>{
                override fun onResponse(call: Call<DetailsResponse>, response: Response<DetailsResponse>) {
                    if (response.isSuccessful){
                       println(response.body()?.title)
                    }else{
                        println("Error")
                    }
                }

                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                   println("Falhou em algo: ${t.message}")
                }
            })
    }
}