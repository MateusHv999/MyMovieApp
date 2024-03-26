package com.example.mymovieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieapp.responses.DetailsResponse
import com.example.mymovieapp.responses.ImageResponse
import com.example.mymovieapp.responses.Image
import com.example.mymovieapp.responses.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.mymovieapp.responses.MovieResponse


class MovieViewModel : ViewModel() {
    private val _movieDetailsLivedata = MutableLiveData <DetailsResponse?>()
    private val _movieListLivedata = MutableLiveData<List<Movie>?>()
    private val _imageLiveData = MutableLiveData<ImageResponse>()
    private val _navigationToDetailLiveData = MutableLiveData<Unit>()
    private val _appState= MutableLiveData<States>()

    val retrofit = Retrofit.Builder()
        .baseUrl(Credentials.baseurl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
   private val MovieService = retrofit.create(MovieService::class.java)

    val movieDetailsLiveData : LiveData<DetailsResponse?>
        get() = _movieDetailsLivedata
    val movieListLivedata : LiveData<List<Movie>?>
        get() = _movieListLivedata
    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    val imageLiveData
        get() = _imageLiveData
    val appState :  LiveData<States>
        get() = _appState

    fun onMovieSelected(position: Int) {
        _navigationToDetailLiveData.postValue(Unit)
    }
    init {
        _appState.postValue(States.LOADING)
        getMovie()
    }

  private fun getMovie(){
        MovieService.getMovie(Credentials.apikey).enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                        _movieListLivedata.postValue(response.body()?.results)
                        val movieId = response.body()?.results?.first()?.id
                        getDetails(movieId)
                        getImage(movieId)
                        _appState.postValue(States.SUCCESS)
                    }else{
                        _appState.postValue(States.ERROR)
                    }
                }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                println("Falha ao carregar o filme: ${t.message}")
                _appState.postValue(States.ERROR)
            }
        })
    }
    private fun getDetails(movieId: Int?){
        MovieService.getDetails(movieId, Credentials.apikey).enqueue(object: Callback<DetailsResponse>{
            override fun onResponse(
                call: Call<DetailsResponse>,
                response: Response<DetailsResponse>
            ) {
               if(response.isSuccessful){
                   _movieDetailsLivedata.postValue(response.body())
               }else{
                   _appState.postValue(States.ERROR)
               }
            }

            override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                println("Falha ao carregar os detalhes ${t.message}")
                _appState.postValue(States.ERROR)
            }

        })
    }
    private fun getImage(movieId: Int?){
        MovieService.getImage(movieId, Credentials.apikey).enqueue(object: Callback<ImageResponse>{
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if(response.isSuccessful){
                    _imageLiveData.postValue(response.body())
                }else {
                    _appState.postValue(States.ERROR)
                }
            }
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                println("Falha ao carregar as imagens ${t.message}")
                _appState.postValue(States.ERROR)
            }

        })
    }

}