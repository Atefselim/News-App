package com.example.newsapp.api

import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("top-headlines/sources")
    fun getSources(

        @Query("apiKey") apikey:String = ApiManager.API_KEY): Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySources(
        @Query("sources") source:String,
        @Query("apiKey") apikey:String = ApiManager.API_KEY): Call<NewsResponse>
}