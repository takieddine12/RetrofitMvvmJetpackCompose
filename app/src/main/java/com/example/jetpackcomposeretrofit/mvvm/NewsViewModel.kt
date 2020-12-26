package com.example.jetpackcomposeretrofit.mvvm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.viewinterop.viewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeretrofit.models.TopHeadlinesModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel @ViewModelInject constructor(
    var repository: NewsRepository
) : ViewModel(){


     var states : MutableState<TopHeadlinesModel>? = mutableStateOf(
        TopHeadlinesModel(emptyList())
    )

    fun getHeadlines(country : String , apikey : String){
       viewModelScope.launch {
           try {
               repository.getTopHeadlines(country,apikey).collect {
                   states?.value  = it
               }
           }catch (ex : Exception){
               Log.d("TAG","Error ${ex.message}")
           }
       }
    }
}