package com.example.newsnow

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article

@Composable
fun HomePage(newsViewModel: NewsViewModel, navController: NavHostController) {

    val articles by newsViewModel.articles.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CategoriesBar(newsViewModel)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
           items(articles){ article ->
               ArticleItem(article, navController)
           }
        }
    }
}

@Composable
fun  ArticleItem(article: Article, navController: NavHostController){
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            navController.navigate(NewsArticleScreen(article.url))
        }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.urlToImage?:"data:image/png;base64,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",
                contentDescription = "Article image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                Text(text = article.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3
                )
                Text(text = article.source.name, maxLines = 2, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun CategoriesBar(newsViewModel: NewsViewModel) {

    var searchQuery by remember {
        mutableStateOf("")
    }
    var isSearchExpanded by remember {
        mutableStateOf(false)
    }

    val categoryList = listOf(
        "GENERAL",
        "BUSINESS",
        "ENTERTAINMENT",
        "HEALTH",
        "SCIENCE",
        "SPORTS",
        "TECHNOLOGY"
    )
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ){

        if (isSearchExpanded){
        OutlinedTextField(
            modifier = Modifier.padding(8.dp)
                .height(48.dp)
                .border(1.dp, Color.Gray, CircleShape)
                .clip(CircleShape),
            value = searchQuery, onValueChange = {searchQuery = it},
            trailingIcon = {
                IconButton(onClick = {
                    isSearchExpanded = false
                    if (searchQuery.isNotEmpty()){
                        newsViewModel.fetchEverythingWithQuery(searchQuery)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon" )
                }
            }
        )
        } else{
            IconButton(onClick = {
                isSearchExpanded = true
            }) {
               Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon" )
            }
        }

        categoryList.forEach{ category->
            Button(onClick = {
                newsViewModel.fetchNewsTopHeadLines(category)
            },
                modifier = Modifier.padding(4.dp)) {
                Text(text = category)
            }

        }

    }
}