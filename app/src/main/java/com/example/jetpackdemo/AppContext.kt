package com.example.jetpackdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {
        

        private var instance: AppContext? = null

        fun getContext(): AppContext {
            return instance!!
        }
    }

}