package com.example.jetpackcomposeretrofit.mvvm
import com.example.jetpackcomposeretrofit.models.everythingmodel.EverythingModel
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeretrofit.models.headlinesmodel.TopHeadlinesModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel @ViewModelInject constructor(
    var repository: NewsRepository
) : ViewModel(){


     var topHeadlinesState : MutableState<TopHeadlinesModel>? = mutableStateOf(
        TopHeadlinesModel(emptyList()))
     var everythingState : MutableState<EverythingModel>? = mutableStateOf(
             EverythingModel(emptyList()))

    fun getHeadlines(country : String , apikey : String){
       viewModelScope.launch {
           try {
               repository.getTopHeadlines(country,apikey).collect {
                   topHeadlinesState?.value  = it
               }
           }catch (ex : Exception){
               Log.d("TAG","Error ${ex.message}")
           }
       }
    }
    fun getEverything(query : String , apikey: String){
        viewModelScope.launch {
            try {
                repository.getEverything(query,apikey).collect {
                    everythingState?.value = it
                }
            }catch (ex : Exception){
                Log.d("TAG","Error ${ex.message}")
            }
        }
    }
}