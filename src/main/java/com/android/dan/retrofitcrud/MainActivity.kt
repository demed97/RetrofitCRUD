package com.android.dan.retrofitcrud

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.dan.retrofitcrud.adapter.AnecdoteAdapter
import com.android.dan.retrofitcrud.databinding.ActivityMainBinding
import com.android.dan.retrofitcrud.repository.AnecdoteRepository
import com.android.dan.retrofitcrud.viewmodel.AnecdoteViewModel
import com.android.dan.retrofitcrud.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = AnecdoteAdapter(listOf())
        val viewModel =
            ViewModelProvider(this, ViewModelFactory(AnecdoteRepository(application))).get(
                AnecdoteViewModel::class.java
            )
        val mainBinding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewModel.getAllAnecdotes().observe(this, Observer {
            adapter.setAnecdoteList(it)
            println("!!!!! OBSERVER GET LIST: $it")
        })
        mainBinding.adapter = adapter
        mainBinding.viewModel = viewModel

        checkConnection(viewModel)
    }

    private fun checkConnection(viewModel : AnecdoteViewModel){
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()
        var callback = object : ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                viewModel.isConnected.set(true)

                Toast.makeText(baseContext,"Network is available", Toast.LENGTH_LONG).show()
            }

            override fun onLost(network: Network) {
                viewModel.isConnected.set(false)

                Toast.makeText(baseContext,"Network was lost", Toast.LENGTH_LONG).show()
            }
        }
//!!!!!!!
        try {
            manager.unregisterNetworkCallback(callback)
        }catch (e: Exception){
            Log.w("MainActivity","NetworkCallback for Wi-fi was not registered or already unregistered")
        }

        manager.registerNetworkCallback(networkRequest,callback)
    }
}