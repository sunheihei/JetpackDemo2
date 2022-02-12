package com.example.jetpackdemo

import com.example.jetpackdemo.base.BaseVMActivity
import com.example.jetpackdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding>() {


    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

}


