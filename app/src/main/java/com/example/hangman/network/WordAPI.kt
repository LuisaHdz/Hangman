package com.example.hangman.network

import com.example.hangman.model.Word
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
interface WordAPI {
    @GET
    fun getWord(
        @Url url: String?
    ): Call<Word>
}