package com.example.jetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetpackdemo.bean.GitUserinfo
import com.example.jetpackdemo.di.GithubUserInfoViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivity : ComponentActivity() {

    private val TAG = "UserInfoActivity"


    private val userName = "sunheihei"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val gitUserinfoViewModel: GithubUserInfoViewModel by viewModels()

                    var userinfo = gitUserinfoViewModel.fectchGithubUserInfo(userName)
                        .observeAsState().value
                    userinfo?.let {
                        GitHubUserInfo(userinfo.getOrThrow())
                    }

                }
            }
        }

    }
}

@Composable
fun GitHubUserInfo(userinfo: GitUserinfo) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GitUserInfo") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                ) {
                    Image(
                        painter = rememberImagePainter(data = userinfo.avatar_url),
                        contentDescription = "avatar"
                    )
                }
                Text(text = userinfo.name)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackDemoTheme {
//        GitHubUserInfo()
    }
}