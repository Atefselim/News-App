package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                LaunchedEffect(Unit) {
                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            val intent = Intent(this@SplashActivity,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                            ,2500)
                }
                SplashContent()
            }
        }
    }
}
@Composable
fun SplashContent(modifier: Modifier =Modifier){
    val context = LocalContext.current  //to access context ->
    Box(modifier = modifier
        .background(Color.Black)
        .fillMaxSize()
        .navigationBarsPadding()
        , contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.logo_splash), contentDescription = "splash logo",
            modifier = Modifier.fillMaxWidth(.25F),
            contentScale = ContentScale.Crop

        )
        Image(painter = painterResource(id = R.drawable.route_logo), contentDescription = "route logo"
        ,modifier = Modifier
                .fillMaxWidth(.4F)
                .align(Alignment.BottomCenter)


        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SplashContentPreview(){

    SplashContent()
}