package com.example.jetpackcomposeretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.bumptech.glide.Glide
import com.example.jetpackcomposeretrofit.models.everythingmodel.EverythingModel
import com.example.jetpackcomposeretrofit.models.headlinesmodel.Article
import com.example.jetpackcomposeretrofit.models.headlinesmodel.Screens
import com.example.jetpackcomposeretrofit.models.headlinesmodel.TopHeadlinesModel
import com.example.jetpackcomposeretrofit.mvvm.NewsViewModel
import com.example.jetpackcomposeretrofit.util.Extras
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            Surface(color = Color.White) {
                Scaffold(
                        topBar = {
                            TopAppBar(
                                    title = { Text(text = "News Application") },
                                    backgroundColor = Color.White,
                                    elevation = 12.dp,
                                    actions = {},
                                    navigationIcon = {}
                            )
                        },
                        bottomBar = {
                            BottomAppBar {
                                val screensList = listOf(Screens.headliensScreen, Screens.everythingScreen)

                                BottomNavigation(backgroundColor = Color.Black) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

                                    screensList.forEach {
                                        BottomNavigationItem(
                                                icon = { Icon(it.icon) },
                                                selected = currentRoute == it.route,
                                                label = { Text(text = it.label) },
                                                onClick = {
                                                    navController.popBackStack(
                                                            navController.graph.startDestination, false)
                                                    if (currentRoute != it.route) {
                                                        navController.navigate(it.route)
                                                    }
                                                })
                                    }
                                }

                            }
                        }) {

                    val headlinesList = newsViewModel.getHeadlines("us", "a9105f91876947b1b3b70761813fd4f9")?.value
                    val everythingList = newsViewModel.getEverything("bitcoin", "a9105f91876947b1b3b70761813fd4f9")?.value

                    GetHeadlines(navController = navController, headlinesList!!, everythingList!!)
                }
            }
        }
    }
}


@Composable
private fun GetHeadlines(navController: NavHostController, headlinesList : TopHeadlinesModel, everythingList: EverythingModel) {
    NavHost(navController = navController,
            startDestination = "headlines") {
        composable("headlines") {
            HeadlinesScreen(headlinesList)
        }
        composable("everything") {
            EverythingScreen(everythingList)
        }
    }
}

@Composable
private fun HeadlinesScreen(list : TopHeadlinesModel) {

    LazyColumn {
        items(list.articles) { value ->
            Card(
                    modifier = Modifier.fillMaxWidth()
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .padding(20.dp),
                    elevation = 14.dp,
                    backgroundColor = Color.White,
            ) {
                Box(modifier = Modifier.height(200.dp).padding(16.dp)) {
                    val image = Extras.loadPicture(imageUrl = value.urlToImage, defaultImg = R.drawable.ic_launcher_background).value
                    image?.let {
                        Image(modifier = Modifier.fillMaxWidth().height(200.dp),
                                bitmap = it.asImageBitmap())
                    }

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    Text(text = "${value.title}")

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    Text(text = "${value.publishedAt}")
                }
            }
        }
    }
}

@Composable
private fun EverythingScreen(list : EverythingModel) {
    LazyColumn {
        items(list.articles) { value ->
            Card(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .padding(20.dp),
                    elevation = 14.dp,
                    backgroundColor = Color.White,
                    content = {
                        val image = Extras.loadPicture(imageUrl = value.urlToImage, defaultImg = R.drawable.ic_launcher_background).value
                        image?.let {
                            Image(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                    bitmap = it.asImageBitmap())
                        }

                        Spacer(modifier = Modifier.padding(top = 10.dp))

                        Text(text = "${value.title}")

                        Spacer(modifier = Modifier.padding(top = 10.dp))

                        Text(text = "${value.publishedAt}")

                    })
        }
    }
}

