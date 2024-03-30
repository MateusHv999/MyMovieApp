package com.example.mymovieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.fragments.Events
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
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movieDetailsLivedata = MutableLiveData <DetailsResponse?>()
    private val _movieListLivedata = MutableLiveData<List<Movie>?>()
    private val _imageLiveData = MutableLiveData<ImageResponse>()
    private val _navigationToDetailLiveData = MutableLiveData<Events<Unit>>()
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
        _navigationToDetailLiveData.postValue(Events(Unit))
    }
    init {
        _appState.postValue(States.LOADING)
        getMovie()
    }

  private fun getMovie(){
      viewModelScope.launch{
         val request = MovieService.getMovie(Credentials.apikey)

          if(request.isSuccessful){
              _movieListLivedata.postValue(request.body()?.results)
              val movieId = request.body()?.results?.first()?.id
              getDetails(movieId)
              getImage(movieId)
              _appState.postValue(States.SUCCESS)
          }else{
              _appState.postValue(States.ERROR)
          }
      }
  }
    private fun getDetails(movieId: Int?){
        viewModelScope.launch{
            val request = MovieService.getDetails(movieId, Credentials.apikey)
            if(request.isSuccessful){
                _movieDetailsLivedata.postValue(request.body())
            }else{
                _appState.postValue(States.ERROR)
            }
        }
    }
    private fun getImage(movieId: Int?){
        viewModelScope.launch {
            val request = MovieService.getImage(movieId, Credentials.apikey)
            if(request.isSuccessful){
                _imageLiveData.postValue(request.body())
            }else {
                _appState.postValue(States.ERROR)
            }
        }
    }
}