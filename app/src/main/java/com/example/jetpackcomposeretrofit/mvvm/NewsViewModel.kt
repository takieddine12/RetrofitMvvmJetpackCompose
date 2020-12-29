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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class NewsViewModel @ViewModelInject constructor(
    var repository: NewsRepository
) : ViewModel() {


     private val topHeadlinesState: MutableState<TopHeadlinesModel> = mutableStateOf(
        TopHeadlinesModel(listOf())
    )
     private val everythingState: MutableState<EverythingModel> = mutableStateOf(
        EverythingModel(listOf())
    )


    fun getHeadlines(country : String , apikey : String) : MutableState<TopHeadlinesModel>?{
       viewModelScope.launch {
           repository.getTopHeadlines(country,apikey).collect {
              try {
                  topHeadlinesState.value = it
                  Timber.d("Headlines Title ${it.articles[0].title}")
              }catch (ex : Exception){
                  Timber.d("Headlines Error ${ex.message}")
              }
           }
       }
        return topHeadlinesState
    }
    fun getEverything(query : String , apikey: String) : MutableState<EverythingModel>?{
        viewModelScope.launch {
            repository.getEverything(query,apikey).collect {
                try {
                    everythingState.value  = it
                    Timber.d("Everything Title ${it.articles[0].title}")
                }catch (ex :Exception){
                    Timber.d("Everything Error ${ex.message}")
                }
            }
        }
        return everythingState
    }
}