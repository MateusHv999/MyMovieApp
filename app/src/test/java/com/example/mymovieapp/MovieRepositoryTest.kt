package com.example.mymovieapp

import com.example.mymovieapp.data.Details
import com.example.mymovieapp.data.ImageResponse
import com.example.mymovieapp.data.Movie
import com.example.mymovieapp.datasource.MovieApiClientDataSource
import com.example.mymovieapp.datasource.MovieDatabaseDataSource
import com.example.mymovieapp.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.verifySequence
import net.bytebuddy.implementation.bytecode.Throw

class MovieRepositoryTest {
    @get:Rule
    val mocKkRule = MockKRule(this)

    val MovieApiClientDataSource : MovieApiClientDataSource = mockk()
    val MovieDatabaseDataSource : MovieDatabaseDataSource = mockk()

    val repository = MovieRepository(MovieApiClientDataSource, MovieDatabaseDataSource)

    val movieList = listOf(Movie())
    val details = Details()
    val image = ImageResponse()

    @Before
    fun setUp() {
        coEvery {
            MovieDatabaseDataSource.clearMovieData()
            MovieDatabaseDataSource.clearDetailsData()
            MovieDatabaseDataSource.clearImagesData()
        }returns Unit
        coEvery {
            MovieDatabaseDataSource.saveMovieData(any())
            MovieDatabaseDataSource.saveDetailsData(any(), any())
            MovieDatabaseDataSource.saveImageData(any(), any())
        }returns Unit
    }

    //Api Success

    @Test
    fun getMovieData_whenApiHadSuccess_shouldPersistDataAndReturnList() = runBlocking{
        //Configuration
        val apiResponse = Result.success(movieList)
        coEvery { MovieApiClientDataSource.getMovieData() } returns apiResponse

        //Execution
       val result = repository.getMovieData()

        //Verify
        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            MovieApiClientDataSource.getMovieData()
            MovieDatabaseDataSource.clearMovieData()
            MovieDatabaseDataSource.saveMovieData(movieList)
        }
    }
    @Test
    fun getDetailsData_whenApiHadSuccess_shouldPersistDataAndReturnList() = runBlocking{
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.success(details)
        coEvery { MovieApiClientDataSource.getMovieDetails(id) } returns apiResponse

        //Execution
        val result = repository.getDetailsData(id)

        //Verify
        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            MovieApiClientDataSource.getMovieDetails(id)
            MovieDatabaseDataSource.clearDetailsData()
            MovieDatabaseDataSource.saveDetailsData(details, id)
        }
    }
    @Test
    fun getImageData_whenApiHadSuccess_shouldPersistDataAndReturnList() = runBlocking{
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.success(image)
        coEvery { MovieApiClientDataSource.getMovieImage(id) } returns apiResponse

        //Execution
        val result = repository.getImageData(id)

        //Verify
        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            MovieApiClientDataSource.getMovieImage(id)
            MovieDatabaseDataSource.clearImagesData()
            MovieDatabaseDataSource.saveImageData(image, id)
        }
    }

    //Api Fail

    @Test
    fun getMovieData_whenApiFailed_shouldLoadLocalData() = runBlocking {
        //Configuration
        val apiResponse = Result.failure<List<Movie>>(Throwable("test"))
        val localResponse = Result.success(movieList)
        coEvery { MovieApiClientDataSource.getMovieData() } returns apiResponse
        coEvery { MovieDatabaseDataSource.getMovieData() } returns localResponse

        //Execution
        val result = repository.getMovieData()

        //Verify
       coVerify(exactly = 0){
           MovieDatabaseDataSource.clearMovieData()
           MovieDatabaseDataSource.saveMovieData(any())
       }
        coVerify(exactly = 1) {
           MovieDatabaseDataSource.getMovieData()
        }
        assertThat(result).isEqualTo(localResponse)
    }
    @Test
    fun getDetailsData_whenApiFailed_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.failure<Details>(Throwable("test"))
        val localResponse = Result.success(details)
        coEvery { MovieApiClientDataSource.getMovieDetails(id) } returns apiResponse
        coEvery { MovieDatabaseDataSource.getMovieDetails(id) } returns localResponse

        //Execution
        val result = repository.getDetailsData(id)

        //Verify
        coVerify(exactly = 0) {
            MovieDatabaseDataSource.clearDetailsData()
            MovieDatabaseDataSource.saveDetailsData(details, id)
        }
        coVerify(exactly = 1) {
            MovieDatabaseDataSource.getMovieDetails(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }
    @Test
    fun getImageData_whenApiFailed_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.failure<ImageResponse>(Throwable("test"))
        val localResponse = Result.success(image)
        coEvery { MovieApiClientDataSource.getMovieImage(id) } returns apiResponse
        coEvery { MovieDatabaseDataSource.getMovieImage(id) } returns localResponse

        //Execution
        val result = repository.getImageData(id)

        //Verify
        coVerify(exactly = 0) {
            MovieDatabaseDataSource.clearImagesData()
            MovieDatabaseDataSource.saveImageData(image, id)
        }
        coVerify(exactly = 1) {
            MovieDatabaseDataSource.getMovieImage(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }

    //Api Exception

    @Test
    fun getMovieData_whenExceptionOccurs_shouldLoadLocalData() = runBlocking {
        //Configuration
        val localResponse = Result.success(movieList)
        coEvery { MovieApiClientDataSource.getMovieData() }.throws(Exception("test"))
        coEvery { MovieDatabaseDataSource.getMovieData() } returns localResponse

        //Execution
        val result = repository.getMovieData()

        //Verify
        coVerify(exactly = 0) {
            MovieDatabaseDataSource.clearMovieData()
            MovieDatabaseDataSource.saveMovieData(any())
        }
        coVerify(exactly = 1) {
            MovieDatabaseDataSource.getMovieData()
        }
        assertThat(result).isEqualTo(localResponse)
    }

    @Test
    fun getDetailsData_whenExceptionOccurs_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val localResponse = Result.success(details)
        coEvery { MovieApiClientDataSource.getMovieDetails(id) }.throws(Exception("test"))
        coEvery { MovieDatabaseDataSource.getMovieDetails(id) } returns localResponse

        //Execution
        val result = repository.getDetailsData(id)

        //Verify
        coVerify(exactly = 0) {
            MovieDatabaseDataSource.clearDetailsData()
            MovieDatabaseDataSource.saveDetailsData(details, id)
        }
        coVerify(exactly = 1) {
            MovieDatabaseDataSource.getMovieDetails(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }
    @Test
    fun getImageData_whenExceptionOccurs_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val localResponse = Result.success(image)
        coEvery { MovieApiClientDataSource.getMovieImage(id) }.throws(Exception("test"))
        coEvery { MovieDatabaseDataSource.getMovieImage(id) } returns localResponse

        //Execution
        val result = repository.getImageData(id)

        //Verify
        coVerify(exactly = 0) {
            MovieDatabaseDataSource.clearImagesData()
            MovieDatabaseDataSource.saveImageData(image, id)
        }
        coVerify(exactly = 1) {
            MovieDatabaseDataSource.getMovieImage(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }
}