package com.example.hangman.model


import com.google.gson.annotations.SerializedName


data class Word(
    @SerializedName("word")
    var word: String?,
    @SerializedName("category")
    var category: String?
)