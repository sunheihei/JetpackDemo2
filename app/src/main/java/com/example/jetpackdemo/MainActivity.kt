package com.example.jetpackdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.Observable
import com.example.jetpackdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var adapter: GitReposAdapter

    val gitReposViewModel: GithubRepoViewModel by viewModels()

    private val userName = "sunheihei"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gitReposRec.adapter = adapter

        gitReposViewModel.apply {
            fectchGithubRepos(userName).observe(this@MainActivity) { result ->

                result.fold(onSuccess = {
                    adapter.submitList(result.getOrThrow())

                }, onFailure = {

                })
            }


            mLoading.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    binding.progressCircular.visibility =
                        if (mLoading.get()) View.VISIBLE else View.GONE
                }
            })

            adapter.setOnSaveListener {
                return@setOnSaveListener gitReposViewModel.isSaved(it.id).value!!
            }


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


