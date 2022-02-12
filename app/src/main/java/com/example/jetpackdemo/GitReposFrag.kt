package com.example.jetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpackdemo.base.BaseVMFragment
import com.example.jetpackdemo.bean.GitRepo
import com.example.jetpackdemo.bean.GitRepoItem
import com.example.jetpackdemo.databinding.FragmentGitBinding

class GitFragment : BaseVMFragment<FragmentGitBinding>(FragmentGitBinding::inflate) {


    companion object {
        @JvmStatic
        fun newInstance() = GitFragment()
    }

    override fun initView() {

        val gitrepo: GitRepoItem = arguments?.getSerializable("repo") as GitRepoItem

        Log.d("GitFragment", gitrepo.name)

    }
}