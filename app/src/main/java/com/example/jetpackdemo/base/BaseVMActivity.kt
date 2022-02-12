package com.example.jetpackdemo.base
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.jetpackdemo.R
import com.example.jetpackdemo.utils.StatusBarUtil
import com.example.jetpackdemo.utils.VersionUtils

/**
 * https://stackoverflow.com/questions/63686289/how-to-use-abstraction-with-viewbinding-with-base-activity
 *
 * base activity
 */
abstract class BaseVMActivity<B : ViewBinding> : BaseActivity() {

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setStatusBarColor()
        initView()
    }

    abstract fun getViewBinding(): B

    abstract fun initView()


    /**
     * 设置状态栏
     */
    open fun setStatusBarColor() {
        //设置标题栏暗色,状态栏默认为白色
//        if (VersionUtils.hasMarshmallow()) {
//            StatusBarUtil.setColor(
//                this, ContextCompat.getColor(
//                    this,
//                    R.color.white
//                )
//            )
//            StatusBarUtil.setDarkMode(this)
//        } else {
//            StatusBarUtil.setColor(
//                this, ContextCompat.getColor(
//                    this,
//                    R.color.black
//                )
//            )
//        }
    }

}