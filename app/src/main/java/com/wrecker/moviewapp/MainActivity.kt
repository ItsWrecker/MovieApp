package com.wrecker.moviewapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wrecker.moviewapp.presantation.MovieViewModel
import com.wrecker.moviewapp.presantation.UiState
import com.wrecker.moviewapp.presantation.UserAction
import com.wrecker.moviewapp.ui.theme.MoviewAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by viewModel.movie.collectAsState()
            //viewModel.processUserEvent(UserAction.Search("spider"))
            Log.e("MovieData", uiState.toString())
            val navController = rememberNavController()
            MoviewAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (uiState.isSearchScreen) "MovieSearch" else "MovieDetails"
                    ) {
                        composable("MovieSearch"){
                            SearchScreen(uiState = uiState, viewModel =viewModel )
                        }
                        composable("MovieDetails"){
                            MovieDetailsScreen(uiState = uiState, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsScreen(uiState: UiState, viewModel: MovieViewModel) {
    Scaffold(topBar = { TopAppBar(title = { Text(text = "MovieDetailsScreen") }) }) {
        val movieDetails = uiState.movieDetails ?: return@Scaffold
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            //if (movieDetails.poster.startsWith("https://"))
            GlideImage(model = movieDetails.poster, contentDescription = "poster")
            Text(text = movieDetails.actors)
            Text(text = movieDetails.awards)
            Text(text = movieDetails.genre)
            Text(text = movieDetails.language)
            Text(text = movieDetails.released)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SearchScreen(uiState: UiState, viewModel: MovieViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Movie") }) }
    ) {
        var searchInput by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = searchInput, onValueChange = {
                    searchInput = it
                })
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )

            Button(onClick = {
                viewModel.processUserEvent(UserAction.Search(searchInput))
            }) {
                Text(text = "Search")
            }

            if (uiState.movieData?.isNotEmpty() == true) {
                LazyColumn {
                    itemsIndexed(uiState.movieData) { index, item ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                viewModel.processUserEvent(UserAction.Details(item.imdbID))
                            }
                        ) {
                            Row {
                                GlideImage(model = item.poster, contentDescription = "poster")
                                Text(text = item.title)
                                Text(text = item.year)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviewAppTheme {
        Greeting("Android")
    }
}