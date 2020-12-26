package com.example.jetpackcomposeretrofit.auth

import com.example.jetpackcomposeretrofit.models.TopHeadlinesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : TopHeadlinesModel


}