package com.example.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 object ApiManager {

    val API_KEY = "dd07a79818c441608fd2a1094fa559a6"
     private val httpLoggingInterceptor = HttpLoggingInterceptor{message->

         Log.e("Api",message )
     }
     private val okHttpClient = OkHttpClient.Builder()
         .addInterceptor(httpLoggingInterceptor)
         .build()
   private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val newsServices : NewsServices = retrofit.create(NewsServices::class.java)
}