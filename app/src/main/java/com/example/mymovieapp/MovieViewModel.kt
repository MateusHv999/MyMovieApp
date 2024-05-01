package com.example.mymovieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.api.Credentials
import com.example.mymovieapp.database.MovieDataBase
import com.example.mymovieapp.helper.Events
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.ImagesWithAllProperties
import com.example.mymovieapp.data.Movie
import com.example.mymovieapp.helper.States
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDB = MovieDataBase.getDataBase(application)
    private val movieDao = movieDB.movieDao(movieDB)
    private val imageListDao = movieDB.imageListDao(movieDB)
    private val detailsDao = movieDB.detailsDao(movieDB)
    private val _movieDetailsLivedata = MutableLiveData <Details?>()
    private val _movieListLivedata = MutableLiveData<List<Movie>?>()
    private val _imageLiveData = MutableLiveData<ImageResponse?>()
    private val _navigationToDetailLiveData = MutableLiveData<Events<Unit>>()
    private val _appState = MutableLiveData<States>()

    val retrofit = Retrofit.Builder()
        .baseUrl(Credentials.baseurl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
   private val MovieService = retrofit.create(com.example.mymovieapp.api.MovieService::class.java)

    val movieDetailsLiveData : LiveData<Details?>
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
        val movieID = _movieListLivedata.value?.get(position)?.id
        movieID?.let {
            getDetails(it)
            getImage(it)
        }
        _navigationToDetailLiveData.postValue(Events(Unit))
    }
    init {
        _appState.postValue(States.LOADING)
        getMovie()
    }

// API Responses
  private fun getMovie() {
    viewModelScope.launch {
        try {
            val request = MovieService.getMovie(Credentials.apikey)
            if (request.isSuccessful) {
                val movies = request.body()?.results
                movies?.let {
                    persistMovieData(it)
                }
                _movieListLivedata.postValue(movies)
                _appState.postValue(States.SUCCESS)
            } else {
                errorMovieHandler()
            }
        } catch (e: Exception) {
            errorMovieHandler()
        }
    }
}
    private fun getDetails(movieId: Int){
        viewModelScope.launch {
            try {
                val request = MovieService.getDetails(movieId, Credentials.apikey)
                if (request.isSuccessful) {
                    val details = request.body()
                    details?.let {
                        persistDetailsData(details, movieId)
                    }
                    _movieDetailsLivedata.postValue(request.body())
                } else {
                    errorDetailsHandler(movieId)
                }
            }catch (e: Exception){
                errorDetailsHandler(movieId)
            }
        }
    }
    private fun getImage(movieId: Int) {
            viewModelScope.launch {
                try {
                val request = MovieService.getImage(movieId, Credentials.apikey)
                if (request.isSuccessful) {
                    val images = request.body()
                    images?.let {
                        persistImageData(images, movieId)
                    }
                    _imageLiveData.postValue(images)
                } else {
                    errorImageHandler(movieId)
                }
            }catch (e: Exception){
                errorImageHandler(movieId)
            }
        }
    }

    //Persist
    private suspend fun persistMovieData(movieList: List<Movie>){
        movieDao.clearMoviesData()
        movieDao.insertMovieList(movieList)
    }
    //Especifico meu ID para associar ao filme correto.
    private suspend fun persistImageData(image: ImageResponse, movieId: Int){
        image.movieId = movieId
        imageListDao.insertImage(image)
    }
    //Faço a atribuição dos valores da entity junto do movieID
    private suspend fun persistDetailsData(details: Details, movieId: Int){
       val detailsData = Details()
        detailsData.title = details.title
        detailsData.overview = details.overview
        detailsData.poster_path = details.poster_path
        detailsData.movieId = movieId
        detailsDao.insertDetails(detailsData)
    }
    //Mapeio as propriedades da imagem para associar a minha instância da lista de imagens
    private fun mapImagesWithPropertiesToImages(imageWithAllProperties: ImagesWithAllProperties) : ImageResponse{
        imageWithAllProperties.images.backdrops = imageWithAllProperties.backdrops
        return imageWithAllProperties.images
    }
    //Usando o mapeamento com o formato correto carrego minhas imagens
    private suspend fun loadPersistedImageData(movieId: Int): ImageResponse?{
        val mapImagesWithPropertiesToImages = imageListDao.getImage(movieId)
        return mapImagesWithPropertiesToImages?.let { mapImagesWithPropertiesToImages(it) }
    }
    //Handlers caso ocorra algum erro
    private suspend fun errorMovieHandler(){
        val moviesList = movieDao.getAllMovies()
        if(moviesList.isNullOrEmpty()){
            _appState.postValue(States.ERROR)
        }else{
            _movieListLivedata.postValue(moviesList)
            _appState.postValue(States.SUCCESS)
        }

    }
    private suspend fun errorDetailsHandler(movieId: Int){
        val details = detailsDao.getDetails(movieId)
        if(details == null){
            _appState.postValue(States.ERROR)
        }else{
            _movieDetailsLivedata.postValue(details)
            _appState.postValue(States.SUCCESS)
        }
    }

    private suspend fun errorImageHandler(movieId: Int){
        val image = loadPersistedImageData(movieId)
        if(image == null){
            _appState.postValue(States.ERROR)
        }else{
            _imageLiveData.postValue(image)
            _appState.postValue(States.SUCCESS)
        }
    }
}