package com.example.jetpackdemo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams. SOFT_INPUT_ADJUST_RESIZE);
    }

}