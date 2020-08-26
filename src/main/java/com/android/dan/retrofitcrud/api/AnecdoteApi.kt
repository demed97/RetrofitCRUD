package com.android.dan.retrofitcrud.api

import com.android.dan.retrofitcrud.entity.Anecdote
import retrofit2.http.GET
import retrofit2.http.Query

interface AnecdoteApi {

    @GET("/api/get")
    suspend fun getAnecdotes(
        @Query("name") name: String,
        @Query("num") num: Int
    ): List<Anecdote>
}