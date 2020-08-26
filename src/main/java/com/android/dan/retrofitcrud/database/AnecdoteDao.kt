package com.android.dan.retrofitcrud.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.dan.retrofitcrud.entity.Anecdote

@Dao
interface AnecdoteDao {

    @Query("SELECT * FROM anecdote")
    fun getAllAnecdotes(): LiveData<List<Anecdote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAnecdotes(anecdoteList: List<Anecdote>)

    @Query("DELETE FROM anecdote")
    fun deleteAllAnecdotes()
}