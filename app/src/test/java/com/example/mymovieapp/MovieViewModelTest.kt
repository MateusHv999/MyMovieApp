package com.example.mymovieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie
import com.example.mymovieapp.datasource.MovieDatabaseDataSource
import com.example.mymovieapp.helper.DataState
import com.example.mymovieapp.helper.States
import com.example.mymovieapp.repository.MovieRepository
import io.mockk.MockK
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat


@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    val dispatcher = UnconfinedTestDispatcher()

    val movieRepository: MovieRepository = mockk()
    val appStateObserver: Observer<States> = mockk(relaxed = true)
    val appStateValues = mutableListOf<States>()

    lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun {
            appStateObserver.onChanged(capture(appStateValues))
        }

        coEvery {
            movieRepository.getMovieData()
            movieRepository.getDetailsData(any())
            movieRepository.getImageData(any())
        } returns Result.failure(Throwable("test"))



        movieViewModel = MovieViewModel(movieRepository)

        movieViewModel.appState.observeForever(appStateObserver)
        appStateValues.clear()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        movieViewModel.appState.removeObserver(appStateObserver)
        appStateValues.clear()
    }

    //API Data States When Has Success

    @Test
    fun getMovieData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        //Configuration
        coEvery { movieRepository.getMovieData() } returns Result.success(listOf(Movie()))

        //Execution
        movieViewModel.getMovie()

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.SUCCESS))
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.success(Details())

        //Execution
        movieViewModel.getDetails(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.SUCCESS))
    }
    @Test
    fun getImageData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.success(ImageResponse())

        //Execution
        movieViewModel.getImage(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.SUCCESS))
    }

    //API Data States When Has Failure

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        //Configuration
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("test"))

        //Execution
        movieViewModel.getMovie()

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.ERROR))
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.failure(Throwable("test"))

        //Execution
        movieViewModel.getDetails(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.ERROR))
    }
    @Test
    fun getImageData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.failure(Throwable("test"))

        //Execution
        movieViewModel.getImage(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.ERROR))
    }

    //API Check Data And Emit

    @Test
    fun getMovieData_whenMovieRepository_hasData_shouldChangeEmitList() = runBlocking {
        //Configuration
        val list = listOf(Movie())
        coEvery { movieRepository.getMovieData() } returns Result.success(list)

        //Execution
        movieViewModel.getMovie()

        //Verify
        assertThat(movieViewModel.movieListLivedata.value).isEqualTo(list)
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasData_shouldChangeEmit() = runBlocking {
        //Configuration
        val details = Details()
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.success(details)

        //Execution
        movieViewModel.getDetails(id)

        //Verify
        assertThat(movieViewModel.movieDetailsLiveData.value).isEqualTo(details)
    }
    @Test
    fun getImagesData_whenMovieRepository_hasData_shouldChangeEmit() = runBlocking {
        //Configuration
        val image = ImageResponse()
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.success(image)

        //Execution
        movieViewModel.getImage(id)

        //Verify
        assertThat(movieViewModel.imageLiveData.value).isEqualTo(image)
    }

    //API Check Data And Don't Emit When Has Error

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldNotEmitList() = runBlocking {
        //Configuration
        val list = listOf(Movie())
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("test"))

        //Execution
        movieViewModel.getMovie()

        //Verify
        assertThat(movieViewModel.movieListLivedata.value).isNull()
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasError_shouldNotEmit() = runBlocking {
        //Configuration
        val details = Details()
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.failure(Throwable("test"))

        //Execution
        movieViewModel.getDetails(id)

        //Verify
        assertThat(movieViewModel.movieDetailsLiveData.value).isNull()
    }
    @Test
    fun getImagesData_whenMovieRepository_hasError_shouldNotEmit() = runBlocking {
        //Configuration
        val image = ImageResponse()
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.failure(Throwable("test"))

        //Execution
        movieViewModel.getImage(id)

        //Verify
        assertThat(movieViewModel.imageLiveData.value).isNull()
    }
}
