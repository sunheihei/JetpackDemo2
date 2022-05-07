package com.example.jetpackdemo

import com.example.jetpackdemo.bean.GitRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoDataSource @Inject constructor(private val service: GithubService) {

     fun getGitRepo(user: String) = flow<GitRepo> {
        val result = service.getGitHubRepo(user)
        emit(result)
    }



}