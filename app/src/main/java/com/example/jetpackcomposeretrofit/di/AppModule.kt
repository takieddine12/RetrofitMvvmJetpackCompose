package com.example.jetpackcomposeretrofit.di

import android.content.Context
import android.service.autofill.AutofillService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.jetpackcomposeretrofit.R
import com.example.jetpackcomposeretrofit.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofitInstance() : AuthService {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthService::class.java)
    }


//    @Singleton
//    @Provides
//    fun provideGlideInstance(@ApplicationContext context: Context) {
//        Glide.with(context)
//            .setDefaultRequestOptions(RequestOptions()
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .diskCacheStrategy(DiskCacheStrategy.DATA))
//    }
}