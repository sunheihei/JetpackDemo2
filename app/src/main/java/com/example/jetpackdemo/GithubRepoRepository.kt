package com.example.jetpackdemo

import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitRepoItem
import com.example.jetpackdemo.db.GitRepoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 再抽出一层
 * 新增GithubRepoDataSource作为原始数据源
 * 如果需要中途修改数据则在Repository中运用map｛｝等方法操作
 *
 * retrofit方法中加suspend配合协程后面方法就不需要写suspend
 */

@Singleton
class GithubRepoRepository @Inject constructor(
    private val githubRepoDataSource: GithubRepoDataSource,
    private val gitRepoDao: GitRepoDao
) {

    fun getGitRepo(user: String): Flow<GitRepo> {
        return githubRepoDataSource.getGitRepo(user)
            .map { gitRepo ->
                gitRepo.forEach {
                    it.local_save = gitRepoDao.isSavedForBoolean(it.id)
                }
                gitRepo
            }.flowOn(Dispatchers.IO)
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



