package com.android.dan.retrofitcrud.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.dan.retrofitcrud.repository.AnecdoteRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: AnecdoteRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass){
            AnecdoteViewModel::class.java -> AnecdoteViewModel(repository)
            else -> throw IllegalArgumentException("ViewModel Not Found")
        } as T
    }
}