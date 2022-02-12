package com.example.jetpackdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.Observable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.jetpackdemo.base.BaseVMFragment
import com.example.jetpackdemo.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseVMFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {


    private val TAG = "MainActivity"

    @Inject
    lateinit var adapter: GitReposAdapter

    val gitReposViewModel: GithubRepoViewModel by viewModels()

    private val userName = "sunheihei"

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun initView() {
        binding.gitReposRec.adapter = adapter

        gitReposViewModel.apply {
            fectchGithubRepos(userName).observe(requireActivity()) { result ->

                Log.d(TAG, "fectchGithubRepos")
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

            repos.observe(requireActivity()) { repos ->
                Log.d(TAG, "repos size: ${repos.size}")
            }
        }

        adapter.setOnSaveClick {
            it.local_save = !it.local_save
            gitReposViewModel.saveOrDeleteRepo(it)
            adapter.notifyDataSetChanged()
        }


        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        adapter.setOnItemClick {

            val bundle = Bundle().apply {
                putSerializable("repo", it)
            }
            findNavController().navigate(R.id.git_fragment, bundle, options)
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