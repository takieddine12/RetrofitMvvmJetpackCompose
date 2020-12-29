package com.example.jetpackcomposeretrofit.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomposeretrofit.auth.AuthService
import com.example.jetpackcomposeretrofit.models.everythingmodel.EverythingModel
import com.example.jetpackcomposeretrofit.models.headlinesmodel.TopHeadlinesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    var authService: AuthService
) {

    fun getTopHeadlines(country : String , apiKey: String)  : Flow<TopHeadlinesModel> {
        return flow {
            emit(authService.getHeadlines(country = country,apiKey = apiKey))
        }
    }
    fun getEverything(query : String , apiKey : String) : Flow<EverythingModel>{
        return flow {
            emit(authService.getEverything(query,apiKey))
        }
    }
}