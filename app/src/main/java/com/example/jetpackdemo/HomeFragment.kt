package com.example.jetpackdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.Observable
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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
            //一种nav跳转
            findNavController().navigate(R.id.git_fragment, bundle, options)

            //另一种是nav导航图中建立好对应关系,注意：该方法只能再()的click方法中执行,再lambda中无法正常调用
//            Navigation.createNavigateOnClickListener(R.id.action_home_dest_to_git_fragment, bundle)

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