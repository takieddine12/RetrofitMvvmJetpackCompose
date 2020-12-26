package com.example.jetpackcomposeretrofit.mvvm

import com.example.jetpackcomposeretrofit.auth.AuthService
import com.example.jetpackcomposeretrofit.models.TopHeadlinesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    var authService: AuthService
) {

    // TODO : Using Flow Api To get data

    fun getTopHeadlines(country : String , apiKey : String) : Flow<TopHeadlinesModel> {
        return flow {
            emit(authService.getHeadlines(country,apiKey))
        }
    }
}