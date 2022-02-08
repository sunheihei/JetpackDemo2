package com.example.jetpackdemo

import com.example.jetpackdemo.bean.GitRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoRepository @Inject constructor(private val service: GithubService) {

    suspend fun getGitRepo(user: String): Flow<Result<GitRepo>> {
        return flow<Result<GitRepo>> {

            try {
                val result = service.getGitHubRepo(user)
                emit(Result.success(result))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

        }.flowOn(Dispatchers.IO)
    }

}