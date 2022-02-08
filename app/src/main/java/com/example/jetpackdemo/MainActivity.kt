package com.example.jetpackdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.Observable
import com.example.jetpackdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding


    private val gitReposViewModel: GithubRepoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gitReposViewModel.apply {
            Log.d(TAG, "loading")
            fectchGithubRepos("sunheihei").observe(this@MainActivity) { result ->

                result.fold(onSuccess = {
                    Log.d(TAG, "Success")

                }, onFailure = {
                    Log.d(TAG, "Fail")

                })
            }

            mLoading.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

                }

            })

        }


//        lifecycleScope.launch {
//            gitReposViewModel.apply {
//                fetchGithubRepos3("sunheihei").observe(this@MainActivity) { result ->
//                    result.fold(onSuccess = {
//                        Log.d(TAG, "Success")
//
//                    }, onFailure = {
//                        Log.d(TAG, "Fail")
//
//                    })
//                }
//            }
//        }


    }
}


