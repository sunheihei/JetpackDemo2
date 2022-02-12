package com.example.jetpackdemo

import android.util.Log
import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitRepoItem
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
class GithubRepoRepository @Inject constructor(
    private val service: GithubService,
    private val gitRepoDao: GitRepoDao
) {
    private var result: GitRepo? = null
    suspend fun getGitRepo(user: String): Flow<Result<GitRepo>> {
        return flow<Result<GitRepo>> {
            try {
                result = service.getGitHubRepo(user)
                emit(Result.success(dealSaveGitRepo(result!!)))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

        }.flowOn(Dispatchers.IO)
    }

    //和数据库已保存做对比
    private fun dealSaveGitRepo(result: GitRepo): GitRepo {
        val result2 = GitRepo()
        result.map {
            it.local_save = gitRepoDao.isSavedForBoolean(it.id)
            return@map it
        }.forEach {
            result2.add(it)
        }
        return result2
    }


    fun isSaved(repoId: Int) =
        gitRepoDao.isSaved(repoId)

    fun getRepos() = gitRepoDao.getRepos()

    fun isSavedForBoolean(repoId: Int) = gitRepoDao.isSavedForBoolean(repoId)


    suspend fun saveOrDeleteRepo(gitRepo: GitRepoItem) = withContext(Dispatchers.IO) {
        if (isSavedForBoolean(gitRepo.id)) {
            deleteRepo(gitRepo)
        } else {
            insertRepo(gitRepo)
        }
    }


    suspend fun insertRepo(gitRepo: GitRepoItem) {
        gitRepoDao.insertFavRepo(gitRepo)
    }


    suspend fun deleteRepo(gitRepo: GitRepoItem) {
        gitRepoDao.deleteFavRepo(gitRepo)
    }

}