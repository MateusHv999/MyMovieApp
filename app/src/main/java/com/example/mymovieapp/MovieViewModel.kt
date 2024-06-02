package com.example.mymovieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie
import com.example.mymovieapp.helper.Events
import com.example.mymovieapp.helper.States
import com.example.mymovieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    val movieRepository : MovieRepository
): ViewModel(){
    private val _movieDetailsLivedata = MutableLiveData<Details?>()
    private val _movieListLivedata = MutableLiveData<List<Movie>?>()
    private val _imageLiveData = MutableLiveData<ImageResponse?>()
    private val _navigationToDetailLiveData = MutableLiveData<Events<Unit>>()
    private val _appState = MutableLiveData<States>()
    val movieDetailsLiveData: LiveData<Details?>
        get() = _movieDetailsLivedata

    val movieListLivedata: LiveData<List<Movie>?>
        get() = _movieListLivedata

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    val imageLiveData
        get() = _imageLiveData

    val appState: LiveData<States>
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
    // Resultados do repositorio
    @VisibleForTesting
    fun getMovie() {
        _appState.postValue(States.LOADING)
        viewModelScope.launch {
            val movieListResult = movieRepository.getMovieData()
            movieListResult.fold(
                onSuccess = {
                    _movieListLivedata.value = it
                    _appState.value = States.SUCCESS
                },
                onFailure = { _appState.value = States.ERROR }
            )
        }
    }
    @VisibleForTesting
    fun getDetails(id: Int) {
        _appState.postValue(States.LOADING)
        viewModelScope.launch {
            val detailListResult = movieRepository.getDetailsData(id)
            detailListResult.fold(
                onSuccess = {
                    _movieDetailsLivedata.value = it
                    _appState.value = States.SUCCESS
                },
                onFailure = { _appState.value = States.ERROR }
            )
        }
    }
    @VisibleForTesting
    fun getImage(id: Int) {
        _appState.postValue(States.LOADING)
        viewModelScope.launch {
            val imageListResult = movieRepository.getImageData(id)
            imageListResult.fold(
                onSuccess = {
                    _imageLiveData.value = it
                    _appState.value = States.SUCCESS
                },
                onFailure = { _appState.value = States.ERROR }
            )
        }
    }
}
