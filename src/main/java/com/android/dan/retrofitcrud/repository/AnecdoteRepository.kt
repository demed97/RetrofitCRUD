package com.android.dan.retrofitcrud.repository

import android.app.Application
import android.util.Log
import com.android.dan.retrofitcrud.api.Controller
import com.android.dan.retrofitcrud.database.AnecdoteDao
import com.android.dan.retrofitcrud.database.AnecdoteDatabase
import com.android.dan.retrofitcrud.entity.Anecdote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class AnecdoteRepository(application: Application) {

    private var dao: AnecdoteDao

    private var allAnecdotesList = mutableListOf<Anecdote>()

    private val allAnecdotesStateFlow = MutableStateFlow(allAnecdotesList.toList())


    init {
        val database = AnecdoteDatabase.getDatabase(application)!!
        dao = database.getAnecdoteDao()
    }

    private suspend fun addData(anecdoteList: List<Anecdote>) {
        dao.addAnecdotes(anecdoteList)
        allAnecdotesStateFlow.value = anecdoteList.toList()
    }

    suspend fun getAnecdotes(): StateFlow<List<Anecdote>> {
        allAnecdotesList = dao.getAllAnecdotes().toMutableList()
        allAnecdotesStateFlow.value = allAnecdotesList.toList()
        return allAnecdotesStateFlow
    }

    private suspend fun deleteAllData() {
        dao.deleteAllAnecdotes()
    }

    suspend fun loadAndPutInDatabase() {
        Log.w("AnecdoteRepository", "!!!!! loadAndPutInDatabase is run")
//!!!!!!!!!!!!!
        val list = Controller().getApiArguments().getAnecdotes("new anekdot", 10)

        deleteAllData()
        addData(list)
        Log.w("AnecdoteRepository", "!!!!! onResponse, list is: ")
    }
}