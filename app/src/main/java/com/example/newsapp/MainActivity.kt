package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField


import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.categories.CategoriesScreen
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
                    NewsScreenContent()
//                CategoriesScreen()


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(title:String,modifier: Modifier = Modifier,onSearchClick: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Row(modifier=Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier.size(1.dp))
            Text(text = title)
            Image(painter = painterResource(id = R.drawable.search_icon),
                contentDescription ="search icon"
                ,modifier = Modifier.clickable { onSearchClick() })
        }

    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black, titleContentColor = Color.White, navigationIconContentColor = Color.White),
        navigationIcon = {
            Image(painter = painterResource(id = R.drawable.menu_icon), contentDescription ="menu icon" )
        }
        )

}

@Preview(showBackground = true)
@Composable
fun NewsToolbarPreview(){
    val showSearch = remember { mutableStateOf(false) }
    NewsToolbar("General",onSearchClick = { showSearch.value = true })
}
@Composable
fun SearchScreen(onClose: () -> Unit, newsList: List<ArticlesItem>) {
    val searchQuery = remember { mutableStateOf("") }
    val filteredNews by remember {
        derivedStateOf {
            newsList.filter { article ->
                article.title?.contains(searchQuery.value, ignoreCase = true) == true
            }
        }
    }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    BackHandler(onBack = { onClose() })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black, RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = searchQuery.value,
                onValueChange = { newValue ->
                    searchQuery.value = newValue
                },
                maxLines = 1,
                placeholder = {Text("Search", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp, Color.White, RoundedCornerShape(16.dp)
                    )
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors (
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.LightGray,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.clickable { onClose() })
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn (modifier = Modifier.weight(1f)){
            items(filteredNews) { article ->
                NewsCard(articleItem = article, context = LocalContext.current)
            }
        }
    }
}

@Composable
fun NewsScreenContent() {
    val showSearch = remember { mutableStateOf(false) }

    val newsListState = remember { mutableStateListOf<ArticlesItem>() }
    val selectedSourceId = remember { mutableStateOf("") }
    val sourcesListState = remember { mutableStateListOf<SourcesItem>() }
    val errorState = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        getSources(
            onSuccess = { sourcesList -> sourcesListState.addAll(sourcesList) },
            onFailure = { errorState.value = it }
        )
    }

    LaunchedEffect(selectedSourceId.value) {
        if (selectedSourceId.value.isNotEmpty()) {
            newsListState.clear()
            ApiManager.newsServices.getNewsBySources(selectedSourceId.value).enqueue(
                object : Callback<NewsResponse> {
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        response.body()?.articles?.let { newsListState.addAll(it) }
                    }
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        Log.e("NewsError", "Failed to fetch news: ${t.message}")
                    }
                }
            )
        }
    }

    if (showSearch.value) {
        SearchScreen(onClose = { showSearch.value = false }, newsList = newsListState)
    } else {
        Scaffold(
            topBar = {
                NewsToolbar(title = "General", onSearchClick = { showSearch.value = true })
            }, containerColor = Color.Black
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SourcesTabRow(
                    sourcesList = sourcesListState,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) { selectedSourceId.value = it }

                if (selectedSourceId.value.isNotEmpty()) {
                    NewsList(newsList = newsListState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsBottomSheetDialog(
    articlesItem: ArticlesItem,
    onDismiss: () -> Unit,
    context: Context
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = Color.White,
        dragHandle = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = articlesItem.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = articlesItem.content ?: "",
                color = Color.Black,
                fontSize = 18.sp,
                maxLines = 4,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articlesItem.url))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "View Full Article", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NewsDialogPreview() {
    NewsBottomSheetDialog(ArticlesItem(author = " Jon Haworth",
        content = "40-year-old man falls 200 feet to his his hishis his his his his his death while canyoneering at national park",
    ) , onDismiss = { /*TODO*/ }, context = LocalContext.current)

}


@Composable
fun SourcesTabRow(
    sourcesList: List<SourcesItem>,
    modifier: Modifier = Modifier,
    onSourcesSelected:(id:String)->Unit) {
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedModifier = Modifier.drawBehind {
        val strokeWidthPx  = 2.dp.toPx()
        val verticalOffset = size.height -2.dp.toPx()
        drawLine(
            color = Color.White
            , strokeWidth = strokeWidthPx,
            start = Offset(0f,verticalOffset)
            , end = Offset(size.width,verticalOffset)
        )
    }

        LazyRow(modifier.background(Color.Black)){
        itemsIndexed(sourcesList){ index, sourcesItem ->
            Tab(selected = selectedIndex.intValue == index,
                onClick = {
                onSourcesSelected(sourcesItem.id ?:"")
                selectedIndex.intValue = index
            }, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(text = sourcesItem.name ?: "", color = Color.White,
               modifier =  if (selectedIndex.intValue == index){
                   selectedModifier
                }else{
                    Modifier
                }
                )
            }

        }
    }


}

@Composable
fun NewsList(newsList:List<ArticlesItem>,modifier: Modifier = Modifier) {
    LazyColumn {
        items(newsList) {
            NewsCard(articleItem = it, context = LocalContext.current)
        }
    }
}

@Composable
fun NewsCard(articleItem:ArticlesItem,modifier: Modifier = Modifier,context: Context) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        NewsBottomSheetDialog(articleItem, onDismiss = { showDialog.value = false }, context = context)
    }
    Card(
        modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Color.White,
                RoundedCornerShape(10.dp)
            )
            .padding(6.dp)
            .clickable { showDialog.value = true },

    colors = CardDefaults.cardColors(containerColor = Color.Transparent, contentColor = Color.White)
    ) {
        AsyncImage(model = articleItem.urlToImage,
            contentDescription = "Specific News Image",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,

            )
        Text(
            text = articleItem.title ?: "",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "By: ${articleItem.author}" ?: "",
            color = Color.Gray,
            fontSize = 10.sp,
            fontWeight = FontWeight.W500
            )
    }
}

@Preview
@Composable
private fun NewsCardPreview() {
    NewsCard(articleItem = ArticlesItem(author = " Jon Haworth",
        title = "40-year-old man falls 200 feet to his death while canyoneering at national park",
        ), context = LocalContext.current )

}

@Preview
@Composable
private fun SourcesTabRowPreview() {
    SourcesTabRow(sourcesList = listOf(SourcesItem(name = "ABC News"),
        SourcesItem(name = "ALARABIA"),
        SourcesItem(name = "Alatehad"),
        SourcesItem(name = "AL-nas"),
        SourcesItem(name = "AL-nas"),
        SourcesItem(name = "AL-nas"),
        SourcesItem(name = "AL-nas")
        )){

    }

}

fun getSources(onSuccess:(sources:List<SourcesItem>)->Unit,onFailure:(message:String)->Unit){
    ApiManager.newsServices.getSources()
//                        .execute() run in main thread
        .enqueue(object : Callback<SourcesResponse>
        {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                Log.e("TAG", "onResponse: ${response.body()?.sources}", )
                val list  = response.body()?.sources
                if (list?.isNotEmpty() == true)
                    onSuccess(list)
            }

            override fun onFailure(p0: Call<SourcesResponse>, throwable: Throwable) {
                Log.e("TAG", "onFailure: ${throwable.message}", )
                onFailure(throwable.message ?: "Somethings Went Wrong")
            }

        })
}