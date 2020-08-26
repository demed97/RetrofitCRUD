package com.android.dan.retrofitcrud.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.dan.retrofitcrud.entity.Anecdote
import com.android.dan.retrofitcrud.repository.AnecdoteRepository

class AnecdoteViewModel(private val repository: AnecdoteRepository) : ViewModel() {

    var isConnected : ObservableBoolean = ObservableBoolean(false)

    fun showAnecdotes() {
        loadAnecdotesFromServer()
        Log.w("AnecdoteViewModel","!!!!! showAnecdotes is run")
    }

    private fun loadAnecdotesFromServer(){
        repository.loadAndPutInDatabase()
        Log.w("AnecdoteViewModel","!!!!! loadAnecdotesFromServer is run")
    }

    fun getAllAnecdotes() : LiveData<List<Anecdote>> {
        return repository.getAnecdotes()
        Log.w("AnecdoteViewModel","!!!!! getAllAnecdotes is run")
    }

}