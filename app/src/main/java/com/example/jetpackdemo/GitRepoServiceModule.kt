package com.example.jetpackdemo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class GitRepoServiceModule {

    private val mApiBaseUrl = "https://api.github.com/"


    @Provides
    @Singleton
    fun create(): GithubService {

        val client = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(mApiBaseUrl)
            .client(client)
            .build()
            .create(GithubService::class.java)
    }

}