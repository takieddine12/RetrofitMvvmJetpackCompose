package com.example.jetpackcomposeretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.findNavController
import com.example.jetpackcomposeretrofit.models.Screens
import com.example.jetpackcomposeretrofit.models.TopHeadlinesModel
import com.example.jetpackcomposeretrofit.mvvm.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()

            // TODO : FetchData
            newsViewModel.getHeadlines("us","a9105f91876947b1b3b70761813fd4f9")
            val list = newsViewModel.states?.value

               Surface(color = Color.Black) {
                    Scaffold {
                        // TODO  : AppBar
                        TopAppBar(
                            title = { androidx.compose.material.Text(text = "News Application") },
                            backgroundColor = Color.White,
                            elevation =  12.dp,
                            actions = {},
                            navigationIcon = {}
                        )

                        // TODO : BottomBar and BottomNavigationView
                        BottomAppBar {
                            val screensList = listOf(Screens.headliensScreen,Screens.everythingScreen)

                            BottomNavigation(
                                backgroundColor = Color.Black
                            ) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

                                screensList.forEach {
                                    BottomNavigationItem(
                                        icon = { Icon(it.icon) },
                                        label = { androidx.compose.material.Text(text = it.label) },
                                        selected = currentRoute == it.route,
                                        onClick = {
                                            navController.popBackStack(navController.graph.startDestination, false)
                                            if(currentRoute != it.route){
                                                navController.navigate(it.route)
                                            }
                                        })
                                }
                            }

                        }
                        GetHeadlines(navController = navController, list = list!!)
                    }
                }
            }
        }
    }

    @Composable
    private fun GetHeadlines(navController: NavHostController, list : TopHeadlinesModel){
       NavHost(navController = navController,
        startDestination = "headlines"){
            composable("headlines"){
                HeadlinesScreen(list)
            }
            composable("everything"){
                EverythingScreen()
            }
        }
    }


    @Composable
    private fun HeadlinesScreen(list : TopHeadlinesModel){
        Column {
            LazyColumn{
                items(list.articles){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .padding(20.dp),
                        elevation = 14.dp,
                        backgroundColor = Color.White,
                        content = {}
                    )



                }
            }
        }
    }

    @Composable
     private fun EverythingScreen(){
        Column {
            Box(contentAlignment = Alignment.Center) {
                androidx.compose.material.Text(text = "Everything Screen")
            }
        }
    }

