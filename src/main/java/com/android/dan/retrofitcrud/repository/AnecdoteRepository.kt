package com.android.dan.retrofitcrud.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.android.dan.retrofitcrud.api.Controller
import com.android.dan.retrofitcrud.database.AnecdoteDao
import com.android.dan.retrofitcrud.database.AnecdoteDatabase
import com.android.dan.retrofitcrud.entity.Anecdote
import retrofit2.Call
import retrofit2.Response

class AnecdoteRepository(application: Application) {

    private var dao: AnecdoteDao

    init {
        val database = AnecdoteDatabase.getDatabase(application)!!
        dao = database.getAnecdoteDao()
    }

    companion object {
        class AddDataAsync(private val anecdoteDao: AnecdoteDao) :
            AsyncTask<List<Anecdote>, Unit, Unit>() {
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            override fun doInBackground(vararg p0: List<Anecdote>?) {
                anecdoteDao.addAnecdotes(p0[0]!!)
                Log.w("AnekdotRepository", "!!!!! addDataAsync is run")
            }
        }

        class GetAllDataAsync(private val anecdoteDao: AnecdoteDao) :
            AsyncTask<Unit, Unit, LiveData<List<Anecdote>>>() {
            override fun doInBackground(vararg p0: Unit?): LiveData<List<Anecdote>> {
                return anecdoteDao.getAllAnecdotes()
                Log.w("AnecdoteRepository", "!!!!! getAllDataAsync is run")
            }
        }

        class DeleteAllDataAsync(private val anecdoteDao: AnecdoteDao) :
            AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                anecdoteDao.deleteAllAnecdotes()
            }
        }
    }

    fun loadAndPutInDatabase(){
        Log.w("AnecdoteRepository","!!!!! loadAndPutInDatabase is run")
//!!!!!!!!!!!!!
        Controller().getApiArguments().getAnecdotes("new anekdot",10)
            .enqueue(object : retrofit2.Callback<List<Anecdote>> {
                override fun onFailure(call: Call<List<Anecdote>>, t: Throwable) {
                    Log.e("AnecdoteRepository","!!!!! onFailure",t)

                }

                override fun onResponse(
                    call: Call<List<Anecdote>>,
                    response: Response<List<Anecdote>>
                ) {
                    DeleteAllDataAsync(dao).execute()
                    AddDataAsync(dao).execute(response.body())

                    Log.w("AnecdoteRepository","!!!!! onResponse, list is: ${response.body()}")
                }
            })
    }

    fun getAnecdotes() : LiveData<List<Anecdote>>{
        return GetAllDataAsync(dao).execute().get()
    }
}