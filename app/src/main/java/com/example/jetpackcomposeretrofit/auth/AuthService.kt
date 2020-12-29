package com.example.jetpackcomposeretrofit.auth

import com.example.jetpackcomposeretrofit.models.everythingmodel.EverythingModel
import com.example.jetpackcomposeretrofit.models.headlinesmodel.TopHeadlinesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

    @GET("v2/top-headlines")
     suspend fun getHeadlines(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : TopHeadlinesModel


    @GET("v2/everything")
    suspend fun getEverything(
            @Query("q") query : String,
            @Query("apiKey") apiKey : String
    ) : EverythingModel


}