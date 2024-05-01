package com.example.mymovieapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    suspend fun insert(entity: T)
    @Insert
    suspend fun insertList(entities: kotlin.collections.List<com.example.mymovieapp.data.Image>)
    @Update
    suspend fun update(entity: T)
    @Update
    suspend fun updateList(entities: List<T>)
    @Delete
    suspend fun delete(entity: T)
    @Delete
    suspend fun deleteList(entities: List<T>)
}