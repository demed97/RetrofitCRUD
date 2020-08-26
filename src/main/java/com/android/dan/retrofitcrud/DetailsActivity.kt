package com.android.dan.retrofitcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.dan.retrofitcrud.entity.Anecdote
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val anecdote = intent.extras?.get("anecdote") as Anecdote
        anecdoteTV.text = anecdote.elementPureHtml
    }
}