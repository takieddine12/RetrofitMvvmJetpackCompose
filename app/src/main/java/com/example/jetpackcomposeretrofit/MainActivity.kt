package com.example.jetpackcomposeretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.AmbientConfiguration
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.jetpackcomposeretrofit.models.everythingmodel.EverythingModel
import com.example.jetpackcomposeretrofit.models.Screens
import com.example.jetpackcomposeretrofit.models.headlinesmodel.Article
import com.example.jetpackcomposeretrofit.models.headlinesmodel.TopHeadlinesModel
import com.example.jetpackcomposeretrofit.mvvm.NewsViewModel
import com.example.jetpackcomposeretrofit.util.Extras
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val headlinesList = newsViewModel.getHeadlines("us", "a9105f91876947b1b3b70761813fd4f9")?.value
            val everythingList = newsViewModel.getEverything("bitcoin", "a9105f91876947b1b3b70761813fd4f9")?.value
            val navController = rememberNavController()

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
                            val screensList = listOf(Screens.HeadlinesScreen, Screens.EverythingScreen)

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
                    })
            {
                CreateNavigator(navHostController = navController, topHeadlines = headlinesList!!, everything = everythingList!!)
            }
        }
    }
}


@Composable
fun CreateNavigator(navHostController: NavHostController, topHeadlines: TopHeadlinesModel, everything: EverythingModel) {
    NavHost(navController = navHostController, startDestination = "headlines") {
        composable("headlines") {
            LazyColumn(
                    modifier =  Modifier
                            .padding(bottom = 20.dp)
            ) {
                items(topHeadlines.articles) { value ->

                    Card(
                            shape = MaterialTheme.shapes.medium,
                            elevation = 14.dp,
                            modifier = Modifier.padding(16.dp)
                                    .clickable(onClick = {

                                    })
                    ) {
                        Column {
                            value.urlToImage?.let { url ->
                                val image = Extras.loadPicture(imageUrl = url, defaultImg = R.drawable.ic_launcher_background).value
                                image?.let {
                                    Image(modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp),
                                            bitmap = it.asImageBitmap())
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Text(text = value.title!!)
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Text(text = value.publishedAt!!)
                            }

                        }
                    }
                }
            }
        }
        composable("everything") {
            LazyColumn(
                    modifier =  Modifier
                            .padding(bottom = 20.dp)
            ) {
                items(everything.articles) { value ->

                    Card(
                            shape = MaterialTheme.shapes.medium,
                            elevation = 14.dp,
                            modifier = Modifier.padding(16.dp)
                                    .clickable(onClick = {})
                    ) {
                        Column {
                            value.urlToImage?.let { url ->
                                val image = Extras.loadPicture(imageUrl = url, defaultImg = R.drawable.ic_launcher_background).value
                                image?.let {
                                    Image(modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .padding(
                                                    start = 20.dp,
                                                    top = 10.dp,
                                                    end = 20.dp
                                            ),
                                            bitmap = it.asImageBitmap())
                                }
                                Text(text = value.title!!,
                                fontFamily = FontFamily.Cursive,
                                fontSize = TextUnit.Companion.Sp(18),
                                color = Color.Black)
                                Text(fontFamily = FontFamily.Cursive,
                                        fontSize = TextUnit.Companion.Sp(18),
                                        color = Color.Black,
                                        text = value.publishedAt!!)
                            }

                        }
                    }
                }
            }
        }
    }
}



