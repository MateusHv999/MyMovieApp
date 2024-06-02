    package com.example.mymovieapp.dao

    import androidx.room.Dao
    import androidx.room.Query
    import androidx.room.Transaction
    import com.example.mymovieapp.database.MovieDataBase
    import com.example.mymovieapp.data.Details
    import com.example.mymovieapp.data.Movie

    @Dao
    abstract class DetailsDao: BaseDao<Details> {
        @Transaction
        @Query("SELECT * FROM details")
        abstract suspend fun getAllDetails() : List<Details>?
        @Transaction
        @Query("SELECT * FROM details WHERE movieId=:movieId")
        abstract suspend fun getDetails(movieId: Int): Details?
        @Transaction
        open suspend fun insertDetails(details: Details){
            insert(details)
        }
        @Transaction
        @Query("DELETE from details")
        abstract suspend fun clearDetailsData()
    }