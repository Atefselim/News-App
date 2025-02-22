package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.NewsServices
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.ui.theme.NewsAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                LaunchedEffect(Unit) {
                    ApiManager.newsServices.getSources()
//                        .execute() run in main thread
                        .enqueue(object : Callback<SourcesResponse>
                         {
                            override fun onResponse(
                                call: Call<SourcesResponse>,
                                response: Response<SourcesResponse>
                            ) {
                                Log.e("TAG", "onResponse: ${response.body()?.sources}", )
                            }

                            override fun onFailure(p0: Call<SourcesResponse>, throwable: Throwable) {
                                Log.e("TAG", "onFailure: ${throwable.message}", )
                            }

                        })  //run on background thread and return result on main thread
                }

            }
        }
    }
}
