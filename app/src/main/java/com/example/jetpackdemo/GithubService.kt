package com.example.jetpackdemo

import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitUserinfo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}/repos")
    suspend fun getGitHubRepo(@Path("user") user: String): GitRepo


    @GET("users/{user}")
    suspend fun getGitHubUserInfo(@Path("user") user: String): GitUserinfo

}