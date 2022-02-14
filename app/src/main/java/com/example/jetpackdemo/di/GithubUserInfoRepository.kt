package com.example.jetpackdemo.di

import android.util.Log
import com.example.jetpackdemo.GithubService
import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitRepoItem
import com.example.jetpackdemo.bean.GitUserinfo
import com.example.jetpackdemo.db.GitRepoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserInfoRepository @Inject constructor(
    private val service: GithubService
) {
    suspend fun getGitUserInfo(user: String): Flow<Result<GitUserinfo>> {
        return flow<Result<GitUserinfo>> {
            try {
                val result = service.getGitHubUserInfo(user)
                emit(Result.success(result))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

        }.flowOn(Dispatchers.IO)
    }

}