package com.example.jetpackcomposeretrofit.di

import android.service.autofill.AutofillService
import com.example.jetpackcomposeretrofit.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideRetrofitInstance() : AuthService {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthService::class.java)
    }
}