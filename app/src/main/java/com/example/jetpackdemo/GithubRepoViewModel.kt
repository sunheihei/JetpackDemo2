package com.example.jetpackdemo

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitRepoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubRepoViewModel @Inject constructor(private val githubRepoRepository: GithubRepoRepository) :
    ViewModel() {

    val mLoading = ObservableBoolean()

    // 私有的 MutableLiveData 可变的，对内访问
    private val gitRepos = MutableLiveData<Result<GitRepo>>()

    // 对外暴露不可变的 LiveData，只能查询
    val _gitRepos: LiveData<Result<GitRepo>> = gitRepos


    /**
     * 推荐
     * 方法二
     */
    fun fectchGithubRepos(name: String) = liveData {
        githubRepoRepository.getGitRepo(name)
            .onStart {
                // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的按钮
                mLoading.set(true)
            }
            .catch {
                // 捕获上游出现的异常
                mLoading.set(false)
            }
            .onCompletion {
                // 请求完成
                mLoading.set(false)
            }
            .collect { result ->
                emit(result)
            }

    }


//    /**
//     * 方法三
//     */
//    suspend fun fetchGithubRepos3(name: String) =
//        githubRepoRepository.getGitRepo(name)
//            .onStart {
//                // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的按钮
//            }
//            .catch {
//                // 捕获上游出现的异常
//            }
//            .onCompletion {
//                // 请求完成
//            }.asLiveData()
//
//
//    /**
//     * 方法一
//     *增加两个LiveData变量
//     */
//    fun fetchGithubRepos1(name: String) = viewModelScope.launch {
//        githubRepoRepository.getGitRepo(name)
//            .onStart {
//                // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的按钮
//            }
//            .catch {
//                // 捕获上游出现的异常
//            }
//            .onCompletion {
//                // 请求完成
//            }
//            .collectLatest { result ->
//                gitRepos.postValue(result)
//            }
//    }


    /**
     * DB 相关基础操作
     */

    val repos = githubRepoRepository.getRepos().asLiveData()

    fun isSaved(repoId: Int) = githubRepoRepository.isSaved(repoId).asLiveData()

    fun saveOrDeleteRepo(gitRepo: GitRepoItem) {
        viewModelScope.launch {
            githubRepoRepository.saveOrDeleteRepo(gitRepo)
        }
    }

    fun insertRepo(gitRepo: GitRepoItem) {
        viewModelScope.launch {
            githubRepoRepository.insertRepo(gitRepo)
        }
    }


    fun deleteRepo(gitRepo: GitRepoItem) {
        viewModelScope.launch {
            githubRepoRepository.deleteRepo(gitRepo)
        }
    }


}