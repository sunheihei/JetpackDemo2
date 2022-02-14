package com.example.jetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.example.jetpackdemo.di.GithubUserInfoViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivity : ComponentActivity() {

    private val TAG = "UserInfoActivity"

    val gitReposViewModel: GithubUserInfoViewModel by viewModels()

    private val userName = "sunheihei"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }

        gitReposViewModel.apply {
            fectchGithubUserInfo(userName).observe(this@UserInfoActivity) { result ->
                Log.d(TAG, "fectchGithubUserInfo")
                result.fold(onSuccess = {
                    Log.d(TAG, "${result.getOrNull()}")
                }, onFailure = {

                })
            }
        }


    }


}

@Composable
fun GitHubUserInfo() {
    Scaffold(
        topBar = {
            TopAppBar() {

            }
        }
    ) {

    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackDemoTheme {
        Greeting("Android")
    }
}