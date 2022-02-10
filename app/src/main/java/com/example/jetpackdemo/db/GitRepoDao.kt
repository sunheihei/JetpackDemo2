package com.example.jetpackdemo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jetpackdemo.bean.GitRepoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GitRepoDao {


    @Query("SELECT * FROM repos")
    fun getRepos(): Flow<List<GitRepoItem>>

    @Query("SELECT EXISTS(SELECT 1 FROM repos WHERE id = :repoId LIMIT 1)")
    fun isSaved(repoId: Int): Flow<Boolean>


    @Query("SELECT EXISTS(SELECT 1 FROM repos WHERE id = :repoId LIMIT 1)")
    fun isSavedForBoolean(repoId: Int): Boolean


    @Insert
    suspend fun insertFavRepo(gitRepo: GitRepoItem): Long

    @Delete
    suspend fun deleteFavRepo(gitRepo: GitRepoItem)


}