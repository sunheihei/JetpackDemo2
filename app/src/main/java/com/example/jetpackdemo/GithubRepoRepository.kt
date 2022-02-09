package com.example.jetpackdemo

import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitRepoItem
import com.example.jetpackdemo.db.GitRepoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoRepository @Inject constructor(
    private val service: GithubService,
    private val gitRepoDao: GitRepoDao
) {

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

    fun isSaved(repoId: Int) =
        gitRepoDao.isSaved(repoId)

    fun getRepos() = gitRepoDao.getRepos()


    suspend fun insertRepo(gitRepo: GitRepoItem) {
        gitRepoDao.insertFavRepo(gitRepo)
    }


    suspend fun deleteRepo(gitRepo: GitRepoItem) {
        gitRepoDao.deleteFavRepo(gitRepo)
    }

}