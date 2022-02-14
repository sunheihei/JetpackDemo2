package com.example.jetpackdemo.di

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GithubUserInfoViewModel @Inject constructor(private val githubUserRepository: GithubUserInfoRepository) :
    ViewModel() {

    val mLoading = ObservableBoolean()

    fun fectchGithubUserInfo(name: String) = liveData {
        githubUserRepository.getGitUserInfo(name)
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



}