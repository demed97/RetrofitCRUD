package com.android.dan.retrofitcrud.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anecdote (
    val site: String,
    val name: String,
    val desc: String,
    val link: String?,
    val elementPureHtml: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)