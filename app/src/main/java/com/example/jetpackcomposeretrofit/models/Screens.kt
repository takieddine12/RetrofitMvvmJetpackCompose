package com.example.jetpackcomposeretrofit.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(var route : String ,var label : String , var icon : ImageVector) {

    object headliensScreen : Screens("headlines","Top Headlines",
    Icons.Default.Home)

    object everythingScreen : Screens("everything","Everything",
    Icons.Default.RssFeed)
}