package com.example.newsapp.categories

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.api.model.Category
import com.example.newsapp.ui.theme.blackWith50Opacity
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    LazyColumn {
        item { 
            Text(
                text = "Good Morning\n" +
                        "Here is Some News For You",
                color = Color.White,
                fontWeight = FontWeight.W500,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), textAlign = TextAlign.Start
            )
        }
        val categoriesList = Category.getCategoriesList()
        items(categoriesList.size){position->
            CategoryCard(categoriesList.get(position), isRight = position % 2 == 0)

        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    isRight:Boolean,
    modifier: Modifier = Modifier,
){
    Card (
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 4.dp)
            .fillMaxWidth(.9F)
            .height(120.dp)
        ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            ){
            if (isRight) {
                Image(painter = painterResource(id = category.drawableResId ?: R.drawable.logo_splash), contentDescription = "Image category",
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth(.4F),
                    contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.weight(1F))
                Column(modifier = Modifier.fillMaxHeight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround) {
                    Text(text = stringResource(id = category.titleResId?:R.string.app_name),
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp))
                    ViewAllRightArrowButton()
                }
            } else {
                Column(modifier = Modifier.fillMaxHeight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround) {
                    Text(text = stringResource(id = category.titleResId?:R.string.app_name),
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp))
                    ViewAllLeftArrowButton()
                }
                Spacer(modifier = Modifier.weight(1F))
                Image(painter = painterResource(id = category.drawableResId ?: R.drawable.logo_splash), contentDescription = "Image category",
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth(.4F)
                        .scale(2F),
                    )



            }
        }
    }
}

@Composable
fun ViewAllLeftArrowButton(modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .padding(8.dp)
        .background(blackWith50Opacity, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.view_all_right_arrow),
            contentDescription = "View ALl Image",
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = "View All",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
@Composable
fun ViewAllRightArrowButton(modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .padding(8.dp)
        .background(blackWith50Opacity, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(text = "View All",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
            )
        Image(painter = painterResource(id = R.drawable.view_all_right_arrow), contentDescription ="View ALl Image",
            modifier = Modifier.size(30.dp)
            )

    }
    
}

@Preview(showBackground = false)
@Composable
private fun CategoryCardRightPreview() {
    CategoryCard(category = Category.getCategoriesList()[0], isRight = true)

}
@Preview(showBackground = true)
@Composable
private fun CategoryCardLeftPreview() {
    CategoryCard(category = Category.getCategoriesList()[1], isRight = false)

}


@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen()
}