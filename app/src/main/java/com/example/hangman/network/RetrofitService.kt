package com.example.hangman.network

import com.example.hangman.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private var INSTANCE: Retrofit? = null

    fun getRetrofit(): Retrofit = INSTANCE ?: synchronized(this){
        val instance = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        INSTANCE = instance

        instance
    }
}